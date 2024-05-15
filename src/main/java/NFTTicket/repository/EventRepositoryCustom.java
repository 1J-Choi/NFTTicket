package NFTTicket.repository;

import NFTTicket.dto.EventSearchDto;
import NFTTicket.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventRepositoryCustom {
    Page<Event> getEvents(EventSearchDto eventSearchDto, Pageable pageable);
}
