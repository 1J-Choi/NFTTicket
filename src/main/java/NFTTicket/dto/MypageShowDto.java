package NFTTicket.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MypageShowDto {
    private String nick;
    private String email;
    private String metaAddress;

    @QueryProjection // Querydsl 결과 조회 시 EventShowDto 객체로 바로 오도록 활용
    public MypageShowDto(String nick, String email, String metaAddress){
        this.nick = nick;
        this.email = email;
        this.metaAddress = metaAddress;
    }
    
}
