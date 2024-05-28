package NFTTicket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="memberImg")
@Getter
@Setter
@ToString
public class MemberImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberImg_id")
    private Long id;

    private  String imgName; // 이미지 파일명

    private  String oriImgName; // 원본 이미지 파일명

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name="memberImg_URL")
    private String imgURL;

    public void updateMemberImg(String oriImgName, String imgName, String imgURL){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgURL = imgURL;
    }
}