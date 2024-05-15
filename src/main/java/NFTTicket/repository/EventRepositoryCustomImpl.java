package NFTTicket.repository;

import NFTTicket.dto.EventSearchDto;
import NFTTicket.entity.Event;
import NFTTicket.entity.QEvent;
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

    @Override
    public Page<Event> getEvents(EventSearchDto eventSearchDto, Pageable pageable){
        QueryResults<Event> results = queryFactory.selectFrom(QEvent.event).
                where(searchByLike(eventSearchDto.getSearchBy(), eventSearchDto.getSearchQuery()))
                .orderBy(QEvent.event.id.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
        List<Event> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
