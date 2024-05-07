package NFTTicket.service;

import NFTTicket.dto.EventFormDto;
import NFTTicket.entity.Event;
import NFTTicket.entity.Member;
import NFTTicket.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Long saveEvent(EventFormDto eventFormDto, Member member)throws Exception{
        Event event = Event.createEvent(eventFormDto, member);
        eventRepository.save(event);
        return event.getId();
    }
}
