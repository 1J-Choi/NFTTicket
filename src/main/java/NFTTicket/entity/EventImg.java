package NFTTicket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="eventImg")
@Getter
@Setter
@ToString
public class EventImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eventImg_id")
    private Long id;

    private  String imgName; // 이미지 파일명

    private  String oriImgName; // 원본 이미지 파일명

    @OneToOne
    @JoinColumn(name="event_id")
    private Event event;

    @Column(name="eventImg_URL")
    private String imgURL;

    public void updateEventImg(String oriImgName, String imgName, String imgURL) {
        this.oriImgName=oriImgName;
        this.imgName=imgName;
        this.imgURL=imgURL;
    }
}
