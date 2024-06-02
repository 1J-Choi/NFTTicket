package NFTTicket.controller;

import NFTTicket.dto.MemberImgMetaDto;
import NFTTicket.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;


@Controller
@RequiredArgsConstructor
public class MypageController {
    private final MemberService memberService;

    @GetMapping(value = "/mypage")
    public String mypageUser(){
        return "mypage/mypage";
    }

    @GetMapping("/imgmeta/{memberId}")
    public String imgMeta(@PathVariable("memberId")Long memberId, Model model){
        try{
            MemberImgMetaDto memberImgMetaDto = memberService.getMemberDtl(memberId);
            model.addAttribute("memberImgMetaDto", memberImgMetaDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 계정입니다.");
            return "/";
        }

        return "mypage/myImgMeta";
    }

    @PostMapping("/imgmeta/{memberId}")
    public String imgMetaUpdate(@Valid MemberImgMetaDto memberImgMetaDto, BindingResult bindingResult,
                                @RequestParam("memberImgFile") MultipartFile memberImgFile, Model model){
        if (bindingResult.hasErrors()) {
            return "mypage/myImgMeta";
        }
        try{
            memberService.updateMember(memberImgMetaDto, memberImgFile);
        }catch (Exception e){
            model.addAttribute("errorMessage", "회원 정보 수정 중 에러가 발생하였습니다.");
            return "mypage/myImgMeta";
        }
        return "redirect:/"; // 다시 실행 /
    }

}
