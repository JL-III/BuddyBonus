package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.ChangeNotifyRewardEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ChangeNotifyReward implements Listener {

    private BuddyBonus plugin;

    private static final String toggle_on = ChatColor.GOLD + "Toggled buddy reward notifications" + ChatColor.GREEN + " on";
    private static final String toggle_off = ChatColor.GOLD + "Toggled buddy reward notifications" + ChatColor.RED + " off";

    public ChangeNotifyReward(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChangeNotifyRewardEvent(ChangeNotifyRewardEvent event) {
        plugin.debug("received change notify event!");
        UUID player_uuid = event.getPlayer_uuid();
        Player player = Bukkit.getPlayer(player_uuid);
        if (player == null) {
            plugin.debug("player in change notify event listener is null!");
        }
        if (plugin.getPlayerNotification().containsKey(player_uuid)) {
            boolean player_setting_inverted = !plugin.getPlayerNotification().get(player_uuid);
            plugin.getPlayerNotification().put(player_uuid, player_setting_inverted);
            player.sendMessage(player_setting_inverted ? toggle_on : toggle_off);
        } else {
            plugin.getPlayerNotification().put(player_uuid, false);
            player.sendMessage(toggle_off);
        }
    }
}
