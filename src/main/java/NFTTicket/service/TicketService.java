package NFTTicket.service;

import NFTTicket.repository.TicketBoxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {
    private final TicketBoxRepository ticketBoxRepository;
}
