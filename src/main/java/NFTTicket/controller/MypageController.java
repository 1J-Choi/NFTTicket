package NFTTicket.controller;

import NFTTicket.dto.*;
import NFTTicket.entity.Member;
import NFTTicket.entity.TicketBox;
import NFTTicket.service.MemberImgService;
import NFTTicket.service.MemberService;
import NFTTicket.service.TicketBoxService;
import NFTTicket.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class MypageController {
    private final MemberService memberService;
    private final TicketBoxService ticketBoxService;
    private final TicketService ticketService;


    @GetMapping(value = "/mypage")
    public String mypageUser(Model model){
        return "mypage/mypage";
    }

    @GetMapping("/imgmeta/{memberId}")
    public String imgMeta(@PathVariable("memberId")Long memberId, Model model){
        try{
            MemberImgMetaDto memberImgMetaDto = memberService.getMemberDtl(memberId);
            model.addAttribute("memberImgMetaDto", memberImgMetaDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 계정입니다.");
            return "/";
        }

        return "mypage/myImgMeta";
    }

    @PostMapping("/imgmeta/{memberId}")
    public String imgMetaUpdate(@Valid MemberImgMetaDto memberImgMetaDto, BindingResult bindingResult,
                                @RequestParam("memberImgFile") MultipartFile memberImgFile, Model model){
        if (bindingResult.hasErrors()) {
            return "mypage/myImgMeta";
        }
        try{
            memberService.updateMember(memberImgMetaDto, memberImgFile);
        }catch (Exception e){
            model.addAttribute("errorMessage", "회원 정보 수정 중 에러가 발생하였습니다.");
            return "mypage/myImgMeta";
        }
        return "redirect:/"; // 다시 실행 /
    }

    @GetMapping(value = {"/mypage/ticket", "/mypage/ticket/{page}"})
    public String ticketShow(TicketSearchDto ticketSearchDto, @PathVariable("page")Optional<Integer> page, Model model,
                             Principal principal, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "main";
        }

        String email = principal.getName();
        Member memberNow = memberService.findMember(email);
        TicketBox ticketBox = ticketBoxService.findTicketBox(memberNow.getId());
        Long ticketBoxid = ticketBox.getId();

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<TicketShowDto> tickets = ticketService.getTicketList(ticketSearchDto, ticketBoxid, pageable);
        model.addAttribute("tickets", tickets);
        model.addAttribute("ticketSearchDto", ticketSearchDto);
        model.addAttribute("maxPage", 5);
        return "mypage/mypageUser";
    }
}
