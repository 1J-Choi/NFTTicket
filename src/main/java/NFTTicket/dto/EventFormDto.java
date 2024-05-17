package NFTTicket.dto;

import NFTTicket.constant.EventCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventFormDto {
    @NotEmpty(message = "이벤트 이름을 적어주세요")
    private String evName;

    @NotNull(message = "인원수를 적어주세요")
    private int number;

    private String place;

    @NotNull(message = "날짜를 지정해주세요")
    private LocalDateTime date;

    @NotBlank(message = "이벤트 세부사항을 작성해주세요")
    private String script;

    private EventCategory category;

    private EventImgDto eventImgDto;
}
