package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.events.NotifyEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Notify implements Listener {
    @EventHandler
    public void onNotifyEvent(NotifyEvent event) {
        Bukkit.getConsoleSender().sendMessage("received notify event!");
    }
}
