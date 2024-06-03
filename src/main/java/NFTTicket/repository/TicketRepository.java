package NFTTicket.repository;

import NFTTicket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByEventIdAndTicketBoxId(Long eventId, Long ticketBoxId);
}