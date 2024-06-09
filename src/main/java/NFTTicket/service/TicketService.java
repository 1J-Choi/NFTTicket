package NFTTicket.service;

import NFTTicket.dto.TicketDto;
import NFTTicket.dto.TicketSearchDto;
import NFTTicket.dto.TicketShowDto;
import NFTTicket.entity.Event;
import NFTTicket.entity.Member;
import NFTTicket.entity.Ticket;
import NFTTicket.entity.TicketBox;
import NFTTicket.repository.EventRepository;
import NFTTicket.repository.MemberRepository;
import NFTTicket.repository.TicketBoxRepository;
import NFTTicket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final MemberRepository memberRepository;
    private final TicketBoxRepository ticketBoxRepository;

    public Long buyTicket(TicketDto ticketDto, String email){
        Event event = eventRepository.findById(ticketDto.getEventId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);
        TicketBox ticketBox = ticketBoxRepository.findByMemberId(member.getId());

        validateDuplicateTicket(event, ticketBox);

        Ticket buyedTicket = Ticket.createTicket(event, ticketBox);
        ticketRepository.save(buyedTicket);

        return buyedTicket.getId();
    }

    private void validateDuplicateTicket(Event event, TicketBox ticketBox) {
        Ticket findTicket = ticketRepository.findByEventIdAndTicketBoxId(event.getId(), ticketBox.getId());
        if (findTicket != null) {
            throw new IllegalStateException("이미 SafeMint 신청을 한 행사입니다.");
        }
    }

    @Transactional(readOnly = true)
    public Page<TicketShowDto> getTicketList(TicketSearchDto ticketSearchDto, Long ticketBoxId, Pageable pageable) {
        return ticketRepository.getUserTickets(ticketSearchDto, ticketBoxId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<TicketShowDto> getAdminTicketList(TicketSearchDto ticketSearchDto, Long ticketBoxId, Pageable pageable) {
        return ticketRepository.getAdminTickets(ticketSearchDto, ticketBoxId, pageable);
    }
}
