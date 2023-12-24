package com.jpm.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Show implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @GeneratedValue
    @Id
    Long id;

    private String showNumber;

    private int cancellationWindowInMinutes;

    @OneToMany(mappedBy = "show")
    List<Seat> seats = new ArrayList<>();

    public List<Seat> getSeats() {
        return seats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setShowNumber(String showNumber) {
        this.showNumber = showNumber;
    }

    public String getShowNumber() {
        return showNumber;
    }

    public int getCancellationWindowInMinutes() {
        return cancellationWindowInMinutes;
    }

    public void setCancellationWindowInMinutes(int cancellationWindowInMinutes) {
        this.cancellationWindowInMinutes = cancellationWindowInMinutes;
    }
}
