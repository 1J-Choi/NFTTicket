package NFTTicket.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MypageController {

    @GetMapping(value = "/mypage")
    public String mypageUser(){
        return "mypage/mypage";
    }

}
