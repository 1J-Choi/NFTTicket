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
    public String eventListAll() {return "event/eventList_all";}

    @GetMapping(value = "/circle")
    public String eventListCircle() {return "event/eventList_circle";}

    @GetMapping(value = "/department")
    public String eventListDepartment() {return "event/eventList_department";}

    @GetMapping(value = "/others")
    public String eventListOthers() {return "event/eventList_others";}

}
