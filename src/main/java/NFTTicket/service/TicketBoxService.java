package NFTTicket.service;

import NFTTicket.repository.TicketBoxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketBoxService {
    private final TicketBoxRepository ticketBoxRepository;
}
