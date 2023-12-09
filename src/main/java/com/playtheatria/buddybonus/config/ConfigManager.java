package com.playtheatria.buddybonus.config;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.objects.ConfigValidationResult;

public class ConfigManager {

    private final String reward_timer_duration = "reward-timer.duration";
    private final String reward_amount = "reward.amount";
    private final String request_lifespan = "request.lifespan";
    private final String debug = "debug";

    private BuddyBonus plugin;

    public ConfigManager(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.reloadConfig();
    }

    public int getRewardBaseAmount() {
        return plugin.getConfig().getInt(reward_amount);
    }

    public int getRewardTimerDuration() { return plugin.getConfig().getInt(reward_timer_duration); }

    public int getRequestLifespan() { return plugin.getConfig().getInt(request_lifespan); }

    public boolean getDebug() { return plugin.getConfig().getBoolean(debug); }

    public ConfigValidationResult isConfigValid() {
        ConfigValidationResult result = new ConfigValidationResult(true, "BuddyBonus: configuration values look valid!");

        if (plugin.getConfig().getInt(reward_amount) == 0) {
            result.setValid(false);
            result.setMessage("BuddyBonus: reward amount must be greater than 0, make sure you have set a value for " + reward_amount);
            return result;
        }

        if (plugin.getConfig().getInt(reward_timer_duration) == 0) {
            result.setValid(false);
            result.setMessage("BuddyBonus: reward amount must be greater than 0, make sure you have set a value for " + reward_timer_duration);
            return result;
        }

        if (plugin.getConfig().getInt(request_lifespan) == 0) {
            result.setValid(false);
            result.setMessage("BuddyBonus: request lifespan must be greater than 0, make sure you have set a value for " + request_lifespan);
            return result;
        }

        return result;
    }

}
