package com.playtheatria.buddybonus.listeners;

import com.earth2me.essentials.Essentials;
import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.RewardEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.events.RewardCheckEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class RewardCheck implements Listener {

    private BuddyBonus plugin;

    private final Essentials essentials;

    public RewardCheck(BuddyBonus plugin, Essentials essentials) {
        this.plugin = plugin;
        this.essentials = essentials;
    }

    @EventHandler
    public void onRewardCheck(RewardCheckEvent event) {
        if (isQualified(plugin, event.getBuddy())) {
            RewardEvent rewardEvent = new RewardEvent(event.getBuddy(), plugin.getConfigManager().getRewardBaseAmount());
            Bukkit.getPluginManager().callEvent(rewardEvent);
        } else {
            plugin.debug("buddy doesn't qualify");
        }
    }

    public static boolean isQualified(BuddyBonus plugin, Buddy buddy) {

        if (!playersAreWithinDistance(plugin, buddy.player_one_UUID(), buddy.player_two_UUID())) return  false;
        if (!playersAreBothActive(plugin, buddy.player_one_UUID(), buddy.player_two_UUID())) return false;
        return true;
    }

    public static boolean playersAreWithinDistance(BuddyBonus plugin, UUID player_one_uuid, UUID player_two_uuid) {
        if (Bukkit.getPlayer(player_one_uuid) == null) {
            plugin.debug("bukkit returned player one as null");
            return false;
        }
        if (Bukkit.getPlayer(player_two_uuid) == null) {
            plugin.debug("bukkit returned player two as null");
            return false;
        }
        Player player_one = Bukkit.getPlayer(player_one_uuid);
        Player player_two = Bukkit.getPlayer(player_two_uuid);

        assert player_one != null;
        assert player_two != null;
        if (player_one.getWorld() != player_two.getWorld()) {
            plugin.debug("players are not in the same world");
            return false;
        }

        Location loc1 = player_one.getLocation();
        Location loc2 = player_two.getLocation();

        double distance = loc1.distance(loc2);
        plugin.debug("Players are " + distance + " apart.");

        if (distance > 100) {
            plugin.debug("Players are not close enough");
            return false;
        }
        return true;
    }

    public static boolean playersAreBothActive(BuddyBonus plugin, UUID player_one, UUID player_two) {
        Essentials essentials = plugin.getEssentials();
        if (essentials.getUser(player_one) == null) {
            plugin.debug("essentials returned player one as null");
            return false;
        }
        if (essentials.getUser(player_two) == null) {
            plugin.debug("essentials returned player two as null");
            return false;
        }
        if (essentials.getUser(player_one).isAfk()) {
            plugin.debug("essentials returned player one is afk");
            return false;
        }
        if (essentials.getUser(player_two).isAfk()) {
            plugin.debug("essentials returned player two is afk");
            return false;
        }
        return true;
    }
}
