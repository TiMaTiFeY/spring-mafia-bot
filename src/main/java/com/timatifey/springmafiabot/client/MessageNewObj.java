package com.timatifey.springmafiabot.client;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MessageNewObj {
    private long id;
    private long date;
    private long out;
    private long user_id;
    private long read_state;
    private String title;
    private String body;
    private int[] owner_ids;
}