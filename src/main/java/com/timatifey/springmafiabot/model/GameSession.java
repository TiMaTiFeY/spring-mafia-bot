package com.timatifey.springmafiabot.model;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class GameSession {
    private long id;
    private String name;
    private List<Player> players = new ArrayList<>();
    private User leaderUser;
    private String password;

    public Role getRoleForNewPlayer() {
        return Role.VILLAGER;
    }
}
