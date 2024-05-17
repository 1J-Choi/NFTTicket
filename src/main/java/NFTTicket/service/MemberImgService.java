package NFTTicket.service;

import NFTTicket.repository.MemberImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberImgService {
    private final MemberImgRepository memberImgRepository;
    private final FileService fileService;
}

