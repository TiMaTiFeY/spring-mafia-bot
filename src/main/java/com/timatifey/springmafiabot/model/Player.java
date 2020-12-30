package com.timatifey.springmafiabot.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Player {
    private User user;
    private Role role;
}
