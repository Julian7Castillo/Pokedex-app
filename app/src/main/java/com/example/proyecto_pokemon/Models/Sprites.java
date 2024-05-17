package com.example.proyecto_pokemon.Models;

public class Sprites {
    private String normal;
    private String large;
    private String animated;

    public Sprites() {

    }

    public Sprites(String normal, String large, String animated) {
        this.normal = normal;
        this.large = large;
        this.animated = animated;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getAnimated() {
        return animated;
    }

    public void setAnimated(String animated) {
        this.animated = animated;
    }
}
