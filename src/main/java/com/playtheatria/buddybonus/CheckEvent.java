package com.playtheatria.buddybonus;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CheckEvent extends Event {
    private final HandlerList HANDLERS = new HandlerList();
    private final Buddy buddy;
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public CheckEvent(Buddy buddy) {
        this.buddy = buddy;
    }

    public Buddy getBuddy() {
        return buddy;
    }
}
