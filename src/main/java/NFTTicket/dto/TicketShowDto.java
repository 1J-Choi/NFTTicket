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
    private int number;
    private String imgURL;

    @QueryProjection
    public TicketShowDto(Long id, String evName, LocalDateTime date, String place, String nick, int number, String imgURL) {
        this.id = id;
        this.evName = evName;
        this.date = date;
        this.place = place;
        this.nick = nick;
        this.number = number;
        this.imgURL = imgURL;
    }
}