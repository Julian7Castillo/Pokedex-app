package com.example.proyecto_pokemon.Models;

public class Chat {
    String email, username, mesage, date, time;

    public Chat() {

    }

    public Chat(String email, String username, String mesage, String date, String time) {
        this.email = email;
        this.username = username;
        this.mesage = mesage;
        this.date = date;
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
