package com.playtheatria.buddybonus.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BuddyRequestEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final UUID player_initiate;

    private final UUID player_target;

    public BuddyRequestEvent(@NotNull UUID player_initiate, @NotNull UUID player_target) {
        this.player_initiate = player_initiate;
        this.player_target = player_target;
    }

    public UUID getPlayer_initiate() {
        return player_initiate;
    }

    public UUID getPlayer_target() {
        return player_target;
    }
}
