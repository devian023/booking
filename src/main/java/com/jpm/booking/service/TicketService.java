package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TicketService {

    Ticket book(String phoneNumber, List<Seat> seats);

    boolean cancel(Ticket ticket);

    Optional<Ticket> getTicket(long id);

    boolean hasActiveBooking(String showNumber, String phoneNumber);
}
