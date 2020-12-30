package com.timatifey.springmafiabot;

import com.timatifey.springmafiabot.client.MessageNewObj;
import com.timatifey.springmafiabot.client.UserBody;
import com.timatifey.springmafiabot.client.VkClient;
import com.timatifey.springmafiabot.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private long idIterator = 0;

    private Map<String, GameSession> sessionList = new HashMap<>();

    @Autowired
    VkClient client;

    @PostMapping("/hello")
    public String greeting(@RequestBody MessageNewObj msg) {
        logger.info("PING PONG " + msg.toString());
        return "Hello " +  client.getUserInfo(msg.getUser_id()).getUsers().get(0).getFirstName();
    }

    @PostMapping("/create_room")
    public String createRoom(@RequestBody MessageNewObj msg) {
        GameSession session = new GameSession();
        CommandParser parser = new CommandParser(msg.getBody());
        List<String> arguments = parser.getArgs();
        if (arguments.size() == 1) {
            //only name
            session.setName(arguments.get(0));
        } else {
            if (arguments.size() == 2) {
                //with password
                session.setName(arguments.get(0));
                session.setPassword(arguments.get(1));
            } else {
                return "Invalid arguments";
            }
        }
        User user = new User();
        UserBody response = client.getUserInfo(msg.getUser_id()).getUsers().get(0);
        user.setName(response.getFirstName());
        user.setSurname(response.getLastName());
        user.setUserId(msg.getUser_id());
        session.setId(idIterator++);
        session.setLeaderUser(user);
        logger.info("Created room: " + session);
        sessionList.put(session.getName(), session);
        return "Created room: " + session;
    }

    @PostMapping("/games")
    public String gameList(@RequestBody MessageNewObj msg) {
        if (sessionList.isEmpty()) {
            return "Room list is empty";
        }
        return sessionList.values().stream()
                .map(session -> session.getName() + "(" + session.getLeaderUser() + "): "+ session.getPlayers() +"\n")
                .collect(Collectors.joining());
    }

    @PostMapping("/join")
    public String joinToRoom(@RequestBody MessageNewObj msg) {
        User user = new User();
        UserBody response = client.getUserInfo(msg.getUser_id()).getUsers().get(0);
        user.setName(response.getFirstName());
        user.setSurname(response.getLastName());
        user.setUserId(msg.getUser_id());

        CommandParser parser = new CommandParser(msg.getBody());
        List<String> arguments = parser.getArgs();
        String roomName = null;
        String password = null;

        if (arguments.size() == 1) {
            roomName = arguments.get(0);
        } else {
            if (arguments.size() == 2) {
                //with password
                roomName = arguments.get(0);
                password = arguments.get(1);
            } else {
                return "Invalid arguments";
            }
        }
        if (sessionList.containsKey(roomName)) {
            GameSession session = sessionList.get(roomName);
            if (session.getPassword() != null) {
                if (password != null) {
                    if (!session.getPassword().equals(password)) {
                       return "Invalid password";
                    }
                } else {
                    return "Room have a password, please retry with password";
                }
            }
            Player player = new Player();
            player.setUser(user);
            Role newRole = session.getRoleForNewPlayer();
            player.setRole(newRole);
            session.getPlayers().add(player);
            return "You was added to the game room";
        }
        return "Invalid room name";
    }

    @PostMapping("/gold_words")
    public String words(@RequestBody MessageNewObj msg) {
        return "Витя - лох";
    }

    @PostMapping("/aiproject")
    public String aiproject(@RequestBody MessageNewObj msg) {
        return "Витя - лох";
    }
}
