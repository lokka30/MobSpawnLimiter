package io.github.lokka30.mobspawnlimiter.commands;

import io.github.lokka30.mobspawnlimiter.MobSpawnLimiter;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MSLCommand implements CommandExecutor {

    private MobSpawnLimiter instance;
    public MSLCommand(final MobSpawnLimiter instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if(args.length == 0) {
            sender.sendMessage(instance.colorize("&b&lMobSpawnLimiter: &7This server is running &bMSL v" + instance.getDescription().getVersion() + "&7, developed by &blokka30&7."));

            if(sender.hasPermission("mobspawnlimiter.reload")) {
                sender.sendMessage(instance.colorize("&b&lMobSpawnLimiter: &7Available commands:"));
                sender.sendMessage(instance.colorize("&8 &m->&b /" + label + " reload &8- &7Reload the plugin's configuration."));
            }

            sound(sender, true);
        } else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if(sender.hasPermission("mobspawnlimiter.reload")) {
                sender.sendMessage(instance.colorize("&b&lMobSpawnLimiter: &7Started reloading configuration ..."));
                instance.reloadConfig();
                sender.sendMessage(instance.colorize("&b&lMobSpawnLimiter: &7... Configuration reloaded successfuly."));
                sound(sender, true);
            } else {
                sender.sendMessage(instance.colorize("&b&lMobSpawnLimiter: &7You don't have access to that."));
                sound(sender, false);
            }
        } else {
            sender.sendMessage(instance.colorize("&b&lMobSpawnLimiter: &7Usage: &b/" + label + " [reload]"));
            sound(sender, false);
        }
        return true;
    }

    private void sound(final CommandSender sender, final boolean isPositive) {
        if(sender instanceof Player) {
            final Player player = (Player) sender;

            float pitch;
            if(isPositive) {
                pitch = 2.0F;
            } else {
                pitch = 0.0F;
            }

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.75F, pitch);
        }
    }
}
