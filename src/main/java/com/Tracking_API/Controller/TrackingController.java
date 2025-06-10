package com.Tracking_API.Controller;

import com.Tracking_API.DTO.TrackingRequest;
import com.Tracking_API.DTO.TrackingResponse;
import com.Tracking_API.Service.TrackingNumService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/next-tracking-number")
public class TrackingController {

    private final TrackingNumService service;

    public TrackingController(TrackingNumService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<TrackingResponse> getTrackingNumber(@Valid TrackingRequest request) {
        return ResponseEntity.ok(service.generateTrackingNumber(request));
    }
}