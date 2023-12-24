package com.jpm.booking.controller;

import com.jpm.booking.controller.model.ViewShowRequest;
import com.jpm.booking.controller.model.ViewShowResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface AdminApi {

    @PostMapping("/admin/setup")
    ResponseEntity<String> setup(@RequestBody ViewShowRequest request);

    @GetMapping("/admin/view/{showNumber}")
    ResponseEntity<List<ViewShowResponse>> view(@PathVariable("showNumber") String showNumber);

}
