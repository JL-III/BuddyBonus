package com.playtheatria.buddybonus.objects;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.enums.DebugType;
import com.playtheatria.buddybonus.events.BuddyRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ConcurrentHashMap;

public class BuddyLifecycleClock {

    private BuddyBonus plugin;

    private ConcurrentHashMap<Buddy, Long> buddyAuditList;

    public BuddyLifecycleClock(BuddyBonus plugin, ConcurrentHashMap<Buddy, Long> buddyAuditList) {
        this.plugin = plugin;
        this.buddyAuditList = buddyAuditList;
    }

    public void run() {
        new BukkitRunnable() {

            @Override
            public void run() {
                plugin.debug("checking buddies in audit list for log out length", DebugType.INFO);
                plugin.debug("buddies in audit list: " + buddyAuditList.size(), DebugType.INFO);
                buddyAuditList.forEach((buddy, timestamp) -> {
                    if (isOverLimit(timestamp)) {
                        plugin.debug("sending buddy remove event due to logout timestamp exceeding limit.", DebugType.ACTION);
                        Bukkit.getPluginManager().callEvent(new BuddyRemoveEvent(buddy));
                    } else {
                        plugin.debug("no buddies overlimit inside of audit list", DebugType.INFO);
                    }
                });
                if (buddyAuditList.values().removeIf(timestamp -> isOverLimit(timestamp))) {
                    plugin.debug("cleaning up buddy from audit list", DebugType.ACTION);
                };
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private boolean isOverLimit(long timestamp) {
        // hardcoded for 3 minutes
        if ((System.currentTimeMillis() - timestamp) / 1000 > 180) {
            plugin.debug("found buddy to be over limit - calculation result: " + ((System.currentTimeMillis() - timestamp) / 1000), DebugType.INFO);
            return true;
        } else {
            return false;
        }
    }
}
