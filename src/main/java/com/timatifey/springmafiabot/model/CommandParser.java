package com.timatifey.springmafiabot.model;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
public class CommandParser {
    private String body;
    private String command;
    private List<String> args;

    public CommandParser(String body) {
        this.body = body;
        String[] parts = body.split("\\s+");
        this.command = parts[0];
        this.args = Arrays.stream(parts)
                .skip(1)
                .collect(Collectors.toList());
    }
}
