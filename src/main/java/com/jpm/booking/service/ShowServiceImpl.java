package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import com.jpm.booking.repository.SeatRepository;
import com.jpm.booking.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl implements ShowService{

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public ShowServiceImpl(@NonNull final ShowRepository showRepository,
                           @NonNull final SeatRepository seatRepository) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public Show setup(String showNumber, int cancellationWindowInMinutes, List<Seat> seats) {
        var show = new Show();
        show.setShowNumber(showNumber);
        show.setCancellationWindowInMinutes(cancellationWindowInMinutes);
        show.getSeats().addAll(seats);
        seats.forEach(seat -> seat.setShow(show));
        seatRepository.saveAll(seats);
        return showRepository.save(show);
    }

    @Override
    public Optional<Show> view(String showNumber) {
        return showRepository.findByShowNumber(showNumber);
    }
}
