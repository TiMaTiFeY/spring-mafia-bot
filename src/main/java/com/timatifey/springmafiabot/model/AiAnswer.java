package com.timatifey.springmafiabot.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AiAnswer {
    private String userid;
    private Query query;

    @Data
    @ToString
    public class Query {
        private int status;
        private String description;
        private String aiml;
        private String emotion;
    }
}
