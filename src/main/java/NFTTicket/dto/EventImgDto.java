package NFTTicket.dto;

import NFTTicket.entity.EventImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class EventImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgURL;

    private static ModelMapper modelMapper = new ModelMapper();

    public static EventImgDto of(EventImg eventImg) {
        return modelMapper.map(eventImg, EventImgDto.class);
    }
}
