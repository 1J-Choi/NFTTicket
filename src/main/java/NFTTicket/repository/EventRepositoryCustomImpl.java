package NFTTicket.repository;

import NFTTicket.dto.EventSearchDto;
import NFTTicket.entity.Event;
import NFTTicket.entity.QEvent;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
    public List<Event> getEvents(EventSearchDto eventSearchDto){
        QueryResults<Event> results = queryFactory.selectFrom(QEvent.event).
                where(searchByLike(eventSearchDto.getSearchBy(), eventSearchDto.getSearchQurey()))
                .orderBy(QEvent.event.id.desc()).fetchResults();
        return results.getResults();
    }
}
