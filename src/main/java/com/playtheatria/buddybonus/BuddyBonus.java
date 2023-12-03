package com.playtheatria.buddybonus;

import com.earth2me.essentials.Essentials;
import com.playtheatria.buddybonus.events.NotifyEvent;
import com.playtheatria.buddybonus.listeners.Checker;
import com.playtheatria.buddybonus.listeners.Notify;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.objects.Clock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class BuddyBonus extends JavaPlugin {

    private final List<Buddy> buddyList = new ArrayList<>();
    private final Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    private final Checker checker = new Checker(essentials);

    @Override
    public void onEnable() {
        Clock clock = new Clock(this, buddyList);
        clock.run();
        Bukkit.getPluginManager().registerEvents(checker, this);
        Bukkit.getPluginManager().registerEvents(new Notify(), this);
    }

}
