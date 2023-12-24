package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {

    List<Seat> getAvailability(String showNumber);

    List<Seat> getSeats(String showNumber, List<String> seatNumbers);
}
