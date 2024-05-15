package NFTTicket.service;

import NFTTicket.repository.EventImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventImgService {
    private final EventImgRepository eventImgRepository;
}
