package com.jpm.booking.controller;

import com.jpm.booking.controller.model.ViewShowRequest;
import com.jpm.booking.controller.model.ViewShowResponse;
import com.jpm.booking.entity.Seat;
import com.jpm.booking.entity.Show;
import com.jpm.booking.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;

@RestController
public class AdminApiController implements AdminApi {

    private final ShowService showService;

    @Autowired
    public AdminApiController(@NonNull final ShowService showService) {
        this.showService = showService;
    }

    @Override
    public ResponseEntity<String> setup(ViewShowRequest request) {

        int MAX_ROW_COUNT = 26;
        int MAX_ROW_SIZE = 10;
        if(request.getNumberOfRows() > MAX_ROW_COUNT ||
                request.getNumberOfSeatsPerRow() > MAX_ROW_SIZE ||
                isBlank(request.getShowNumber()) ||
                request.getCancellationWindowInMinutes() == 0
                ) {
            return ResponseEntity.badRequest().build();
        }


        List<Seat> seats = createSeats(request.getNumberOfRows(), request.getNumberOfSeatsPerRow());
        Show show = showService.setup(request.getShowNumber(), request.getCancellationWindowInMinutes(), seats);


        return ResponseEntity.created(URI.create("/admin/view/"+show.getShowNumber())).build();
    }

    List<Seat> createSeats(int rowCount, int rowSize) {
        List<Seat> seatList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 1; j <= rowSize ; j++) {
                var seat = new Seat();
                seat.setSeatNumber(String.valueOf((char)(i + 65)) + j);
                seatList.add(seat);
            }
        }
        return seatList;
    }

    @Override
    public ResponseEntity<List<ViewShowResponse>> view(String showNumber) {

        List<Seat> seats = showService.view(showNumber)
                .map(Show::getSeats)
                .orElse(Collections.emptyList());

        List<ViewShowResponse> list = seats.stream()
                .map(ViewShowResponse::new)
                .toList();

        return ResponseEntity.ok(list);
    }
}
