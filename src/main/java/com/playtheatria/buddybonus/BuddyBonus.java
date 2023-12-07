package com.playtheatria.buddybonus;

import com.earth2me.essentials.Essentials;
import com.playtheatria.buddybonus.commands.AdminCommands;
import com.playtheatria.buddybonus.commands.PlayerCommands;
import com.playtheatria.buddybonus.config.ConfigManager;
import com.playtheatria.buddybonus.listeners.*;
import com.playtheatria.buddybonus.objects.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public final class BuddyBonus extends JavaPlugin {
    private static Economy econ = null;
    private List<Buddy> buddyList = new CopyOnWriteArrayList<>();
    private List<Request> requestList = new CopyOnWriteArrayList<>();
    private final Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    private final ConfigManager configManager = new ConfigManager(this);

    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (essentials == null) {
            Bukkit.getConsoleSender().sendMessage("BuddyBonus: essentials was not found, shutting down.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        saveDefaultConfig();
        validateConfig();
        Bukkit.getPluginManager().registerEvents(new BuddyCreate(this), this);
        Bukkit.getPluginManager().registerEvents(new BuddyRemove(this), this);
        Bukkit.getPluginManager().registerEvents(new BuddyRequest(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
        Bukkit.getPluginManager().registerEvents(new RequestAccept(this), this);
        Bukkit.getPluginManager().registerEvents(new RequestRemove(this), this);
        Bukkit.getPluginManager().registerEvents(new Reward(this), this);
        Bukkit.getPluginManager().registerEvents(new RewardCheck(this, essentials), this);

        Objects.requireNonNull(getCommand("buddy")).setExecutor(new PlayerCommands(this));
        Objects.requireNonNull(getCommand("abuddy")).setExecutor(new AdminCommands(this));

        new BuddyClock(this, buddyList).run();
        new RequestClock(this, requestList).run();
    }

    public List<Buddy> getBuddyList() {
        return buddyList;
    }

    public List<Request> getRequestList() { return requestList; }

    public void validateConfig() {
        ConfigValidationResult configValidationResult = configManager.isConfigValid();
        if (!configValidationResult.getIsValid()) {
            Bukkit.getConsoleSender().sendMessage(configValidationResult.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            Bukkit.getConsoleSender().sendMessage(configValidationResult.getMessage());
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void debug(String message) {
        if (configManager.getDebug()) {
            Bukkit.getConsoleSender().sendMessage("BuddyBonus [DEBUG]: " + message);
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEconomy() {
        return econ;
    }

}
