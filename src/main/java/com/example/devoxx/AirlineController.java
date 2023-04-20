package com.example.devoxx;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirlineController {
    @GetMapping("/details")
    public String airlineDetails() {
        return "Fly with us and discover a world of new horizons.";
    }
}
