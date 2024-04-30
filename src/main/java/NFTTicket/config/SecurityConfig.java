package NFTTicket.config;

import NFTTicket.constant.Role;
import NFTTicket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    @Autowired
    private MemberService memberService;

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/img/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
        );
        http.authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
//                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/", "/member/**").permitAll()
//                                .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole("USER")
                                .requestMatchers("/owner/**", "/api/v1/owner/**").hasRole("OWNER")
                                .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
        );
        http.exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        );
        http.formLogin((formLogin) ->
                        formLogin
                                .loginPage("/member/login")
                                .usernameParameter("email")
                                .failureUrl("/member/login/error")
                                .passwordParameter("password")
                                .loginProcessingUrl("/member/login")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
        );
        http.logout((logoutConfig) ->
                        logoutConfig
                                .logoutRequestMatcher(new AntPathRequestMatcher("member/logout"))
                                .logoutSuccessUrl("/")
        );
        http.securityContext((securityContext) ->
                securityContext.securityContextRepository(new HttpSessionSecurityContextRepository()));
        http.userDetailsService(memberService);

        return http.build();
    }
}
