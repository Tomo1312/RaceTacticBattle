package com.racetacticbattle.game.Models;

public class User {
    String username, email, friends, room, wins, loses;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.friends = "";
        this.wins = "0";
        this.loses = "0";
        this.room = "";
    }

//    public User(String username) {
//        this.username = username;
//        this.email = "";
//        this.friends = "";
//        this.wins = 0;
//        this.loses = 0;
//        this.room = "";
//    }

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

    public String getWins() {
        return wins;
    }

    public void setWins() {
        this.wins = String.valueOf(Integer.parseInt(this.wins) + 1);
    }

    public String getLoses() {
        return loses;
    }

    public void setLoses() {
        this.loses = String.valueOf(Integer.parseInt(this.loses) + 1);;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
