package com.timatifey.springmafiabot.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Query {
    String ask;
    String userid;
    String key;
}
