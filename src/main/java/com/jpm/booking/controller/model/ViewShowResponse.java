package com.jpm.booking.controller.model;

import com.jpm.booking.entity.Seat;

import static java.util.Objects.nonNull;

public class ViewShowResponse {

    private String showNumber;
    private long ticketNumber;
    private String buyerPhoneNumber;
    private String seatNumber;

    public ViewShowResponse(Seat seat) {
        this.showNumber = seat.getShow().getShowNumber();
        this.seatNumber = seat.getSeatNumber();

        if(nonNull(seat.getTicket())) {
            var ticket = seat.getTicket();
            this.ticketNumber = ticket.getId();
            this.buyerPhoneNumber = ticket.getBuyerPhoneNumber();
        }
    }

    public String getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(String showNumber) {
        this.showNumber = showNumber;
    }

    public long getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(long ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public void setBuyerPhoneNumber(String buyerPhoneNumber) {
        this.buyerPhoneNumber = buyerPhoneNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumbers(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
