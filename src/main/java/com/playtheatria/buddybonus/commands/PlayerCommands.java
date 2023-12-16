package com.playtheatria.buddybonus.commands;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.enums.DebugType;
import com.playtheatria.buddybonus.events.BuddyRemoveEvent;
import com.playtheatria.buddybonus.events.BuddyRequestEvent;
import com.playtheatria.buddybonus.events.ChangeNotifyRewardEvent;
import com.playtheatria.buddybonus.events.RequestAcceptEvent;
import com.playtheatria.buddybonus.listeners.RewardCheck;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerCommands implements CommandExecutor, TabCompleter {
    private final BuddyBonus plugin;
    public PlayerCommands(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length <= 1) {
            commands.add("accept");
            commands.add("add");
            commands.add("notify");
            commands.add("remove");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return commands;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {

            if (args.length == 0) {
                Optional<Buddy> buddyOptional = Utils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), player.getUniqueId());

                if (buddyOptional.isPresent()) {
                    OfflinePlayer buddy_one = Bukkit.getOfflinePlayer(buddyOptional.get().player_one_UUID());
                    OfflinePlayer buddy_two = Bukkit.getOfflinePlayer(buddyOptional.get().player_two_UUID());

                    boolean active = RewardCheck.playersAreBothActive(plugin, buddy_one.getUniqueId(), buddy_two.getUniqueId());
                    boolean within_range = RewardCheck.playersAreWithinDistance(plugin, buddy_one.getUniqueId(), buddy_two.getUniqueId());

                    player.sendMessage(ChatColor.GOLD + "Buddy: "
                            + ChatColor.GREEN + buddy_one.getName()
                            + ChatColor.GOLD + " <-> "
                            + ChatColor.GREEN + buddy_two.getName());
                    player.sendMessage(ChatColor.GOLD + " - both active: " + (active ? ChatColor.GREEN : ChatColor.RED) + active);
                    player.sendMessage(ChatColor.GOLD + " - within 250 blocks: " + (within_range ? ChatColor.GREEN : ChatColor.RED) + within_range);
                } else {
                    player.sendMessage(ChatColor.GOLD + "You don't have a buddy! Go to discord and find someone to hop online!");
                }
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("remove")) {
                    Optional<Buddy> buddyOptional = Utils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), player.getUniqueId());
                    if (buddyOptional.isEmpty()) {
                        plugin.debug("no buddy found for player: " + player.getName(), DebugType.INFO);
                        player.sendMessage(ChatColor.GOLD + "You don't have a buddy!");
                    } else {
                        player.sendMessage(ChatColor.GOLD + "Removing buddy!");
                        Bukkit.getPluginManager().callEvent(new BuddyRemoveEvent(buddyOptional.get()));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    Bukkit.getPluginManager().callEvent(new RequestAcceptEvent(player.getUniqueId()));
                    return true;
                } else if (args[0].equalsIgnoreCase("notify")) {
                    Bukkit.getPluginManager().callEvent(new ChangeNotifyRewardEvent(player.getUniqueId()));
                    return true;
                }
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add")) {
                    if (Bukkit.getPlayer(args[1]) == player) {
                        player.sendMessage(ChatColor.RED + "You cannot add yourself! Go find a buddy!");
                        return true;
                    }
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);

                        assert target != null;
                        Bukkit.getPluginManager().callEvent(new BuddyRequestEvent(player.getUniqueId(), target.getUniqueId()));
                    } else {
                        player.sendMessage(ChatColor.GOLD + "That is not a valid player, please check the spelling of the name.");
                    }
                    return true;
                }
            }
        } else {
            commandSender.sendMessage("BuddyBonus: You must be a player to execute this command!");
        }
        return false;
    }
}
