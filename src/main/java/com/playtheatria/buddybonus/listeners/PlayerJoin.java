package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.enums.DebugType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private BuddyBonus plugin;

    public PlayerJoin(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // if a buddy is inside the audit list when a player rejoins, we will remove them from the audit
        plugin.debug(event.getPlayer().getName() + " logged in, checking if buddy exists and is in audit list.", DebugType.ACTION);
        plugin.getBuddyAuditList().keySet().removeIf(buddy ->
            buddy.player_one_UUID().equals(event.getPlayer().getUniqueId()) ||
            buddy.player_two_UUID().equals(event.getPlayer().getUniqueId())
        );
    }
}
