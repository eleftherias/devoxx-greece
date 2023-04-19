package com.example.devoxx;

public class Passenger {
    private String username;

    private String password;

    private String seat;

    private boolean premium;

    public Passenger() {
    }

    public Passenger(Passenger user) {
        this.username = user.username;
        this.password = user.password;
        this.seat = user.seat;
        this.premium = user.premium;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

}
