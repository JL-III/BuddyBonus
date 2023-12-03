package com.playtheatria.buddybonus.events;

import com.playtheatria.buddybonus.objects.Buddy;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class NotifyEvent extends Event {
    private final HandlerList HANDLERS = new HandlerList();
    private final Buddy buddy;
    @Override
    public @NotNull HandlerList getHandlers() { return HANDLERS; }
    public NotifyEvent(Buddy buddy) { this.buddy = buddy; }
    public Buddy getBuddy() { return buddy; }
}
