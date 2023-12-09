package com.playtheatria.buddybonus.objects;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.RequestRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RequestLifeCycleClock {
    private final BuddyBonus plugin;
    private final List<Request> requestList;
    public RequestLifeCycleClock(BuddyBonus plugin, List<Request> requestList) {
        this.plugin = plugin;
        this.requestList = requestList;
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Request request: requestList) {
                    // what is this math here?
                    if ((System.currentTimeMillis() - request.time_stamp()) / 1000 > plugin.getConfigManager().getRequestLifespan()) {
                        RequestRemoveEvent event = new RequestRemoveEvent(request);
                        Bukkit.getPluginManager().callEvent(event);
                        plugin.debug("remove event fired due to request expiry.");
                    }
                }
                // run every second to deal with the expiry
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
