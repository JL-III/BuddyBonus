package com.playtheatria.buddybonus.events;

import com.playtheatria.buddybonus.objects.Buddy;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RewardEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Buddy buddy;
    private final int reward;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    @Override
    public @NotNull HandlerList getHandlers() { return HANDLERS; }
    public RewardEvent(Buddy buddy, int reward) {
        this.buddy = buddy;
        this.reward = reward;
    }
    public Buddy getBuddy() { return buddy; }
    public int getReward() { return reward; }

}
