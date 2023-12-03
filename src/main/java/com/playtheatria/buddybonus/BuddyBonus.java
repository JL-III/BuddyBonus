package com.playtheatria.buddybonus;

import com.earth2me.essentials.Essentials;
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
    }

}
