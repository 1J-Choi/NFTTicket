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

    @OneToOne
    @JoinColumn(name="event_id")
    private Event event;

    @Column(name="eventImg_URL")
    private String imgURL;
}
