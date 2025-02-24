package gg.lunar.hub.user.manager;

import gg.lunar.hub.user.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class UserManager {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public User createUser(UUID uuid, String name) {
        User user = new User(uuid, name);
        users.put(uuid, user);
        return user;
    }

    public void removeUser(UUID uuid) {
        users.remove(uuid);
    }

    public boolean hasUser(UUID uuid) {
        return users.containsKey(uuid);
    }
}