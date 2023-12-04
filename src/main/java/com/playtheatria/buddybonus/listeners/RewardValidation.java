package com.playtheatria.buddybonus.listeners;

import com.earth2me.essentials.Essentials;
import com.playtheatria.buddybonus.events.RewardEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.events.CheckEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

// this seems like a weird name for the class at this point
public class RewardValidation implements Listener {
    private final Essentials essentials;
    public RewardValidation(Essentials essentials) {
        this.essentials = essentials;
    }

    @EventHandler
    public void onCheckEvent(CheckEvent event) {
        if (isQualified(event.getBuddy())) {
            RewardEvent rewardEvent = new RewardEvent(event.getBuddy(), 500);
            Bukkit.getPluginManager().callEvent(rewardEvent);
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
        return false;
    }

    public boolean playersAreWithinDistance(Player player_one, Player player_two) {
        if (player_one.getWorld() != player_two.getWorld()) {
            Bukkit.getConsoleSender().sendMessage("players are not in the same world");
            return false;
        }
        if (player_one.getLocation().getBlockX() - player_two.getLocation().getBlockX() > 100
        || player_one.getLocation().getBlockZ() - player_two.getLocation().getBlockZ() > 100) {
            Bukkit.getConsoleSender().sendMessage("players are not close enough");
            return false;
        }
        return true;
    }

    public boolean playersAreBothActive(UUID player_one, UUID player_two) {
        if (essentials.getUser(player_one) != null && essentials.getUser(player_one).isAfk()) {
            Bukkit.getConsoleSender().sendMessage("player one is either null or afk");
            return false;
        }
        if (essentials.getUser(player_two) != null && essentials.getUser(player_two).isAfk()) {
            Bukkit.getConsoleSender().sendMessage("player two is either null or afk");
            return false;
        }
        return true;
    }

}
