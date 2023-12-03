package com.playtheatria.buddybonus;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class Checker implements Listener {
    private final Essentials essentials;
    public Checker(Essentials essentials) {
        this.essentials = essentials;
    }

    @EventHandler
    public void onCheckEvent(CheckEvent event) {
        if (isQualified(event.getBuddy())) {
            Bukkit.getConsoleSender().sendMessage("Buddy is qualified!");
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
        if (player_one.getWorld() != player_two.getWorld()) return false;
        if (player_one.getLocation().getBlockX() - player_two.getLocation().getBlockX() > 100
        || player_one.getLocation().getBlockZ() - player_two.getLocation().getBlockZ() > 100) {
            return false;
        }
        return true;
    }

    public boolean playersAreBothActive(UUID player_one, UUID player_two) {
        if (essentials.getUser(player_one) != null && essentials.getUser(player_one).isAfk()) return false;
        if (essentials.getUser(player_two) != null && essentials.getUser(player_two).isAfk()) return false;
        return true;
    }

}
