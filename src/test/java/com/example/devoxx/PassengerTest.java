package com.example.devoxx;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerTest {
    @Test
    public void constructorCreatesCopy() {
        Passenger user = new Passenger();
        user.setUsername("user");
        user.setPassword("password");
        user.setSeat("8A");
        user.setPremium(true);
        Passenger copy = new Passenger(user);
        assertThat(user).usingRecursiveComparison().isEqualTo(copy);
    }
}