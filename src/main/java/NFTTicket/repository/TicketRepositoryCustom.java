package NFTTicket.repository;

import NFTTicket.dto.TicketSearchDto;
import NFTTicket.dto.TicketShowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketRepositoryCustom {
    Page<TicketShowDto> getUserTickets(TicketSearchDto ticketSearchDto, Long ticketBoxId, Pageable pageable);
    Page<TicketShowDto> getAdminTickets(TicketSearchDto ticketSearchDto, Pageable pageable);
    Page<TicketShowDto> getUserNTickets(TicketSearchDto ticketSearchDto, Long ticketBoxId, Pageable pageable);

}
