package com.playtheatria.buddybonus.commands;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyRemoveEvent;
import com.playtheatria.buddybonus.events.BuddyRequestEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.utils.BuddyUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerCommands implements CommandExecutor {
    // player sends request to player for buddy system
    // check if player already has a buddy
    // player can remove buddy with /buddy remove
    // player can add buddy with /buddy <name>
    private final BuddyBonus plugin;
    public PlayerCommands(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // check if target player exists
        // check if they belong to a buddy - maybe this isn't needed?
        // its possible we just let them always request a new buddy and if
        // someone accepts it changes their buddy to the new person
        // not caring if they were already in a buddy?
        // maybe when a player sends a request they can be removed from any previous buddy they belonged to
        // check if they have a pending request?
        if (commandSender instanceof Player player) {
            // handles /buddy remove, /buddy <name>, /buddy accept
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("remove")) {
                    Optional<Buddy> buddyOptional = BuddyUtils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), player.getUniqueId());
                    if (buddyOptional.isEmpty()) {
                        Bukkit.getConsoleSender().sendMessage("No buddy found for player: " + player.getName());
                    } else {
                        Bukkit.getPluginManager().callEvent(new BuddyRemoveEvent(buddyOptional.get()));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    return true;
                } else {
                    // check to see if target exists and is not the player themselves
                    if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]) != player) {
                        Player target = Bukkit.getPlayer(args[0]);

                        assert target != null;
                        for (Buddy buddy: plugin.getBuddyList()) {
                            // here we check if the player is a buddy already, if they are then we remove the buddy from the list so that they can buddy up with someone else.
                            if (buddy.player_one_UUID() == player.getUniqueId()) { plugin.getBuddyList().remove(buddy); }
                            if (buddy.player_two_UUID() == player.getUniqueId()) { plugin.getBuddyList().remove(buddy); }
                        }
                        Bukkit.getPluginManager().callEvent(new BuddyRequestEvent(player.getUniqueId(), target.getUniqueId()));
                        return true;
                    }
                }
            }
        } else {
            commandSender.sendMessage("BuddyBonus: You must be a player to execute this command!");
        }
        return false;
    }
}
