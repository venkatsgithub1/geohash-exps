package org.example.controllers;

import org.example.service.H3Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/h3")
public class H3Controller {
    private final H3Service h3Service;

    public H3Controller(H3Service h3Service) {
        this.h3Service = h3Service;
    }

    @GetMapping
    public List<String> getNearbyVenues(@RequestParam("latitude") double latitude,
                                        @RequestParam("longitude") double longitude) {
        return h3Service.findNearbyEstablishments(latitude, longitude, 9, 0);
    }
}
