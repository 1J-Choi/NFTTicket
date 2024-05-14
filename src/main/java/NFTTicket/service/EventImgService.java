package NFTTicket.service;

import NFTTicket.repository.EventImgRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EventImgService {
    private final EventImgRepository eventImgRepository;
}
