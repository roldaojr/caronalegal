package br.edu.ifrn.tads.caronas.data;

public class RideRequest extends Entity {
    public enum Status { Cancelled, Pending, Accepted, Rejected }

    private Travel travel;
    private User user;
    private Status status;

    public RideRequest() {
        status = Status.Pending;
    }

    public RideRequest(Travel travel, User user) {
        status = Status.Pending;
        this.travel = travel;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public Status getStatus() {
        return status;
    }

    public void cancel() {
        status = Status.Cancelled;
    }

    public void aceept() {
        status = Status.Accepted;
    }

    public void reject() {
        status = Status.Rejected;
    }
}
