package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import com.jpm.booking.entity.Ticket;
import com.jpm.booking.repository.SeatRepository;
import com.jpm.booking.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public TicketServiceImpl(@NonNull final TicketRepository ticketRepository,
                             @NonNull final SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
    }
    @Override
    public Ticket book(String phoneNumber, List<Seat> seats) {
        var ticket = new Ticket();
        ticket.setBuyerPhoneNumber(phoneNumber);
        ticket.setTransactionDateTime(LocalDateTime.now());
        ticket.getSeats().addAll(seats);

        seats.forEach(seat -> seat.setTicket(ticket));
        seatRepository.saveAll(seats);

        return ticketRepository.save(ticket);
    }

    @Override
    public boolean cancel(Ticket ticket) {
        if(withinCancellationWindow(ticket)) {
            ticket.getSeats().forEach(seat -> seat.setTicket(null));
            seatRepository.saveAll(ticket.getSeats());
            ticket.getSeats().clear();
            ticket.setCancelled(true);
            ticketRepository.save(ticket);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Ticket> getTicket(long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public boolean hasActiveBooking(String showNumber, String phoneNumber) {
        return ticketRepository.findByBuyerPhoneNumber(phoneNumber)
                .stream().anyMatch(ticket ->
                                !ticket.getCancelled() &&
                                        ticket.getSeats()
                                                .stream().map(Seat::getShow)
                                                .map(Show::getShowNumber)
                                                .anyMatch(sn -> Objects.equals(sn, showNumber)));
    }

    private boolean withinCancellationWindow(Ticket ticket) {
        var show = ticket.getSeats().stream().findAny().map(Seat::getShow).orElseThrow();
        int cancellationWindowInMinutes = show.getCancellationWindowInMinutes();

        return ticket.getTransactionDateTime().plusMinutes(cancellationWindowInMinutes)
                .isAfter(LocalDateTime.now());
    }
}
