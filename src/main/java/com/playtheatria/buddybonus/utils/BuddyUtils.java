package com.playtheatria.buddybonus.utils;

import com.playtheatria.buddybonus.objects.Buddy;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BuddyUtils {
    public static Optional<Buddy> getOptionalBuddyFromBuddyList(List<Buddy> buddyList, UUID player_uuid) {
        Optional<Buddy> buddy = Optional.empty();
        for (Buddy buddy_in_list: buddyList) {
            if (buddy_in_list.player_one_UUID() == player_uuid) { buddy = Optional.of(buddy_in_list); }
            if (buddy_in_list.player_two_UUID() == player_uuid) { buddy = Optional.of(buddy_in_list); }
        }
        return buddy;
    }
}
