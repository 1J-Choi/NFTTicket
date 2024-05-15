package NFTTicket.repository;

import NFTTicket.dto.EventSearchDto;
import NFTTicket.entity.Event;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> getEvents(EventSearchDto eventSearchDto);
}
