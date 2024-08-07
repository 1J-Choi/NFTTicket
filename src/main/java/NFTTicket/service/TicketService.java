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
import org.springframework.security.core.parameters.P;
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
    public Page<TicketShowDto> getNTicketList(TicketSearchDto ticketSearchDto, Long ticketBoxId, Pageable pageable) {
        return ticketRepository.getUserNTickets(ticketSearchDto, ticketBoxId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<TicketShowDto> getAdminTicketList(TicketSearchDto ticketSearchDto, Pageable pageable) {
        return ticketRepository.getAdminTickets(ticketSearchDto, pageable);
    }

    public void confirmTicket(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(EntityNotFoundException::new);
        Event nowEvent = eventRepository.findById(ticket.getEvent().getId()).orElseThrow(EntityNotFoundException::new);

        validateNumberEvent(nowEvent);
        // 여기서 nowEvent를 사용한 인원수 초가 관련 if를 추가해야 될 것

        ticket.confirmTicketSafeMint();
        int nowNumber = nowEvent.getNowNumber();
        nowEvent.setNowNumber(nowNumber+1);
    }

    public void validateNumberEvent(Event event) {
        if (event.getNumber() <= event.getNowNumber()) {
            throw new IllegalStateException("제한 인원수가 가득 찬 행사입니다.");
        }
    }

    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(EntityNotFoundException::new);
        ticketRepository.delete(ticket);
    }
}
