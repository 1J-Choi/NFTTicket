package NFTTicket.controller;

import NFTTicket.dto.EventFormDto;
import NFTTicket.dto.EventSearchDto;
import NFTTicket.dto.EventShowDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
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
                           Principal principal, @RequestParam("eventImgFile") MultipartFile eventImgFile,
                           @RequestParam("memberImgFile") MultipartFile memberImgFile){
        if(bindingResult.hasErrors()){
            return "event/eventForm";
        }

        String email = principal.getName();
        Member memberNow = memberService.findMember(email);
        try{
            eventService.saveEvent(eventFormDto, memberNow, eventImgFile);
            memberService.saveMemberImg(memberNow, memberImgFile);
        }catch (Exception e){
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생했습니다.");
            return "event/eventForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = {"/event/all","/event/all/{page}"})
    public String eventListAll(EventSearchDto eventSearchDto, @PathVariable("page")Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<EventShowDto> events = eventService.getEventList(eventSearchDto, pageable);
        model.addAttribute("events", events);
        model.addAttribute("eventSearchDto", eventSearchDto);
        model.addAttribute("maxPage", 5);
        return "event/eventList_all";
    }

    @GetMapping(value = {"/event/circle", "/event/circle/{page}"})
    public String eventListCircle(EventSearchDto eventSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<EventShowDto> events = eventService.getEventsByCategory(eventSearchDto, "CIRCLE", pageable);
        model.addAttribute("events", events);
        model.addAttribute("eventSearchDto", eventSearchDto);
        model.addAttribute("maxPage", 5);
        return "event/eventList_circle";
    }

    @GetMapping(value = {"/event/department", "/event/department/{page}"})
    public String eventListDepartment(EventSearchDto eventSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<EventShowDto> events = eventService.getEventsByCategory(eventSearchDto, "DEPARTMENT", pageable);
        model.addAttribute("events", events);
        model.addAttribute("eventSearchDto", eventSearchDto);
        model.addAttribute("maxPage", 5);
        return "event/eventList_department";
    }

    @GetMapping(value = {"/event/others", "/event/others/{page}"})
    public String eventListOthers(EventSearchDto eventSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<EventShowDto> events = eventService.getEventsByCategory(eventSearchDto, "OTHERS", pageable);
        model.addAttribute("events", events);
        model.addAttribute("eventSearchDto", eventSearchDto);
        model.addAttribute("maxPage", 5);
        return "event/eventList_others";
    }
}

