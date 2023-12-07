package com.playtheatria.buddybonus.commands;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyRemoveEvent;
import com.playtheatria.buddybonus.events.BuddyRequestEvent;
import com.playtheatria.buddybonus.events.RequestAcceptEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerCommands implements CommandExecutor {
    private final BuddyBonus plugin;
    public PlayerCommands(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            // handles /buddy remove, /buddy <name>, /buddy accept

            if (args.length == 0) {
                Optional<Buddy> buddyOptional = Utils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), player.getUniqueId());

                if (buddyOptional.isPresent()) {
                    OfflinePlayer buddy_one = Bukkit.getOfflinePlayer(buddyOptional.get().player_one_UUID());
                    OfflinePlayer buddy_two = Bukkit.getOfflinePlayer(buddyOptional.get().player_two_UUID());

                    player.sendMessage(ChatColor.GOLD + "Buddy: "
                            + ChatColor.GREEN + buddy_one.getName()
                            + ChatColor.GOLD + " - "
                            + ChatColor.GREEN + buddy_two.getName());
                } else {
                    player.sendMessage(ChatColor.GOLD + "You don't have a buddy! Go to discord and find someone to hop online!");
                }
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("remove")) {
                    Optional<Buddy> buddyOptional = Utils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), player.getUniqueId());
                    if (buddyOptional.isEmpty()) {
                        plugin.debug("no buddy found for player: " + player.getName());
                        player.sendMessage(ChatColor.GOLD + "You don't have a buddy!");
                    } else {
                        player.sendMessage(ChatColor.GOLD + "Removing buddy!");
                        Bukkit.getPluginManager().callEvent(new BuddyRemoveEvent(buddyOptional.get()));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    Bukkit.getPluginManager().callEvent(new RequestAcceptEvent(player.getUniqueId()));
                    return true;
                } else {
                    // check to see if target exists and is not the player themselves
                    if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]) != player) {
                        Player target = Bukkit.getPlayer(args[0]);

                        assert target != null;
                        Bukkit.getPluginManager().callEvent(new BuddyRequestEvent(player.getUniqueId(), target.getUniqueId()));
                        return true;
                    } else {
                        player.sendMessage(ChatColor.GOLD + "That is not a valid player, please check the spelling of the name.");
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
