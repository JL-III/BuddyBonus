package com.playtheatria.buddybonus.commands;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.objects.Request;
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

public class AdminCommands implements CommandExecutor {

    private final BuddyBonus plugin;

    public AdminCommands(BuddyBonus plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!commandSender.hasPermission("buddy.admin")) {
            commandSender.sendMessage("You do not have permission to use admin commands.");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                commandSender.sendMessage(ChatColor.GREEN + "Current Buddies and Requests: ");
                commandSender.sendMessage( ChatColor.AQUA + "Requests: ");
                for (Request request : plugin.getRequestList()) {
                    OfflinePlayer player_one = Bukkit.getOfflinePlayer(request.player_one_UUID());
                    OfflinePlayer player_two = Bukkit.getOfflinePlayer(request.player_two_UUID());
                    commandSender.sendMessage("Request: " + player_one.getName() + " - " + player_two.getName());
                }
                commandSender.sendMessage( ChatColor.AQUA + "Buddies: ");
                for (Buddy buddy : plugin.getBuddyList()) {
                    OfflinePlayer player_one = Bukkit.getOfflinePlayer(buddy.player_one_UUID());
                    OfflinePlayer player_two = Bukkit.getOfflinePlayer(buddy.player_two_UUID());
                    commandSender.sendMessage("Buddy: " + player_one.getName() + " - " + player_two.getName());
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                commandSender.sendMessage("BuddyBonus: reloading config");
                plugin.getConfigManager().reload();
                return true;
            }
        }
        if (args.length == 2) {
            if (!args[0].equalsIgnoreCase("disband")) return false;
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                plugin.debug("player was null in disband command.");
                return true;
            }
            Optional<Buddy> optionalBuddy = Utils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), player.getUniqueId());
            if (optionalBuddy.isPresent()) {
                commandSender.sendMessage("removing buddy from list.");
                plugin.getBuddyList().remove(optionalBuddy.get());
            } else {
                commandSender.sendMessage("There is no buddy with this name!");
                return true;
            }
            return true;
        }
        return false;
    }
}
