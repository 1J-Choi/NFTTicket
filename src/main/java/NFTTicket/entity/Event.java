package NFTTicket.entity;

import NFTTicket.constant.EventCategory;
import NFTTicket.constant.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name="event")
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private long id;            //DB 작성상에는 int로 선언했지만 long 타입이 더 적합

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private TransactionStatus tranNow;

    private String evName;

    private int number;

    private String place;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String script;

    @Enumerated(EnumType.STRING)
    private EventCategory category;
}
    //ENUM 같은 경우는 아예 따로 작성