package com.MissionsAPI.MissionSystem;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public abstract class Mission {

    private final UUID uuid;
    private final int id = MissionHandlerList.getInstance().generateNextID();
    private transient OfflinePlayer cachedPlayer;

    public OfflinePlayer getCachedPlayer() {
        return cachedPlayer != null ? cachedPlayer :
                uuid != null ? (this.cachedPlayer = Bukkit.getOfflinePlayer(
                        uuid
                )) : null;
    }

    public Mission(UUID uuid) {
        this.uuid = uuid;
    }

    public Mission(OfflinePlayer offlinePlayer) {
        this(offlinePlayer.getUniqueId());
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getId() {
        return id;
    }

    public abstract boolean hasGoalBeenMet();
    public abstract void invokeGoal();
    public abstract void onMissionEnd();

    public void endMission() {
        onMissionEnd();

    }

}
