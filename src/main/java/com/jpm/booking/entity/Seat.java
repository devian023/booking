package com.jpm.booking.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
public class Seat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @GeneratedValue()
    @Id
    Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    Ticket ticket;

    @ManyToOne(cascade = CascadeType.ALL)
    Show show;
    String seatNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
