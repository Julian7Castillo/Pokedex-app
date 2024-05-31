package com.example.proyecto_pokemon.Models;

public class Pokemon {
    private String national_number;
    private Evolution evolution;
    private String name;
    private Sprites sprites;
    private String[] type;
    private String total;
    private String hp;
    private String attack;
    private String defense;
    private String sp_atk;
    private String sp_def;
    private String speed;

    public Pokemon() {
    }

    public Pokemon(String national_number, Evolution evolution, String name, Sprites sprites, String[] type, String total, String hp, String attack, String defense, String sp_atk, String sp_def, String speed) {
        this.national_number = national_number;
        this.evolution = evolution;
        this.name = name;
        this.sprites = sprites;
        this.type = type;
        this.total = total;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.sp_atk = sp_atk;
        this.sp_def = sp_def;
        this.speed = speed;
    }

    public String getNational_number() {
        return national_number;
    }

    public void setNational_number(String national_number) {
        this.national_number = national_number;
    }

    public Evolution getEvolution() {
        return evolution;
    }

    public void setEvolution(Evolution evolution) {
        this.evolution = evolution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getSp_atk() {
        return sp_atk;
    }

    public void setSp_atk(String sp_atk) {
        this.sp_atk = sp_atk;
    }

    public String getSp_def() {
        return sp_def;
    }

    public void setSp_def(String sp_def) {
        this.sp_def = sp_def;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
