package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import com.jpm.booking.entity.Ticket;
import com.jpm.booking.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Mock
    ShowRepository showRepository;

    @InjectMocks
    SeatServiceImpl seatService;

    @Test
    void whenShowIsNotFoundThenReturnEmptyList() {

        when(showRepository.findByShowNumber(anyString()))
                .thenReturn(Optional.empty());

        List<Seat> availableSeats = seatService.getAvailability("12345");

        assertThat(availableSeats).isEmpty();
    }

    @Test
    void givenFourSeatsWhenTwoAreReservedThenReturnTwoSeats() {
        List<Seat> seats = prepareFourSeats();
        seats.get(0).setTicket(new Ticket());
        seats.get(1).setTicket(new Ticket());

        var show = new Show();
        show.getSeats().addAll(seats);

        when(showRepository.findByShowNumber(anyString()))
                .thenReturn(Optional.of(show));

        List<Seat> availableSeats = seatService.getAvailability("12345");

        assertThat(availableSeats).hasSize(2);
    }

    @Test
    void givenFourSeatsWhenTwoAreReservedButOneIsCancelledThenReturnThreeSeats() {
        List<Seat> seats = prepareFourSeats();
        seats.get(0).setTicket(new Ticket());

        var cancelledTicket = new Ticket();
        cancelledTicket.setCancelled(true);
        seats.get(1).setTicket(cancelledTicket);

        var show = new Show();
        show.getSeats().addAll(seats);

        when(showRepository.findByShowNumber(anyString()))
                .thenReturn(Optional.of(show));

        List<Seat> availableSeats = seatService.getAvailability("12345");

        assertThat(availableSeats).hasSize(3);
    }

    List<Seat> prepareFourSeats() {

        List<Seat> seats = new ArrayList<>();
        var seatA1 = new Seat();
        seatA1.setSeatNumber("A1");
        var seatA2 = new Seat();
        seatA2.setSeatNumber("A2");
        var seatB1 = new Seat();
        seatB1.setSeatNumber("B1");
        var seatB2 = new Seat();
        seatB2.setSeatNumber("B2");

        seats.add(seatA1);
        seats.add(seatA2);
        seats.add(seatB1);
        seats.add(seatB2);

        return seats;
    }

}