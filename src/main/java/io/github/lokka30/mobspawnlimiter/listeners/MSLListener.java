package io.github.lokka30.mobspawnlimiter.listeners;

import io.github.lokka30.mobspawnlimiter.MobSpawnLimiter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.HashMap;

public class MSLListener implements Listener {

    private MobSpawnLimiter instance;
    public MSLListener(final MobSpawnLimiter instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCreatureSpawn(final CreatureSpawnEvent event) {
        final Entity entity = event.getEntity();
        final EntityType entityType = entity.getType();
        final String entityTypeStr = entityType.name();

        HashMap<EntityType, Boolean> managedMobsMap = instance.getManagedMobsMap();

        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            boolean isManaged;
            if(managedMobsMap.containsKey(entityType) ) {
                isManaged = managedMobsMap.get(entityType);
            } else {
                isManaged = instance.getConfig().contains("mobs." + entityTypeStr);
            }

            if(isManaged) {
                if(!instance.chanceOf(instance.getConfig().getInt("mobs." + entityTypeStr))) {
                    event.setCancelled(true);
                    if(instance.isDebugEnabled()) { Bukkit.getLogger().info("(DEBUG) Cancelled spawning of " + entityTypeStr + "."); }
                } else {
                    if(instance.isDebugEnabled()) { Bukkit.getLogger().info("(DEBUG) Allowed spawning of " + entityTypeStr + "."); }
                }
                managedMobsMap.put(entityType, true);
            } else {
                managedMobsMap.put(entityType, false);
            }
        }
    }
}
