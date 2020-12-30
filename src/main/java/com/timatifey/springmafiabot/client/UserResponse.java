package com.timatifey.springmafiabot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserResponse {
    @JsonProperty("response")
    List<UserBody> users;
}
