package NFTTicket.service;

import NFTTicket.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EventImgService {
    private final EventRepository eventRepository;
}
