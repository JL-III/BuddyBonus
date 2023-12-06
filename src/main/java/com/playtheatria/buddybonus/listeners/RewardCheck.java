package com.playtheatria.buddybonus.listeners;

import com.earth2me.essentials.Essentials;
import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.RewardEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.events.RewardCheckEvent;
import org.bukkit.Bukkit;
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
        if (isQualified(event.getBuddy())) {
            RewardEvent rewardEvent = new RewardEvent(event.getBuddy(), plugin.getConfigManager().getReward_amount());
            Bukkit.getPluginManager().callEvent(rewardEvent);
        } else {
            plugin.debug("buddy doesn't qualify");
        }
    }
    public boolean isQualified(Buddy buddy) {

        if (Bukkit.getPlayer(buddy.player_one_UUID()) == null) return false;
        if (Bukkit.getPlayer(buddy.player_two_UUID()) == null) return false;
        Player player_one = Bukkit.getPlayer(buddy.player_one_UUID());
        Player player_two = Bukkit.getPlayer(buddy.player_two_UUID());

        assert player_one != null;
        assert player_two != null;
        if (!playersAreWithinDistance(player_one, player_two)) return  false;
        if (!playersAreBothActive(buddy.player_one_UUID(), buddy.player_two_UUID())) return false;
        return true;
    }

    public boolean playersAreWithinDistance(Player player_one, Player player_two) {
        if (player_one.getWorld() != player_two.getWorld()) {
            plugin.debug("players are not in the same world");
            return false;
        }
        if (player_one.getLocation().getBlockX() - player_two.getLocation().getBlockX() > 100
        || player_one.getLocation().getBlockZ() - player_two.getLocation().getBlockZ() > 100) {
            plugin.debug("players are not close enough");
            return false;
        }
        return true;
    }

    public boolean playersAreBothActive(UUID player_one, UUID player_two) {
        if (essentials.getUser(player_one) != null && essentials.getUser(player_one).isAfk()) {
            plugin.debug("player one is either null or afk");
            return false;
        }
        if (essentials.getUser(player_two) != null && essentials.getUser(player_two).isAfk()) {
            plugin.debug("player two is either null or afk");
            return false;
        }
        return true;
    }

}
