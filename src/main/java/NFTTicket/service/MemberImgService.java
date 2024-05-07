package NFTTicket.service;

import NFTTicket.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberImgService {
    private final MemberImgService memberImgService;
}

