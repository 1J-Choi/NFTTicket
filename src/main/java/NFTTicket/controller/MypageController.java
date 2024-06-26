package NFTTicket.controller;

import NFTTicket.constant.Role;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    private final EventService eventService;


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

    @GetMapping(value = {"/mypage/mypageAdmin/safemint", "/mypage/mypageAdmin/safemint/{page}"})
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
        return "mypage/mypageAdmin_safemint";
    }

    @GetMapping(value = {"/mypage/mypageAdmin/event", "/mypage/mypageAdmin/event/{page}"})
    public String eventShowAdmin(EventSearchDto eventSearchDto, @PathVariable("page")Optional<Integer> page, Model model,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "main";
        }

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<EventShowDto> events = eventService.getRequestEvents(eventSearchDto, pageable);
        model.addAttribute("events", events);
        model.addAttribute("eventSearchDto", eventSearchDto);
        model.addAttribute("maxPage", 5);
        return "mypage/mypageAdmin_event";
    }

    @PostMapping("/admins/eventConfirm/{eventId}")
    public @ResponseBody ResponseEntity confirmEvent(@PathVariable("eventId") Long eventId, Principal principal) {
//        if (!orderService.validateOrder(orderId, principal.getName())){
//            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
//        }
        if(!memberService.findMember(principal.getName()).getRole().toString().equals(Role.ADMIN.toString())){
            return new ResponseEntity<String>("행사 컨펌 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        eventService.confirmEvent(eventId);
        return new ResponseEntity<Long>(eventId, HttpStatus.OK);
    }

    @PostMapping("/admins/ticketConfirm/{ticketId}")
    public @ResponseBody ResponseEntity confirmTicket(@PathVariable("ticketId") Long ticketId, Principal principal) {
//        if (!orderService.validateOrder(orderId, principal.getName())){
//            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
//        }
        if(!memberService.findMember(principal.getName()).getRole().toString().equals(Role.ADMIN.toString())){
            return new ResponseEntity<String>("티켓 컨펌 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        ticketService.confirmTicket(ticketId);
        return new ResponseEntity<Long>(ticketId, HttpStatus.OK);
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
