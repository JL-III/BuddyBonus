package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.events.RewardEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Notify implements Listener {
    @EventHandler
    public void onRewardEvent(RewardEvent event) {
        Bukkit.getConsoleSender().sendMessage("received reward event!");
    }
}
