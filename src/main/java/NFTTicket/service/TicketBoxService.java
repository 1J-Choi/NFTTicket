package NFTTicket.service;

import NFTTicket.entity.Member;
import NFTTicket.entity.Ticket;
import NFTTicket.entity.TicketBox;
import NFTTicket.repository.MemberRepository;
import NFTTicket.repository.TicketBoxRepository;
import NFTTicket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketBoxService {
    private final TicketBoxRepository ticketBoxRepository;
    private final TicketRepository ticketRepository;
    private final MemberRepository memberRepository;

    public Long makeTicketBox(Member member){
        TicketBox ticketBox = new TicketBox();
        ticketBox.setMember(member);
        ticketBoxRepository.save(ticketBox);
        return ticketBox.getId();
    }

    public TicketBox findTicketBox (Long memberId) {
        return ticketBoxRepository.findByMemberId(memberId);
    }

    public boolean validateTicketBox(Long ticketId, String email){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        if(ticket.getTicketBox().getMember() == member){
            return true;
        }
        return false;
    }
}
