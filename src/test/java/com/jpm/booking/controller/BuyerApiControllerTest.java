package com.jpm.booking.controller;

import com.jpm.booking.controller.model.BookRequest;
import com.jpm.booking.controller.model.CancelRequest;
import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Ticket;
import com.jpm.booking.service.SeatService;
import com.jpm.booking.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyerApiControllerTest {

    @Mock
    SeatService seatService;
    @Mock
    TicketService ticketService;

    @InjectMocks
    BuyerApiController buyerApi;

    @Test
    void whenShowNumberNotFoundThenNoContent() {
        when(seatService.getAvailability(anyString()))
                .thenReturn(Collections.emptyList());

        var response = buyerApi.availability("12345");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void whenShowNumberFoundThenOK() {
        var seat = new Seat();
        seat.setSeatNumber("A1");

        when(seatService.getAvailability(anyString()))
                .thenReturn(List.of(seat));

        var response = buyerApi.availability("12345");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getSeatNumbers()).hasSize(1);
    }

    @Test
    void whenMissingShowNumberThenBadRequest() {
        var request = new BookRequest();

        var response = buyerApi.book(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenMissingPhoneNumberThenBadRequest() {
        var request = new BookRequest();
        request.setShowNumber("12345");

        var response = buyerApi.book(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenEmptySeatThenBadRequest() {
        var request = new BookRequest();
        request.setShowNumber("12345");
        request.setBuyerPhoneNumber("09001234567");

        var response = buyerApi.book(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void okBooking() {
        var request = new BookRequest();
        request.setShowNumber("12345");
        request.setBuyerPhoneNumber("09001234567");
        request.setSeatNumbers(List.of("A1", "A2"));

        when(seatService.getSeats(anyString(), anyList()))
                .thenReturn(List.of(new Seat()));

        var ticket = new Ticket();
        ticket.setId(1000L);
        when(ticketService.book(anyString(), anyList()))
                .thenReturn(ticket);

        var response = buyerApi.book(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void whenMissingTicketNumberThenBadCancelRequest() {
        var request = new CancelRequest();

        var response = buyerApi.cancel(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenMissingPhoneNumberThenBadCancelRequest() {
        var request = new CancelRequest();
        request.setPhoneNumber("09001234567");

        var response = buyerApi.cancel(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenTicketNumberNotFoundThenNotFound() {
        var request = new CancelRequest();
        request.setPhoneNumber("09001234567");
        request.setTicketNumber(1000);

        when(ticketService.getTicket(anyLong()))
                .thenReturn(Optional.empty());

        var response = buyerApi.cancel(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void cancellationSuccess() {
        var request = new CancelRequest();
        request.setPhoneNumber("09001234567");
        request.setTicketNumber(1000);

        when(ticketService.getTicket(anyLong()))
                .thenReturn(Optional.of(new Ticket()));

        when(ticketService.cancel(any(Ticket.class)))
                .thenReturn(true);

        var response = buyerApi.cancel(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

}