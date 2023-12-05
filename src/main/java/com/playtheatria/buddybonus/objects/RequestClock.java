package com.playtheatria.buddybonus.objects;

import com.playtheatria.buddybonus.events.RequestRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RequestClock {
    private final Plugin plugin;
    private final List<Request> requestList;
    public RequestClock(Plugin plugin, List<Request> requestList) {
        this.plugin = plugin;
        this.requestList = requestList;
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Request request: requestList) {
                    if (System.currentTimeMillis() - request.time_stamp() > 20 * 300) {
                        RequestRemoveEvent event = new RequestRemoveEvent(request);
                        Bukkit.getPluginManager().callEvent(event);
                        Bukkit.getConsoleSender().sendMessage("remove event fired due to expired request");
                    }
                }
                // run every second to deal with the expiry
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
