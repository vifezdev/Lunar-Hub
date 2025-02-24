package gg.lunar.hub.user;

import java.util.UUID;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class User {

    private final UUID uuid;
    private String name;
    private boolean vanished;
    private boolean flightEnabled;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.vanished = false;
        this.flightEnabled = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }

    public boolean isFlightEnabled() {
        return flightEnabled;
    }

    public void setFlightEnabled(boolean flightEnabled) {
        this.flightEnabled = flightEnabled;
    }
}