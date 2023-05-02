package com.example.devoxx;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirlineController {
    @GetMapping("/details")
    public String airlineDetails() {
        return "Fly with us and discover a world of new horizons.";
    }

    @GetMapping("/greeting")
    public String greeting(@AuthenticationPrincipal(expression = "username") String username) {
        return "Welcome, " + username + "!";
    }

    @GetMapping("/seat")
    public String getSeat(@AuthenticationPrincipal Passenger user) {
        return user.getSeat();
    }

    @PostMapping("/seat")
    public void changeSeat(@AuthenticationPrincipal Passenger user, @RequestBody String newSeat) {
        // To constrain the project scope, data operations are omitted
        System.out.println("User: " + user + "updated to seat " + newSeat);
    }

    @GetMapping("/api/data")
    public String getAirlineData() {
        return "3% of passenger are checked-in";
    }
}
