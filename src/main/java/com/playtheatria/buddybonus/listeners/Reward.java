package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.enums.DebugType;
import com.playtheatria.buddybonus.events.RewardEvent;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        plugin.debug("reward: received reward event!", DebugType.INFO);
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
        int reward_amount = getRewardAmount(event);
        Double beginning_balance = plugin.getEconomy().getBalance(player);
        EconomyResponse eco_response = plugin.getEconomy().depositPlayer(player, reward_amount);
        Double ending_balance = plugin.getEconomy().getBalance(player);

        if (eco_response.transactionSuccess()) {

            boolean notify = true;

            if (plugin.getPlayerNotification().containsKey(player.getUniqueId())
                    && !plugin.getPlayerNotification().get(player.getUniqueId())) {
                plugin.debug("reward notifications for " + player.getName() + " are turned off.", DebugType.INFO);
                notify = false;
            }

            if (notify) {
                player.sendMessage(ChatColor.GOLD + "You received a buddy reward of " + ChatColor.GREEN + reward_amount + ChatColor.GOLD + "!");
            }
            plugin.debug(player.getName() + " received a buddy bonus reward of " + reward_amount, DebugType.ACTION);
            plugin.debug("beginning balance: " + beginning_balance, DebugType.INFO);
            plugin.debug("reward amount: " + reward_amount, DebugType.INFO);
            plugin.debug("final balance: " + ending_balance, DebugType.INFO);
        } else {
            player.sendMessage(ChatColor.DARK_RED + "An error occurred! Please let an admin know!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "BuddyBonus Error!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was an error trying to reward " + player.getName() + " for the amount of " + reward_amount);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Beginning balance: " + beginning_balance);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Ending balance: " + ending_balance);
        }
    }

    private int getRewardAmount(RewardEvent event) {
        long time_comparison = ((System.currentTimeMillis() - event.getBuddy().creation_time_stamp()) / 1000) / 60;
        plugin.debug("time comparison result: " + time_comparison, DebugType.INFO);
        // every ten minutes players are in a buddy they will get an increase in earnings via multiplier
        // buddies are disbanded when a player logs out. they will have to rebuild their multiplier if they log out
        // however if buddies are afk or if they are not close enough, they will retain their multiplier

        if (time_comparison >= 40) {
            plugin.debug("rewarding a buddy with multiplier of 5!", DebugType.INFO);
            return event.getBaseReward() * 5;
        }
        if (time_comparison >= 30) {
            plugin.debug("rewarding a buddy with multiplier of 4!", DebugType.INFO);
            return event.getBaseReward() * 4;
        }
        if (time_comparison >= 20) {
            plugin.debug("rewarding a buddy with multiplier of 3!", DebugType.INFO);
            return event.getBaseReward() * 3;
        }
        if (time_comparison >= 10) {
            plugin.debug("rewarding a buddy with multiplier of 2!", DebugType.INFO);
            return event.getBaseReward() * 2;
        }
        return event.getBaseReward();
    }

}
