package com.example.devoxx;

import com.example.devoxx.PassengerService.PassengerDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
class AirlineControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void detailsReturnsAirlineDetails() throws Exception {
        this.mockMvc.perform(get("/details"))
                .andExpect(status().isOk())
                .andExpect(content().string("Fly with us and discover a world of new horizons."));
    }

    @Test
    public void greetingReturnsWelcomeAndUsername() throws Exception {
        this.mockMvc.perform(get("/greeting").with(user("Alex")))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome, Alex!"));
    }

    @Test
    public void greetingWhenUnauthenticatedUserThenReturns401() throws Exception {
        this.mockMvc.perform(get("/greeting"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void getSeatReturnsPassengerSeatNumber() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setSeat("2A");
        this.mockMvc.perform(get("/seat").with(user(new PassengerDetails(passenger))))
                .andExpect(status().isOk())
                .andExpect(content().string("2A"));
    }

    @Test
    public void getSeatWhenUnauthenticatedUserThenReturns401() throws Exception {
        this.mockMvc.perform(get("/seat"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void postSeatWhenPassengerIsPremiumThenUpdatesSeat() throws Exception {
        Passenger premiumPassenger = new Passenger();
        premiumPassenger.setPremium(true);
        this.mockMvc.perform(post("/seat")
                        .content("1A")
                        .with(user(new PassengerDetails(premiumPassenger)))
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @Test
    public void postSeatWithoutCsrfThenReturns403() throws Exception {
        Passenger premiumPassenger = new Passenger();
        premiumPassenger.setPremium(true);
        this.mockMvc.perform(post("/seat")
                        .content("1A")
                        .with(user(new PassengerDetails(premiumPassenger)))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void postSeatWhenPassengerIsNotPremiumThenReturns403() throws Exception {
        Passenger passenger = new Passenger();
        this.mockMvc.perform(post("/seat")
                        .content("1A")
                        .with(user(new PassengerDetails(passenger)))
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void postSeatWhenUnauthenticatedUserThenReturns401() throws Exception {
        this.mockMvc.perform(post("/seat")
                        .content("1A")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}