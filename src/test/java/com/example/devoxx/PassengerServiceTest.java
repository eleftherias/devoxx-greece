package com.example.devoxx;

import java.util.Set;

import com.example.devoxx.PassengerService.PassengerDetails;
import org.junit.jupiter.api.Test;

import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerServiceTest {
    @Test
    public void userGetsAuthorityPassengerByDefault() {
        Passenger user = new Passenger();
        PassengerDetails userDetails = new PassengerDetails(user);
        Set<String> authorities = AuthorityUtils.authorityListToSet(userDetails.getAuthorities());
        assertThat(authorities).containsExactly("PASSENGER");
    }

    @Test
    public void premiumUserGetsAuthorityPremium() {
        Passenger user = new Passenger();
        user.setPremium(true);
        PassengerDetails userDetails = new PassengerDetails(user);
        Set<String> authorities = AuthorityUtils.authorityListToSet(userDetails.getAuthorities());
        assertThat(authorities).containsExactlyInAnyOrder("PASSENGER", "PREMIUM");
    }

    @Test
    public void passengerDetailsHasTheSameValuesAsPassenger() {
        Passenger user = new Passenger();
        user.setUsername("user");
        user.setPassword("password");
        user.setSeat("8B");
        PassengerDetails userDetails = new PassengerDetails(user);
        assertThat(userDetails.getUsername()).isEqualTo("user");
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.getSeat()).isEqualTo("8B");
    }
}