package gg.lunar.hub.user.manager;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.config.ConfigFile;
import gg.lunar.hub.user.User;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class UserManager {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();
    private final ConfigFile usersFile;

    public UserManager() {
        this.usersFile = LunarHub.getInstance().getUsersFile();
    }

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public User createUser(UUID uuid, String name) {
        FileConfiguration config = usersFile.getConfig();

        boolean hidingPlayers = config.getBoolean("users." + uuid + ".hiding-players", false);

        User user = new User(uuid, name);
        user.setHidingPlayers(hidingPlayers);

        users.put(uuid, user);
        return user;
    }

    public void removeUser(UUID uuid) {
        saveUser(uuid);
        users.remove(uuid);
    }

    public boolean hasUser(UUID uuid) {
        return users.containsKey(uuid);
    }

    public void saveUser(UUID uuid) {
        User user = users.get(uuid);
        if (user == null) return;

        FileConfiguration config = usersFile.getConfig();
        config.set("users." + uuid + ".name", user.getName());
        config.set("users." + uuid + ".hiding-players", user.isHidingPlayers());

        usersFile.save();
    }
}