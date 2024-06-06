package NFTTicket.dto;

import java.time.LocalDateTime;

import NFTTicket.entity.Member;
import NFTTicket.entity.MemberImg;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;


@Getter
@Setter
public class MypageShowDto {
    private Long id;
    private String nick;
    private String email;
    private String metaAddress;
    private String imgURL;

    public MypageShowDto(Member member, MemberImg memberImg){
        this.id = member.getId();
        this.nick = member.getNick();
        this.email = member.getEmail();
        this.metaAddress = member.getMetaAddress();
        this.imgURL = memberImg.getImgURL();
    }
    
}
