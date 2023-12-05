package com.playtheatria.buddybonus.objects;

import com.playtheatria.buddybonus.events.RewardCheckEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BuddyClock {
    private final Plugin plugin;
    private final List<Buddy> buddyList;
    public BuddyClock(Plugin plugin, List<Buddy> buddyList) {
        this.plugin = plugin;
        this.buddyList = buddyList;
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Buddy buddy: buddyList) {
                    Bukkit.getConsoleSender().sendMessage("sending check event!");
                    RewardCheckEvent event = new RewardCheckEvent(buddy);
                    Bukkit.getPluginManager().callEvent(event);
                }
            }
        }.runTaskTimer(plugin, 0, 20 * 15);
    }
}
