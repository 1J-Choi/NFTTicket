package NFTTicket.service;

import NFTTicket.dto.EventFormDto;
import NFTTicket.dto.EventSearchDto;
import NFTTicket.entity.Event;
import NFTTicket.entity.Member;
import NFTTicket.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public Page<Event> getEventList(EventSearchDto eventSearchDto, Pageable pageable){
        return eventRepository.getEvents(eventSearchDto, pageable);
    }
}
