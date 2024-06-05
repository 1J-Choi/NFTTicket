package NFTTicket.repository;

import NFTTicket.entity.Event;
import NFTTicket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TicketRepository extends JpaRepository<Ticket, Long>, QuerydslPredicateExecutor<Ticket>, TicketRepositoryCustom{
    Ticket findByEventIdAndTicketBoxId(Long eventId, Long ticketBoxId);
}