package gg.lunar.hub.spawn;

import gg.lunar.hub.LunarHub;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnManager {

    private final LunarHub plugin;
    private Location spawnLocation;

    public SpawnManager(LunarHub plugin) {
        this.plugin = plugin;
        loadSpawn();
    }

    public void loadSpawn() {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("SPAWN.ENABLED")) {
            spawnLocation = null;
            return;
        }

        String worldName = config.getString("SPAWN.LOCATION.WORLD", "world");
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            plugin.getLogger().warning("Spawn world '" + worldName + "' is not loaded!");
            return;
        }

        double x = config.getDouble("SPAWN.LOCATION.X");
        double y = config.getDouble("SPAWN.LOCATION.Y");
        double z = config.getDouble("SPAWN.LOCATION.Z");
        float yaw = (float) config.getDouble("SPAWN.LOCATION.YAW");
        float pitch = (float) config.getDouble("SPAWN.LOCATION.PITCH");

        spawnLocation = new Location(world, x, y, z, yaw, pitch);
    }

    public void setSpawn(Location location) {
        FileConfiguration config = plugin.getConfig();
        config.set("SPAWN.LOCATION.WORLD", location.getWorld().getName());
        config.set("SPAWN.LOCATION.X", location.getX());
        config.set("SPAWN.LOCATION.Y", location.getY());
        config.set("SPAWN.LOCATION.Z", location.getZ());
        config.set("SPAWN.LOCATION.YAW", location.getYaw());
        config.set("SPAWN.LOCATION.PITCH", location.getPitch());
        plugin.saveConfig();

        spawnLocation = location;
    }

    public void teleportToSpawn(Player player) {
        if (spawnLocation != null) {
            player.teleport(spawnLocation);
        }
    }

    public boolean isSpawnEnabled() {
        return plugin.getConfig().getBoolean("SPAWN.ENABLED");
    }

    public boolean isSpawnOnJoinEnabled() {
        return plugin.getConfig().getBoolean("SPAWN.JOIN");
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }
}
