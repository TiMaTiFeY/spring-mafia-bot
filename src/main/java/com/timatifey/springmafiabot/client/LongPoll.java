package com.timatifey.springmafiabot.client;

import com.timatifey.springmafiabot.model.CommandHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Data
public class LongPoll implements Runnable {
    private String key;
    private String server;
    private String ts;

    VkClient client;
    CommandHandler commandHandler;

    private boolean stop;

    private static final Logger logger = LoggerFactory.getLogger(LongPoll.class);

    public LongPoll(String key, String server, String ts, VkClient client, ClientConfig config, RestTemplate template) {
        this.key = key;
        this.server = server;
        this.ts = ts;
        this.client = client;
        this.commandHandler = new CommandHandler(client, config, template);
        new Thread(this.commandHandler).start();
    }

    @Override
    public void run() {
        while (!stop) {
            LongPollRequestResponse response = client.getLongPollResponse(server, key, ts);
            logger.info(response.toString());
            this.ts = response.getTs();
            for (MessageNewObj msg : response.getUpdates().stream()
                    .map(Update::getObject)
                    .collect(Collectors.toSet())) {
                    if (msg.getBody().startsWith("/")) {
                        commandHandler.addCommand(msg);
                    }
            }
        }
    }

}
