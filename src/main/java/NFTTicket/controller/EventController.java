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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
                           Principal principal, @RequestParam("eventImgFile") MultipartFile eventImgFile){
        if(bindingResult.hasErrors()){
            return "event/eventForm";
        }

        String email = principal.getName();
        Member memberNow = memberService.findMember(email);
        try{
            eventService.saveEvent(eventFormDto, memberNow, eventImgFile);
        }catch (Exception e){
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생했습니다.");
            return "event/eventForm";
        }
        return "redirect:/";
    }
    
    @DeleteMapping(value = "/owner/event/{eventId}")
    public @ResponseBody ResponseEntity deleteEvent(@PathVariable("eventId") Long eventId, Principal principal){
        // Admin인지 확인하는 부분
        if(!memberService.validateOwner(principal.getName())){
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        if(!eventService.validateRequest(eventId)){
            return new ResponseEntity<String>("컨펌 요청 대기 상태가 아닙니다.", HttpStatus.FORBIDDEN);
        }

        eventService.deleteEvent(eventId);

        return new ResponseEntity<Long>(eventId, HttpStatus.OK);
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

