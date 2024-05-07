package NFTTicket.controller;

import NFTTicket.dto.EventFormDto;
import NFTTicket.entity.Member;
import NFTTicket.service.EventService;
import NFTTicket.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    private final MemberService memberService;

    @GetMapping("/owner/event/new")
    public String eventForm(Model model){
        model.addAttribute("eventFormDto", new EventFormDto());
        return "event/eventForm";
    }

    @PostMapping("/owner/event/new")
    public String newEvent(@Valid EventFormDto eventFormDto, BindingResult bindingResult, Model model,
                           Principal principal){
        if(bindingResult.hasErrors()){
            return "event/eventForm";
        }

        String email = principal.getName();
        Member memberNow = memberService.findMember(email);
        try{
            eventService.saveEvent(eventFormDto, memberNow);
        }catch (Exception e){
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생했습니다.");
            return "event/eventForm";
        }
        return "redirect:/";
    }
}
