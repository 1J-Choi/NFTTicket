package NFTTicket.entity;

import NFTTicket.constant.SafeMintStatus;
import NFTTicket.constant.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="ticket")
@Getter
@Setter
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticketBox_id")
    private TicketBox ticketBox;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    private SafeMintStatus safeMint;

    private String NFT;

    public static Ticket createTicket(Event event, TicketBox ticketBox){
        Ticket ticket = new Ticket();
        ticket.setTicketBox(ticketBox);
        ticket.setEvent(event);
        ticket.setSafeMint(SafeMintStatus.N);
        return ticket;
    }

    public void confirmTicketSafeMint(){
        this.safeMint = SafeMintStatus.Y;
    }
}
