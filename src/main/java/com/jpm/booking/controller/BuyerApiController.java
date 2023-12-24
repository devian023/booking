package com.jpm.booking.controller;

import com.jpm.booking.controller.model.BookRequest;
import com.jpm.booking.controller.model.CancelRequest;
import com.jpm.booking.controller.model.SeatAvailabilityResponse;
import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Ticket;
import com.jpm.booking.service.SeatService;
import com.jpm.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

@RestController
public class BuyerApiController implements BuyerApi {

    private final TicketService ticketService;
    private final SeatService seatService;

    @Autowired
    public BuyerApiController(@NonNull final TicketService ticketService,
                              @NonNull final SeatService seatService) {
        this.ticketService = ticketService;
        this.seatService = seatService;
    }
    @Override
    public ResponseEntity<SeatAvailabilityResponse> availability(String showNumber) {
        var list = seatService.getAvailability(showNumber)
                .stream().map(Seat::getSeatNumber)
                .toList();

        if(list.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(new SeatAvailabilityResponse(list));
    }

    @Override
    public ResponseEntity<String> book(BookRequest request) {
        if( isBlank(request.getShowNumber()) ||
            isBlank(request.getBuyerPhoneNumber()) ||
            isNull(request.getSeatNumbers()) ||
            request.getSeatNumbers().isEmpty()) {
            return ResponseEntity.badRequest().body("Missing required fields.");
        }

        if(ticketService.hasActiveBooking(request.getShowNumber(), request.getBuyerPhoneNumber())) {
            return ResponseEntity.badRequest().body("Cancel active booking first.");
        }
        List<Seat> seats = seatService.getSeats(request.getShowNumber(), request.getSeatNumbers());
        Ticket ticket = ticketService.book(request.getBuyerPhoneNumber(), seats);

        return ResponseEntity.created(URI.create("/buyer/book/"+ticket.getId())).build();
    }

    @Override
    public ResponseEntity<String> cancel(CancelRequest request) {
        if( request.getTicketNumber() == 0 ||
                isBlank(request.getPhoneNumber())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Ticket> optTicket = ticketService.getTicket(request.getTicketNumber());
        if(optTicket.isEmpty()) return ResponseEntity.notFound().build();

        ticketService.cancel(optTicket.get());

        return ResponseEntity.accepted().build();
    }
}
