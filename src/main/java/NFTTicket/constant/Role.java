package NFTTicket.constant;

public enum Role {
    USER, OWNER, ADMIN;

    public String getAuthority() {
        return this.name();
    }
}
