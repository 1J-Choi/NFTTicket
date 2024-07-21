package NFTTicket.repository;

import NFTTicket.entity.Event;
import NFTTicket.entity.EventImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventImgRepository extends JpaRepository<EventImg, Long> {
    EventImg findByEvent(Event event);
}