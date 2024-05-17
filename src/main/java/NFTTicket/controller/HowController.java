package NFTTicket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HowController {

    @GetMapping(value = "/how")
    public String howtouse(){
        return "htu";
    }
}

