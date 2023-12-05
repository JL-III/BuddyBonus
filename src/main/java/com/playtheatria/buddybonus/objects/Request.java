package com.playtheatria.buddybonus.objects;

import java.util.UUID;

public record Request(UUID player_one_UUID, UUID player_two_UUID, long time_stamp) { }
