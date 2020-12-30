package com.timatifey.springmafiabot;

import com.timatifey.springmafiabot.client.MessageNewObj;
import com.timatifey.springmafiabot.client.VkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    VkClient client;

    @PostMapping("/ping")
    public String ping(@RequestBody MessageNewObj msg) {
        logger.info("PING PONG " + msg.toString());
        return "pong";
    }

    @PostMapping("/hello")
    public String hello(@RequestBody MessageNewObj msg) {
        logger.info("PING PONG " + msg.toString());
        return "HELLO " + msg.getUser_id();
    }
}
