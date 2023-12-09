package com.playtheatria.buddybonus.events;

import com.playtheatria.buddybonus.objects.Buddy;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BuddyRemoveEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Buddy buddy;

    public BuddyRemoveEvent(Buddy buddy) {
        this.buddy = buddy;
    }

    public Buddy getBuddy() { return buddy; }
}
