package NFTTicket.repository;

import NFTTicket.constant.EventCategory;
import NFTTicket.dto.QTicketShowDto;
import NFTTicket.dto.TicketSearchDto;
import NFTTicket.dto.TicketShowDto;
import NFTTicket.entity.QEvent;
import NFTTicket.entity.QEventImg;
import NFTTicket.entity.QTicket;
import NFTTicket.entity.TicketBox;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class TicketRepositoryCustomImpl implements TicketRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public TicketRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("eventNm", searchBy)) { // 상품명
            return QTicket.ticket.event.evName.like("%"+searchQuery+"%");
        } else if (StringUtils.equals("createdBy", searchBy)) { // 작성자
            return QTicket.ticket.event.member.nick.like("%"+searchQuery+"%");
        }
        return null;
    }

    private BooleanExpression ticketEq(Long ticketBoxId) {
        return ticketBoxId == null ? null : QTicket.ticket.ticketBox.id.eq(ticketBoxId);
    }

    @Override
    public Page<TicketShowDto> getUserTickets(TicketSearchDto ticketSearchDto, Long ticketBoxId, Pageable pageable) {
        QTicket ticket = QTicket.ticket;
        QEvent event = QEvent.event;
        QEventImg eventImg = QEventImg.eventImg;

        QueryResults<TicketShowDto> results = queryFactory.select(new QTicketShowDto(ticket.id, event.evName, event.date,
                        event.place, event.member.nick, eventImg.imgURL))
                .from(ticket).join(ticket.event, event)
                .where(ticketEq(ticketBoxId), searchByLike(ticketSearchDto.getSearchBy(), ticketSearchDto.getSearchQuery()))
                .limit(pageable.getPageSize()).fetchResults();

        List<TicketShowDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }


}
