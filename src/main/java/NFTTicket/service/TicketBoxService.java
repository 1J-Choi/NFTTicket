package NFTTicket.service;

import NFTTicket.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketBoxService {
    private final TicketRepository ticketRepository;
}
