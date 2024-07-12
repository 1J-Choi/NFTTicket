package NFTTicket.dto;

import NFTTicket.constant.TransactionStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventShowDto {
    private Long id;
    private String evName;
    private LocalDateTime date;
    private String place;
    private String nick;
    private int number;
    private String imgURL;
    private TransactionStatus transNow;

    private int nowNumber;

    @QueryProjection // Querydsl 결과 조회 시 EventShowDto 객체로 바로 오도록 활용
    public EventShowDto(Long id, String evName, LocalDateTime date, String place, String nick,
                        int number, String imgURL, TransactionStatus transNow, int nowNumber){
        this.id = id;
        this.evName = evName;
        this.date = date;
        this.place = place;
        this.nick = nick;
        this.number = number;
        this.imgURL = imgURL;
        this.transNow = transNow;
        this.nowNumber = nowNumber;
    }
}
