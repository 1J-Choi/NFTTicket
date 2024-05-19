package NFTTicket.service;

import NFTTicket.dto.EventFormDto;
import NFTTicket.dto.EventSearchDto;
import NFTTicket.dto.EventShowDto;
import NFTTicket.entity.Event;
import NFTTicket.entity.EventImg;
import NFTTicket.entity.Member;
import NFTTicket.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventImgService eventImgService;

    public Long saveEvent(EventFormDto eventFormDto, Member member, MultipartFile eventImgFile)throws Exception{
        Event event = Event.createEvent(eventFormDto, member);
        eventRepository.save(event);
        EventImg eventImg = new EventImg();
        eventImg.setEvent(event);
        eventImgService.saveEventImg(eventImg, eventImgFile);
        return event.getId();
    }

    @Transactional(readOnly = true)
    public Page<EventShowDto> getEventList(EventSearchDto eventSearchDto, Pageable pageable){
        return eventRepository.getEvents(eventSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<EventShowDto> getEventsByCategory(EventSearchDto eventSearchDto, String category, Pageable pageable){
        return eventRepository.getEventsByCategory(eventSearchDto, category, pageable);
    }
}
