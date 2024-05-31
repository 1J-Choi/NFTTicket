package NFTTicket.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;

public class MypageController {

    @GetMapping(value = "/mypage")
    public String mypageUser(){
        return "/mypage/mypage";
    }

}
