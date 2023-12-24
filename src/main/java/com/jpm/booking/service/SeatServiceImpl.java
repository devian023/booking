package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import com.jpm.booking.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class SeatServiceImpl implements SeatService {

    private final ShowRepository showRepository;

    @Autowired
    public SeatServiceImpl(@NonNull final ShowRepository showRepository) {
        this.showRepository = showRepository;
    }
    @Override
    public List<Seat> getAvailability(String showNumber) {
        Optional<Show> show = showRepository.findByShowNumber(showNumber);
        return show.map(value -> value.getSeats()
                .stream()
                .filter(seat -> isNull(seat.getTicket()) || seat.getTicket().getCancelled())
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Seat> getSeats(String showNumber, List<String> seatNumbers) {
        Optional<Show> show = showRepository.findByShowNumber(showNumber);
        return show.map(value -> value.getSeats()
                        .stream()
                        .filter(seat -> seatNumbers.contains(seat.getSeatNumber()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
