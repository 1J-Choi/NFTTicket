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
    private String nick;
    private String email;
    private String metaAddress;

    private static ModelMapper modelMapper = new ModelMapper();

    public static MypageShowDto of(Member member) {
        return modelMapper.map(member, MypageShowDto.class);
    }
    
}
