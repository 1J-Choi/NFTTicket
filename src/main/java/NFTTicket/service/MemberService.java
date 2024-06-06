package NFTTicket.service;

import NFTTicket.dto.MemberImgDto;
import NFTTicket.dto.MemberImgMetaDto;
import NFTTicket.dto.MypageShowDto;
import NFTTicket.entity.Member;
import NFTTicket.entity.MemberImg;
import NFTTicket.entity.Ticket;
import NFTTicket.entity.TicketBox;
import NFTTicket.repository.MemberImgRepository;
import NFTTicket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final MemberImgRepository memberImgRepository;
    private final MemberImgService memberImgService;  // MemberImgService 추가
    private final TicketBoxService ticketBoxService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

//        List<GrantedAuthority> authorities = new ArrayList<>();
        return User.builder().username(member.getEmail()).password(member.getPassword()).roles(member.getRole().toString())
//                .authorities(authorities)
                .build();
    }

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        Long ticketBoxId = ticketBoxService.makeTicketBox(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입 된 회원입니다.");
        }
    }

    public Member findMember(String email){
        return memberRepository.findByEmail(email);
    }

    public void saveMemberImg(Member member, MultipartFile memberImgFile) throws Exception {
        if (memberImgFile != null && !memberImgFile.isEmpty()) {
            MemberImg memberImg = new MemberImg();
            memberImg.setMember(member);
            memberImgService.saveMemberImg(memberImg, memberImgFile);
        }
    }

    @Transactional(readOnly = true)
    public MemberImgMetaDto getMemberDtl(Long memberId){
        MemberImg memberImg = memberImgRepository.findByMemberId(memberId);
        MemberImgDto memberImgDto = MemberImgDto.of(memberImg);

        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        MemberImgMetaDto memberImgMetaDto = new MemberImgMetaDto();
        memberImgMetaDto.setMemberImgMetaDto(member, memberImgDto);
        memberImgMetaDto.setMemberImgDto(memberImgDto);
        return memberImgMetaDto;
    }

    public Long updateMember(MemberImgMetaDto memberImgMetaDto, MultipartFile memberImgFile) throws Exception{
        Member member = memberRepository.findById(memberImgMetaDto.getId()).orElseThrow(EntityNotFoundException::new);
        member.updateMeta(memberImgMetaDto);

        memberImgService.updateMemberImg(memberImgMetaDto.getMemberImgId(), memberImgFile);

        return member.getId();
    }

    public MypageShowDto findMypageShowDto(Member member) {
        MemberImg memberImg = memberImgRepository.findByMemberId(member.getId());
        MypageShowDto mypageShowDto = new MypageShowDto(member, memberImg);
        return mypageShowDto;
    }
}
