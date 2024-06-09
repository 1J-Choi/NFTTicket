package NFTTicket.controller;

import NFTTicket.dto.*;
import NFTTicket.entity.Member;
import NFTTicket.entity.TicketBox;
import NFTTicket.service.*;
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
    public String mypageUser(Model model, Principal principal){

        String email = principal.getName();
        Member memberNow = memberService.findMember(email);
        try {
            MypageShowDto mypageShowDto = memberService.findMypageShowDto(memberNow);
            model.addAttribute("mypageShowDto", mypageShowDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 계정입니다.");
            return "/";
        }

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

    @GetMapping(value = {"/mypage/mypageAdmin", "/mypage/mypageAdmin/{page}"})
    public String ticketShowAdmin(TicketSearchDto ticketSearchDto, @PathVariable("page")Optional<Integer> page, Model model,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "main";
        }

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<TicketShowDto> tickets = ticketService.getAdminTicketList(ticketSearchDto, pageable);
        model.addAttribute("tickets", tickets);
        model.addAttribute("ticketSearchDto", ticketSearchDto);
        model.addAttribute("maxPage", 5);
        return "mypage/mypageAdmin";
    }

    @GetMapping(value = {"/mypage/mypageUser", "/mypage/mypageUser/{page}"})
    public String ticketShowUser(TicketSearchDto ticketSearchDto, @PathVariable("page")Optional<Integer> page, Model model,
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
    @GetMapping(value = {"/mypage/mypageOwner", "/mypage/mypageOwner/{page}"})
    public String ticketShowOwner(TicketSearchDto ticketSearchDto, @PathVariable("page")Optional<Integer> page, Model model,
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
        return "mypage/mypageOwner";
    }
}
