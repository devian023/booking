package com.jpm.booking.controller;

import com.jpm.booking.controller.model.ViewShowRequest;
import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import com.jpm.booking.entity.Ticket;
import com.jpm.booking.service.ShowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminApiControllerTest {

    @Mock
    ShowService showService;

    @InjectMocks
    AdminApiController adminApi;

    @Test
    void whenNumberOfRowsExceedsMaxThenBadRequest() {
        var request = new ViewShowRequest();
        request.setNumberOfRows(27);

        var response = adminApi.setup(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(showService, never()).setup(anyString(), anyInt(), anyList());
    }

    @Test
    void whenNumberOfSeatsPerRowExceedsMaxThenBadRequest() {

        var request = new ViewShowRequest();
        request.setNumberOfRows(5);
        request.setNumberOfSeatsPerRow(15);

        var response = adminApi.setup(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(showService, never()).setup(anyString(), anyInt(), anyList());
    }

    @Test
    void whenShowNumberIsMissingThenBadRequest() {

        var request = new ViewShowRequest();
        request.setNumberOfRows(5);
        request.setNumberOfSeatsPerRow(5);

        var response = adminApi.setup(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(showService, never()).setup(anyString(), anyInt(), anyList());
    }

    @Test
    void whenCancellationWindowInMinutesIsMissingThenBadRequest() {

        var request = new ViewShowRequest();
        request.setNumberOfRows(5);
        request.setNumberOfSeatsPerRow(5);
        request.setShowNumber("12345");

        var response = adminApi.setup(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(showService, never()).setup(anyString(), anyInt(), anyList());
    }

    @Test
    void whenRequestIsOkThenResponseIsOk() {

        var request = new ViewShowRequest();
        request.setNumberOfRows(2);
        request.setNumberOfSeatsPerRow(2);
        request.setShowNumber("12345");
        request.setCancellationWindowInMinutes(2);

        var show = new Show();
        show.setShowNumber("12345");

        when(showService.setup(anyString(), anyInt(), anyList()))
                .thenReturn(show);

        var response = adminApi.setup(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ArgumentCaptor<List<Seat>> listCaptor = ArgumentCaptor.forClass(List.class);
        verify(showService).setup(anyString(), anyInt(), listCaptor.capture());
        List<Seat> seats = listCaptor.getValue();
        assertThat(seats)
                .hasSize(4);

        assertThat(seats.stream().map(Seat::getSeatNumber).collect(Collectors.toList()))
                .containsAll(List.of("A1", "A2", "B1", "B2"));

    }

    @Test
    void whenShowNotFoundThenResponseIsEmpty() {
        when(showService.view(anyString()))
                .thenReturn(Optional.empty());

        var response = adminApi.view("12345");
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void whenShowFoundThenResponseIsComplete() {

        List<Seat> seats = new ArrayList<>();

        var ticket = new Ticket();
        ticket.setId(1000L);
        ticket.setBuyerPhoneNumber("09001234567");

        var show = new Show();
        show.setShowNumber("12345");

        var seatA1 = new Seat();
        seatA1.setSeatNumber("A1");
        seatA1.setTicket(ticket);
        seatA1.setShow(show);
        var seatA2 = new Seat();
        seatA2.setSeatNumber("A2");
        seatA2.setTicket(ticket);
        seatA2.setShow(show);
        var seatB1 = new Seat();
        seatB1.setSeatNumber("B1");
        seatB1.setShow(show);
        var seatB2 = new Seat();
        seatB2.setSeatNumber("B2");
        seatB2.setShow(show);

        seats.add(seatA1);
        seats.add(seatA2);
        seats.add(seatB1);
        seats.add(seatB2);

        show.getSeats().addAll(seats);

        when(showService.view(anyString()))
                .thenReturn(Optional.of(show));

        var response = adminApi.view("12345");
        assertThat(response.getBody()).hasSize(4);
    }

}