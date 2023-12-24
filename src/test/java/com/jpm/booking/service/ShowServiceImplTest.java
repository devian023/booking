package com.jpm.booking.service;

import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import com.jpm.booking.repository.SeatRepository;
import com.jpm.booking.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShowServiceImplTest {

    @Mock
    ShowRepository showRepository;

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    ShowServiceImpl showService;

    @Test
    void mustCreateShowEntryWithAllocatedSeats() {
        List<Seat> seats = new ArrayList<>();
        var seatA1 = new Seat();
        seatA1.setSeatNumber("A1");
        var seatA2 = new Seat();
        seatA1.setSeatNumber("A2");
        var seatB1 = new Seat();
        seatA1.setSeatNumber("B1");
        var seatB2 = new Seat();
        seatA1.setSeatNumber("B2");

        seats.add(seatA1);
        seats.add(seatA2);
        seats.add(seatB1);
        seats.add(seatB2);

        showService.setup("DEC23XXXXXX", 2, seats);

        verify(seatRepository).saveAll(anyList());

        ArgumentCaptor<Show> showCaptor = ArgumentCaptor.forClass(Show.class);
        verify(showRepository).save(showCaptor.capture());
        Show capturedShow = showCaptor.getValue();

        // assert argument before save
        assertThat(capturedShow.getShowNumber()).isEqualTo("DEC23XXXXXX");
        assertThat(capturedShow.getSeats()).hasSize(4);
    }

}