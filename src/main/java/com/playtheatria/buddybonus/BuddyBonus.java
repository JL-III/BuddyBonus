package com.playtheatria.buddybonus;

import com.earth2me.essentials.Essentials;
import com.playtheatria.buddybonus.commands.PlayerCommands;
import com.playtheatria.buddybonus.listeners.*;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.objects.BuddyClock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BuddyBonus extends JavaPlugin {

    private final List<Buddy> buddyList = new ArrayList<>();
    private final Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    private final Check check = new Check(essentials);

    @Override
    public void onEnable() {
        BuddyClock buddyClock = new BuddyClock(this, buddyList);
        buddyClock.run();
        Bukkit.getPluginManager().registerEvents(check, this);
        Bukkit.getPluginManager().registerEvents(new Notify(), this);
        Bukkit.getPluginManager().registerEvents(new BuddyRequest(this), this);
        Bukkit.getPluginManager().registerEvents(new Reward(), this);
        Bukkit.getPluginManager().registerEvents(new BuddyRemove(), this);
        Objects.requireNonNull(getCommand("buddy")).setExecutor(new PlayerCommands(this));
    }

    public List<Buddy> getBuddyList() {
        return buddyList;
    }

}
