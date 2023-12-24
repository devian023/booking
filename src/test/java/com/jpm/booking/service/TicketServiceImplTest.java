package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import com.jpm.booking.entity.Ticket;
import com.jpm.booking.repository.SeatRepository;
import com.jpm.booking.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    TicketRepository ticketRepository;

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    TicketServiceImpl ticketService;

    @Test
    void whenCancelledOutsideCancellationWindowThenReturnFalse() {
        var ticket = new Ticket();
        var seat = new Seat();
        var show = new Show();
        show.setCancellationWindowInMinutes(2);
        seat.setShow(show);
        ticket.getSeats().add(seat);
        ticket.setTransactionDateTime(LocalDateTime.now().minusMinutes(3));

        assertThat(ticketService.cancel(ticket)).isFalse();
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void whenCancelledWithinCancellationWindowThenReturnTrue() {
        var ticket = new Ticket();
        var seat = new Seat();
        var show = new Show();
        show.setCancellationWindowInMinutes(2);
        seat.setShow(show);
        ticket.getSeats().add(seat);
        ticket.setTransactionDateTime(LocalDateTime.now().minusMinutes(1));

        boolean returnValue = ticketService.cancel(ticket);

        verify(seatRepository).saveAll(anyList());

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository).save(ticketCaptor.capture());
        ticket = ticketCaptor.getValue();
        assertThat(ticket.getCancelled()).isTrue();

        assertThat(returnValue).isTrue();

    }

}