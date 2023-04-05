package com.racetacticbattle.game.Models;

public class User {
    String username, email, friends;
    int wins, loses;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.friends = "";
        this.wins = 0;
        this.loses = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public int getWins() {
        return wins;
    }

    public void setWins() {
        this.wins += 1;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses() {
        this.loses += 1;
    }
}
