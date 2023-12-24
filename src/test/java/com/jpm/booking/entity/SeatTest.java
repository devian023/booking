package com.jpm.booking.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SeatTest {

    private Seat seat;

    @BeforeEach
    void instantiate() {
        seat = new Seat();
    }

    @Test
    void mustHaveSeatNumber() {
        seat.setSeatNumber("A1");
        assertThat(seat.getSeatNumber()).isEqualTo("A1");
    }

}
