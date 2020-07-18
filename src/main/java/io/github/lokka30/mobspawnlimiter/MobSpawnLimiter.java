package io.github.lokka30.mobspawnlimiter;

import io.github.lokka30.mobspawnlimiter.commands.MSLCommand;
import io.github.lokka30.mobspawnlimiter.listeners.MSLListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class MobSpawnLimiter extends JavaPlugin {

    private boolean debug;
    private HashMap<EntityType, Boolean> managedMobsMap;

    public void onEnable() {
        getLogger().info("--- Started Loading ---");

        getLogger().info("Saving config (if not available)");
        saveDefaultConfig();

        getLogger().info("Registering events...");
        getServer().getPluginManager().registerEvents(new MSLListener(this), this);

        getLogger().info("Registering commands...");
        Objects.requireNonNull(getCommand("mobspawnlimiter")).setExecutor(new MSLCommand(this));

        getLogger().info("Setting values...");
        debug = getConfig().getBoolean("debug");
        managedMobsMap = new HashMap<>();

        getLogger().info("--- Loading Complete ---");
    }

    public HashMap<EntityType, Boolean> getManagedMobsMap() {
        return managedMobsMap;
    }

    public boolean isDebugEnabled() {
        return debug;
    }

    public boolean chanceOf(int outOf10) {
        int random = new Random().nextInt(10);
        return random > outOf10;
    }

    public String colorize(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
