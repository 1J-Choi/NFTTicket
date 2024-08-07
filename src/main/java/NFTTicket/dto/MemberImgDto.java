package NFTTicket.dto;

import NFTTicket.entity.EventImg;
import NFTTicket.entity.Member;
import NFTTicket.entity.MemberImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgURL;

//    private static ModelMapper modelMapper = new ModelMapper();
//
//    public static MemberImgDto of(MemberImg memberImg) {
//        return modelMapper.map(memberImg, MemberImgDto.class);
//    }

    public void setMemberImgDto(MemberImg memberImg){
        this.id = memberImg.getId();
        this.imgName = memberImg.getImgName();
        this.oriImgName = memberImg.getOriImgName();
        this.imgURL = memberImg.getImgURL();
    }
}
