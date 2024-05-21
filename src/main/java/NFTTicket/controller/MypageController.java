package NFTTicket.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;

public class MypageController {
    @GetMapping(value= "/mypage")
    public String mypageRole(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/mypage";
        } else if (request.isUserInRole("ROLE_OWNER")) {
            return "redirect:/owner/mapage";
        }
        return "redirect:/user/mypage";
    }

    @GetMapping(value = "/user/mypage")
    public String mypageUser(){
        return "/mypage/mypageUser";
    }

    @GetMapping(value = "/owner/mypage")
    public String mypageOwner(){
        return "/mypage/mypageOwner";
    }

    @GetMapping(value = "/admin/mypage")
    public String mypageAdmin(){
        return "/mypage/mypageAdmin";
    }
}
