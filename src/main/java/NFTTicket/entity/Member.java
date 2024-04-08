package NFTTicket.entity;

import NFTTicket.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING) // Enum을 문자열 형태로 전환하여 DB 저장
    private Role role;

    private String nick;

    @Column(unique = true) // 이메일 중복 방지
    private String eMail;

    private String pw;

    private String metaAdress;
}
