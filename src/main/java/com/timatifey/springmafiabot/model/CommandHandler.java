package com.timatifey.springmafiabot.model;

import com.timatifey.springmafiabot.client.ClientConfig;
import com.timatifey.springmafiabot.client.MessageNewObj;
import com.timatifey.springmafiabot.client.VkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    RestTemplate template;
    VkClient client;
    ClientConfig config;

    public CommandHandler(VkClient client, ClientConfig config, RestTemplate template) {
        this.client = client;
        this.config = config;
        this.template = template;
        logger.info(config.toString());
    }

    private final BlockingQueue<MessageNewObj> queue = new LinkedBlockingQueue<>();

    public void addCommand(MessageNewObj msg) {
        queue.add(msg);
    }

    @Override
    public void run() {
        while (true) {
            try {
                MessageNewObj command = queue.take();
                final StringBuilder requestURL = new StringBuilder(config.getBotHook());
                requestURL.append(command.getBody());
                logger.info("Trying to run "+command.getBody());
                logger.info("Trying POST: " + requestURL);
                final String answer = template.postForObject(requestURL.toString(), command, String.class);
                client.sendMessage(answer, command.getUser_id());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
