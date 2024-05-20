package NFTTicket.controller;

import NFTTicket.dto.EventShowDto;
import NFTTicket.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final EventService eventService;
    @GetMapping(value = "/")
    public String main(Model model){
        List<EventShowDto> events = eventService.findNewest5Events();
        model.addAttribute("events", events);
        return "main";
    }
}
