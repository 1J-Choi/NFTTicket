package NFTTicket.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketShowDto {
    private Long id;
    private String evName;
    private LocalDateTime date;
    private String place;
    private String nick;
    private String imgURL;

    @QueryProjection
    public TicketShowDto(Long id, String evName, LocalDateTime date, String place, String nick, String imgURL) {
        this.id = id;
        this.evName = evName;
        this.date = date;
        this.place = place;
        this.nick = nick;
        this.imgURL = imgURL;
    }
}