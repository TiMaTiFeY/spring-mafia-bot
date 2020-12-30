package com.timatifey.springmafiabot.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private long userId;
    private String name;
    private String surname;
}
