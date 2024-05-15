package NFTTicket.controller;

import NFTTicket.dto.EventFormDto;
import NFTTicket.dto.EventSearchDto;
import NFTTicket.entity.Event;
import NFTTicket.entity.Member;
import NFTTicket.service.EventService;
import NFTTicket.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = {"/event/all","/event/all/{page}"})
    public String eventListAll(EventSearchDto eventSearchDto, @PathVariable("page")Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<Event> events = eventService.getEventList(eventSearchDto, pageable);
        model.addAttribute("events", events);
        model.addAttribute("eventSearchDto", eventSearchDto);
        model.addAttribute("maxPage", 5);
        return "event/eventList_all";
    }

    @GetMapping(value = "/event/circle")
    public String eventListCircle() {return "event/eventList_circle";}

    @GetMapping(value = "/event/department")
    public String eventListDepartment() {return "event/eventList_department";}

    @GetMapping(value = "/event/others")
    public String eventListOthers() {return "event/eventList_others";}
}
