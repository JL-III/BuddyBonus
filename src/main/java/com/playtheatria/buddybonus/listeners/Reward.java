package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.RewardEvent;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Reward implements Listener {
    private BuddyBonus plugin;

    public Reward(BuddyBonus plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onRewardEvent(RewardEvent event) {
        plugin.debug("reward: received reward event!");
        Player player_one = Bukkit.getPlayer(event.getBuddy().player_one_UUID());
        Player player_two = Bukkit.getPlayer(event.getBuddy().player_two_UUID());

        if (player_one != null) {
            sendEcoReward(player_one, event);
        }
        if (player_two != null) {
            sendEcoReward(player_two, event);
        }
    }

    private void sendEcoReward(Player player, RewardEvent event) {
        Double beginning_balance = plugin.getEconomy().getBalance(player);
        EconomyResponse player_one_eco_response = plugin.getEconomy().depositPlayer(player, event.getReward());
        Double ending_balance = plugin.getEconomy().getBalance(player);

        if (player_one_eco_response.transactionSuccess()) {
            player.sendMessage(ChatColor.YELLOW + "You received a buddy reward of " + ChatColor.GREEN + event.getReward() + ChatColor.YELLOW + "!");
            plugin.debug(player.getName() + " received a buddy bonus reward of " + event.getReward());
            plugin.debug("beginning balance: " + beginning_balance);
            plugin.debug("reward amount: " + event.getReward());
            plugin.debug("final balance: " + ending_balance);
        } else {
            player.sendMessage(ChatColor.DARK_RED + "An error occurred! Please let an admin know!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "BuddyBonus Error!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was an error trying to reward " + player.getName() + " for the amount of " + event.getReward());
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Beginning balance: " + beginning_balance);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Ending balance: " + ending_balance);
        }
    }
}
