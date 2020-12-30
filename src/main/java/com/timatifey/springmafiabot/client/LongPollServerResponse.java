package com.timatifey.springmafiabot.client;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LongPollServerResponse {
    Response response;

    @Data
    @ToString
    class Response {
        String key;
        String server;
        String ts;
    }
}

