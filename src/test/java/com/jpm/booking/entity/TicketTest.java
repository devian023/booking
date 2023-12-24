package com.jpm.booking.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TicketTest {

    private Ticket ticket;

    @BeforeEach
    void instantiate() {
        ticket = new Ticket();
    }

    @Test
    void mustHaveBuyerPhoneNumber() {
        ticket.setBuyerPhoneNumber("09001234567");
        assertThat(ticket.getBuyerPhoneNumber()).isEqualTo("09001234567");
    }

    @Test
    void mustBeIdentifiedIfCancelled() {
        ticket.setCancelled(true);
        assertThat(ticket.getCancelled()).isTrue();
    }

    @Test
    void mustRecordTransactionDateAndTime() {
        ticket.setTransactionDateTime(LocalDateTime.now());
        assertThat(ticket.getTransactionDateTime()).isNotNull();
    }

    @Test
    void mustHaveAbilityToAddSingleSeat() {
        ticket.getSeats().add(new Seat());
        assertThat(ticket.getSeats()).hasSize(1);
    }

    @Test
    void mustHaveAbilityToAddMultipleSeats() {
        ticket.getSeats().addAll(List.of(new Seat(), new Seat()));
        assertThat(ticket.getSeats()).hasSize(2);
    }

}