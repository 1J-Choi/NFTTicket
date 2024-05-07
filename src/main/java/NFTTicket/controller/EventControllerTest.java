package NFTTicket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/event")
@Controller
@RequiredArgsConstructor
public class EventControllerTest {

    @GetMapping(value = "/all")
    public String eventListAll() {return "/event/eventList_all";}

}
