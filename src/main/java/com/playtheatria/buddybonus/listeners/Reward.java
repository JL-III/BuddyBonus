package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.RewardEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Reward implements Listener {
    private BuddyBonus plugin;

    public Reward(BuddyBonus plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onRewardEvent(RewardEvent event) {
        plugin.debug("reward: received reward event!");
    }
}
