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

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name="memberImg_URL")
    private String imgURL;
}