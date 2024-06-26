package NFTTicket.repository;

import NFTTicket.dto.EventSearchDto;
import NFTTicket.dto.EventShowDto;
import NFTTicket.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventRepositoryCustom {
    Page<EventShowDto> getEvents(EventSearchDto eventSearchDto, Pageable pageable);
    Page<EventShowDto> getEventsByCategory(EventSearchDto eventSearchDto, String category, Pageable pageable);
    List<EventShowDto> getNewest5Events();
    Page<EventShowDto> getRequestEvents(EventSearchDto eventSearchDto, Pageable pageable);
    Page<EventShowDto> getOwnerEvents(EventSearchDto eventSearchDto, Pageable pageable, Long memberId); // 이 부분은 쓰지 않을 듯 합니다.
    Page<EventShowDto> getOwnerRequestEvents(EventSearchDto eventSearchDto, Pageable pageable, Long memberId);
    Page<EventShowDto> getOwnerCompletionEvents(EventSearchDto eventSearchDto, Pageable pageable, Long memberId);
}
