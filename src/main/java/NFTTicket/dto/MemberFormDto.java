package NFTTicket.dto;

import NFTTicket.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 16, message = "8자 이상, 16자 이하로 작성해주세요")
    private String password;

    @NotNull(message = "계정 권한을 선택하세요.")
    private Role role;

    @NotEmpty(message = "닉네임은 필수 입력값입니다.")
    private String nick;
}
