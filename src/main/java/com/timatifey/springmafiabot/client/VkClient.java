package com.timatifey.springmafiabot.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Random;

@Service
public class VkClient {
    private static final Logger logger = LoggerFactory.getLogger(VkClient.class);

    @Autowired
    RestTemplate template;

    @Autowired
    ClientConfig config;

    @PostConstruct
    void init() {
        LongPollServerResponse.Response server = getLongPollServer().response;
        logger.info(server.toString());
        LongPoll longPoll = new LongPoll(server.key, server.server, server.ts, this, config, template);
        Thread longPollThread = new Thread(longPoll);
        longPollThread.start();
    }

    private String getUrlRequest(String methodName, Map<String, Object> props) {
        StringBuilder properties = new StringBuilder();
        for (Map.Entry<String, Object> property : props.entrySet()) {
            properties.append("&");
            properties.append(property.getKey());
            properties.append("=");
            properties.append(property.getValue());
        }
        if (properties.length() > 0) {
            properties.deleteCharAt(0);
        }
        final StringBuilder requestURL = new StringBuilder(String.format(config.getRequestUrlTemplate(), methodName));
        if (!props.isEmpty()) {
            requestURL.append("?");
            requestURL.append(properties.toString());
        }
        return requestURL.toString();
    }

    public static String getPostUrl(String template, String methodName, Map<String, Object> props) {
        StringBuilder properties = new StringBuilder();
        for (Map.Entry<String, Object> property : props.entrySet()) {
            properties.append("&");
            properties.append(property.getKey());
            properties.append("=");
            properties.append(property.getValue());
        }
        if (properties.length() > 0) {
            properties.deleteCharAt(0);
        }
        final StringBuilder requestURL = new StringBuilder(String.format(template, methodName));
        if (!props.isEmpty()) {
            requestURL.append("?");
            requestURL.append(properties.toString());
        }
        return requestURL.toString();
    }

    public LongPollServerResponse getLongPollServer() {
        final String request = getUrlRequest(
                "groups.getLongPollServer",
                Map.of(
                        "group_id", config.getGroupId(),
                        "access_token", config.getToken(),
                        "v", config.getVersionAPI()
                )
        );
        logger.info("Trying GET REQUEST: " + request);
        final LongPollServerResponse response = template.getForObject(request, LongPollServerResponse.class);
        return response;
    }

    public LongPollRequestResponse getLongPollResponse(String server, String key, String ts) {
        final String request = String.format(
                config.getLongPollRequestTemplate(),
                server,
                key,
                ts
        );
        logger.info("Trying GET REQUEST: " + request);
        final LongPollRequestResponse response = template.getForObject(request, LongPollRequestResponse.class);
        return response;
    }

    public MessageResponse sendMessage(String message, long peerId) {
        Random random = new Random();
        final String request = getUrlRequest(
                "messages.send",
                Map.of(
                        "message", message,
                        "peer_id", peerId,
                        "random_id", random.nextInt(),
                        "access_token", config.getToken(),
                        "v", config.getVersionAPI()
                )
        );
        logger.info("Trying GET REQUEST: " + request);
        final MessageResponse response = template.getForObject(request, MessageResponse.class);
        return response;
    }

    public UserResponse getUserInfo(long id) {
        final String request = getUrlRequest(
                "users.get",
                Map.of(
                        "user_ids", id,
                        "access_token", config.getToken(),
                        "v", config.getVersionAPI()
                )
        );
        logger.info("Trying GET REQUEST: " + request);
        final UserResponse response = template.getForObject(request, UserResponse.class);
        return response;
    }
}
