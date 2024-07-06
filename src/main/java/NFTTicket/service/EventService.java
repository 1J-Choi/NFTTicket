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

import javax.persistence.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public List<EventShowDto> findNewest5Events(){
        return eventRepository.getNewest5Events();
    }

    @Transactional(readOnly = true)
    public Page<EventShowDto> getRequestEvents(EventSearchDto eventSearchDto, Pageable pageable) {
        return eventRepository.getRequestEvents(eventSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<EventShowDto> getOwnerEvents(EventSearchDto eventSearchDto, Pageable pageable, Long memberId) {
        return eventRepository.getOwnerEvents(eventSearchDto, pageable, memberId);
    } // 해당 메소드를 바탕으로 만들면 될 것 같습니다.

    @Transactional(readOnly = true)
    public Page<EventShowDto> getOwnerRequestEvents(EventSearchDto eventSearchDto, Pageable pageable, Long memberId) {
        return eventRepository.getOwnerRequestEvents(eventSearchDto, pageable, memberId);
    }

    @Transactional(readOnly = true)
    public Page<EventShowDto> getOwnerCompletionEvents(EventSearchDto eventSearchDto, Pageable pageable, Long memberId) {
        return eventRepository.getOwnerCompletionEvents(eventSearchDto, pageable, memberId);
    }

    public void confirmEvent(Long eventId){
        Event event = eventRepository.findById(eventId).orElseThrow(EntityNotFoundException::new);
        event.confirmEventTransNow();
    }
}
