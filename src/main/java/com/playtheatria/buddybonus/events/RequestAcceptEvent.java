package com.playtheatria.buddybonus.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class RequestAcceptEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final UUID player_uuid;

    public RequestAcceptEvent(UUID player_uuid) {
        this.player_uuid = player_uuid;
    }

    public UUID getPlayer_uuid() { return player_uuid; }
}
