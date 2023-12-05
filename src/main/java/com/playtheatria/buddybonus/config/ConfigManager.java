package com.playtheatria.buddybonus.config;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.objects.ConfigValidationResult;

public class ConfigManager {

    private final String REWARD_TIMER_DURATION = "reward-timer.duration";
    private final String REWARD_AMOUNT = "reward.amount";
    private final String REQUEST_LIFESPAN = "request.lifespan";

    private BuddyBonus plugin;

    public ConfigManager(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.reloadConfig();
    }

    public int getReward_amount() {
        return plugin.getConfig().getInt(REWARD_AMOUNT);
    }

    public int getRewardTimerDuration() { return plugin.getConfig().getInt(REWARD_TIMER_DURATION); }

    public int getRequestLifespan() { return plugin.getConfig().getInt(REQUEST_LIFESPAN); }


    public ConfigValidationResult isConfigValid() {
        ConfigValidationResult result = new ConfigValidationResult(true, "BuddyBonus: configuration values look valid!");

        if (plugin.getConfig().getInt(REWARD_AMOUNT) == 0) {
            result.setValid(false);
            result.setMessage("BuddyBonus: reward amount must be greater than 0, make sure you have set a value for " + REWARD_AMOUNT);
            return result;
        }

        if (plugin.getConfig().getInt(REWARD_TIMER_DURATION) == 0) {
            result.setValid(false);
            result.setMessage("BuddyBonus: reward amount must be greater than 0, make sure you have set a value for " + REWARD_TIMER_DURATION);
            return result;
        }

        if (plugin.getConfig().getInt(REQUEST_LIFESPAN) == 0) {
            result.setValid(false);
            result.setMessage("BuddyBonus: request lifespan must be greater than 0, make sure you have set a value for " + REQUEST_LIFESPAN);
            return result;
        }

        return result;
    }

}
