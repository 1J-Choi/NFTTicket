package NFTTicket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDto {
    @NotNull(message = "이벤트 아이디는 필수 입력 값입니다.")
    private Long eventId;
}
