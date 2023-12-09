package com.playtheatria.buddybonus.utils;

import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.objects.Request;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Utils {
    public static Optional<Buddy> getOptionalBuddyFromBuddyList(List<Buddy> buddyList, UUID player_uuid) {
        Optional<Buddy> buddy = Optional.empty();
        for (Buddy buddy_in_list: buddyList) {
            if (buddy_in_list.player_one_UUID() == player_uuid) { buddy = Optional.of(buddy_in_list); }
            if (buddy_in_list.player_two_UUID() == player_uuid) { buddy = Optional.of(buddy_in_list); }
        }
        return buddy;
    }

    public static Optional<Request> getOptionalRequestFromRequestList(List<Request> requestList, UUID player_uuid) {
        Optional<Request> request = Optional.empty();
        for (Request request_in_list: requestList) {
            if (request_in_list.player_one_UUID() == player_uuid) { request = Optional.of(request_in_list); }
            if (request_in_list.player_two_UUID() == player_uuid) { request = Optional.of(request_in_list); }
        }
        return request;
    }

}
