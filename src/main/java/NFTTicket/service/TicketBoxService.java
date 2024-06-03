package NFTTicket.service;

import NFTTicket.entity.Member;
import NFTTicket.entity.Ticket;
import NFTTicket.entity.TicketBox;
import NFTTicket.repository.TicketBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketBoxService {
    private final TicketBoxRepository ticketBoxRepository;

    public Long makeTicketBox(Member member){
        TicketBox ticketBox = new TicketBox();
        ticketBox.setMember(member);
        ticketBoxRepository.save(ticketBox);
        return ticketBox.getId();
    }
}
