package NFTTicket.controller;

import NFTTicket.dto.TicketDto;
import NFTTicket.service.MemberService;
import NFTTicket.service.TicketBoxService;
import NFTTicket.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    private final MemberService memberService;
    private final TicketBoxService ticketBoxService;

    @PostMapping(value = "/safeMint")
    public @ResponseBody ResponseEntity ticketSafeMint(@RequestBody @Valid TicketDto ticketDto, BindingResult bindingResult, Principal principal) {
        /*principal: 현재 로그인 된 정보를 가지고 있다 @RequestBody: event_**.html의 function order()의 paramData 가 온다*/
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long ticketId;
        try {
            ticketId = ticketService.buyTicket(ticketDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(ticketId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/ticket/{ticketId}")
    public @ResponseBody ResponseEntity deleteTicket(@PathVariable("ticketId") Long ticketId, Principal principal) {
        // Admin인지 확인하는 부분
        if(!memberService.validateAdmin(principal.getName())){
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<Long>(ticketId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/ticket/{ticketId}")
    public @ResponseBody ResponseEntity deleteUserTicket(@PathVariable("ticketId") Long ticketId, Principal principal) {
        // 사용자의 TicketBox 내의 Ticket인지 확인
        if(!ticketBoxService.validateTicketBox(ticketId, principal.getName())){
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<Long>(ticketId, HttpStatus.OK);
    }
}
