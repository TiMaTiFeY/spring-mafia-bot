package com.timatifey.springmafiabot.model;

public enum Role {
    MAFIA("Мафия"),
    DETECTIVE("Детектив"),
    DOCTOR("Доктор"),
    VILLAGER("Мирный");

    private String rusRoleName;
    Role(String rusRoleName) {
        this.rusRoleName = rusRoleName;
    }
}
