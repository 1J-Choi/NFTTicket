package NFTTicket.service;

import NFTTicket.repository.TicketBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketBoxService {
    private final TicketBoxRepository ticketBoxRepository;
}
