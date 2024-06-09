package NFTTicket.repository;

import NFTTicket.constant.EventCategory;
import NFTTicket.constant.TransactionStatus;
import NFTTicket.dto.EventSearchDto;
import NFTTicket.dto.EventShowDto;
import NFTTicket.dto.QEventShowDto;
import NFTTicket.entity.Event;
import NFTTicket.entity.QEvent;
import NFTTicket.entity.QEventImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

        public class EventRepositoryCustomImpl implements EventRepositoryCustom{

            private JPAQueryFactory queryFactory; // 동적쿼리 사용하기 위해 JPAQueryFactory 변수 선언

            public EventRepositoryCustomImpl (EntityManager em){ // 생성자
                this.queryFactory =new JPAQueryFactory(em); // JPAQueryFactory 실질적인 객체가 만들어진다.
            }

            private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("eventNm", searchBy)) { // 상품명
            return QEvent.event.evName.like("%"+searchQuery+"%");
        } else if (StringUtils.equals("createdBy", searchBy)) { // 작성자
            return QEvent.event.member.nick.like("%"+searchQuery+"%");
        }
        return null;
    }

    private BooleanExpression categoryEq(String category) {
        return StringUtils.isEmpty(category) ? null : QEvent.event.category.eq(EventCategory.valueOf(category));
    }

    private BooleanExpression transactionRq() {
                return QEvent.event.tranNow.eq(TransactionStatus.REQUEST);
    }

    private BooleanExpression transactionCp() {
                return QEvent.event.tranNow.eq(TransactionStatus.COMPLETION);
            }

    @Override
    public Page<EventShowDto> getEvents(EventSearchDto eventSearchDto, Pageable pageable){
        QEvent event = QEvent.event;
        QEventImg eventImg = QEventImg.eventImg;

//        QueryResults<EventShowDto> results = queryFactory.selectFrom(QEvent.event).
//                where(searchByLike(eventSearchDto.getSearchBy(), eventSearchDto.getSearchQuery()))
//                .orderBy(QEvent.event.id.desc())
//                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
//        List<Event> content = results.getResults();
//        long total = results.getTotal();
//        return new PageImpl<>(content, pageable, total);

        //QEventShowDto @QueryProjection을 활용하면 DTO로 바로 조회 가능
        QueryResults<EventShowDto> results = queryFactory.select(new QEventShowDto(event.id, event.evName,
                        event.date, event.place, event.member.nick, event.number, eventImg.imgURL))
                // join 내부조인 .repImgYn.eq("Y")인 대표 이미지만 가져온다.
                .from(eventImg).join(eventImg.event, event)
                .where(transactionCp(), searchByLike(eventSearchDto.getSearchBy(), eventSearchDto.getSearchQuery()))
                .orderBy(event.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
        List<EventShowDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<EventShowDto> getEventsByCategory(EventSearchDto eventSearchDto, String category, Pageable pageable){
        QEvent event = QEvent.event;
        QEventImg eventImg = QEventImg.eventImg;

        QueryResults<EventShowDto> results = queryFactory.select(new QEventShowDto(event.id, event.evName, event.date,
                        event.place, event.member.nick, event.number, eventImg.imgURL))
                .from(eventImg).join(eventImg.event, event)
                .where(transactionCp(), categoryEq(category), searchByLike(eventSearchDto.getSearchBy(), eventSearchDto.getSearchQuery()))
                .orderBy(event.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
        List<EventShowDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
    @Override
    public List<EventShowDto> getNewest5Events(){
        QEvent event = QEvent.event;
        QEventImg eventImg = QEventImg.eventImg;

        QueryResults<EventShowDto> results = queryFactory.select(new QEventShowDto(event.id, event.evName,
                event.date, event.place, event.member.nick, event.number, eventImg.imgURL))
                .from(eventImg).join(eventImg.event, event)
                .where(transactionCp())
                .limit(5)
                .orderBy(event.id.desc()).fetchResults();
        return results.getResults();
    }

    @Override
    public Page<EventShowDto> getRequestEvents(EventSearchDto eventSearchDto, Pageable pageable){
                QEvent event = QEvent.event;
                QEventImg eventImg = QEventImg.eventImg;

                QueryResults<EventShowDto> results = queryFactory.select(new QEventShowDto(event.id, event.evName,
                                event.date, event.place, event.member.nick, event.number, eventImg.imgURL))
                        .from(eventImg).join(eventImg.event, event)
                        .where(transactionRq(), searchByLike(eventSearchDto.getSearchBy(), eventSearchDto.getSearchQuery()))
                        .orderBy(event.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
                List<EventShowDto> content = results.getResults();
                long total = results.getTotal();
                return new PageImpl<>(content, pageable, total);
            }
}
