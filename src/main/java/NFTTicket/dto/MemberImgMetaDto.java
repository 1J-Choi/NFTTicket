package NFTTicket.dto;

import NFTTicket.entity.Member;
import NFTTicket.entity.MemberImg;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberImgMetaDto {
    private Long id;
    @Length(min = 26, max = 35, message = "26자 이상, 35자 이하로 작성해주세요")
    private String metaAddress;

    private Long memberImgId;

//    private static ModelMapper modelMapper = new ModelMapper();
//
//    public static MemberImgMetaDto of(Member member){
//        return modelMapper.map(member, MemberImgMetaDto.class);
//    }

    public void setMemberImgMetaDto(Member member, MemberImg memberImg){
        this.id = member.getId();
        this.metaAddress = member.getMetaAddress();
        this.memberImgId = memberImg.getId();
    }
}
