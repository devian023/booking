package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ShowService {

    Show setup(String showNumber, int cancellationWindowInMinutes, List<Seat> seats);

    Optional<Show> view(String showNumber);
}
