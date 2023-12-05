package com.playtheatria.buddybonus.events;

import com.playtheatria.buddybonus.objects.Request;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BuddyCreateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Request request;

    public BuddyCreateEvent(Request request) {
        this.request = request;
    }

    public Request getRequest() { return request; }

}
