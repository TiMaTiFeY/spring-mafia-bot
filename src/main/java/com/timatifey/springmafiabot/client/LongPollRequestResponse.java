package com.timatifey.springmafiabot.client;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class LongPollRequestResponse {
    private String ts;
    private List<Update> updates;
}