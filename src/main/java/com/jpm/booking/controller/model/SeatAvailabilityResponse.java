package com.jpm.booking.controller.model;

import java.util.List;

public class SeatAvailabilityResponse {
    private List<String> seatNumbers;

    public SeatAvailabilityResponse(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

}
