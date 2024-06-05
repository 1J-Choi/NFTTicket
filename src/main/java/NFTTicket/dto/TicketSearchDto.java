package NFTTicket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketSearchDto {

    private String searchBy;

    private String searchQuery = "";
}
