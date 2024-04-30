package NFTTicket.controller;

import NFTTicket.constant.Role;
import NFTTicket.dto.MemberFormDto;
import NFTTicket.entity.Member;
import NFTTicket.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(
        locations = {"classpath:application-test.properties"}
)
class MemberControllerTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;

    MemberControllerTest() {
    }

    public Member createMember(String email, String password) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setNick("홍길동");
        memberFormDto.setPassword(password);
        memberFormDto.setRole(Role.USER);
        Member member = Member.createMember(memberFormDto, this.passwordEncoder);
        return this.memberService.saveMember(member);
    }


    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@email.com";
        String password = "12345678";
        this.createMember(email, password);
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().userParameter("email")
                .loginProcessingUrl("/member/login").user(email).password(password)).andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String email = "test@email.com";
        String password = "12345678";
        this.createMember(email, password);
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().userParameter("email")
                .loginProcessingUrl("/member/login").user(email).password("12345")).andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    public void logoutSuccessTest() throws Exception {
        String email = "test@email.com";
        String password = "12345678";
        this.createMember(email, password);
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().userParameter("email")
                .loginProcessingUrl("/member/login").user(email).password(password));
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.logout()).andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}