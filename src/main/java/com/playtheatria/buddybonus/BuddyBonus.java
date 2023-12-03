package com.playtheatria.buddybonus;

import com.earth2me.essentials.Essentials;
import com.playtheatria.buddybonus.listeners.BuddyRemove;
import com.playtheatria.buddybonus.listeners.BuddyRequest;
import com.playtheatria.buddybonus.listeners.RewardValidation;
import com.playtheatria.buddybonus.listeners.Notify;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.objects.BuddyClock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class BuddyBonus extends JavaPlugin {

    private final List<Buddy> buddyList = new ArrayList<>();
    private final Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    private final RewardValidation rewardValidation = new RewardValidation(essentials);

    @Override
    public void onEnable() {
        BuddyClock buddyClock = new BuddyClock(this, buddyList);
        buddyClock.run();
        Bukkit.getPluginManager().registerEvents(rewardValidation, this);
        Bukkit.getPluginManager().registerEvents(new Notify(), this);
        Bukkit.getPluginManager().registerEvents(new BuddyRequest(), this);
        Bukkit.getPluginManager().registerEvents(new BuddyRemove(), this);
    }

    public List<Buddy> getBuddyList() {
        return buddyList;
    }

}
