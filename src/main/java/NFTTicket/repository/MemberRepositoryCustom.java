package NFTTicket.repository;

import NFTTicket.dto.MypageShowDto;

public interface MemberRepositoryCustom {
    MypageShowDto findMypageShowDtoByEmail(String email);
}
