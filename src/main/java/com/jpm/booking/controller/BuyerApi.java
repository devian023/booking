package com.jpm.booking.controller;

import com.jpm.booking.controller.model.BookRequest;
import com.jpm.booking.controller.model.CancelRequest;
import com.jpm.booking.controller.model.SeatAvailabilityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public interface BuyerApi {

    @GetMapping("/buyer/availability/{showNumber}")
    ResponseEntity<SeatAvailabilityResponse> availability(@PathVariable("showNumber") String showNumber);

    @PostMapping("/buyer/book")
    ResponseEntity<String> book(@RequestBody BookRequest request);

    @PutMapping("/buyer/cancel")
    ResponseEntity<String> cancel(@RequestBody CancelRequest request);
}
