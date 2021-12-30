package com.MissionsAPI.MissionSystem;

import com.MissionsAPI.JSONSerializer;
import com.MissionsAPI.iJsonable;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MissionHandlerList implements iJsonable {


    private int nextID = 0;
    private static final MissionHandlerList Instance;
    static JSONSerializer jsonSerializer;
    private JavaPlugin javaPlugin;

    static {
        JavaPlugin javaPlugin1 = JavaPlugin.getProvidingPlugin(MissionHandlerList.class);
        jsonSerializer = new JSONSerializer();
        File f = new File(JavaPlugin.getProvidingPlugin(MissionHandlerList.class).getDataFolder(),"missions.json");
        (Instance = jsonSerializer.load(f,MissionHandlerList.class))
                .javaPlugin = javaPlugin1;
    }

    public int generateNextID() {
        nextID++;
        return nextID;
    }

    public static MissionHandlerList getInstance() {
        return Instance;
    }

    @Override
    public File getParent() {
        return javaPlugin.getDataFolder();
    }

    @Override
    public String getChild() {
        return "missions.json";
    }

    public static class MissionEntry {

        private final Map<String,Mission> missions = new HashMap<>();
        private final UUID uuid;
        private transient OfflinePlayer cachedPlayer;

        public MissionEntry(UUID uuid) {
            this.uuid = uuid;
        }

        public MissionEntry(OfflinePlayer offlinePlayer) {
            this(offlinePlayer.getUniqueId());
        }

        public Collection<Mission> getMissions() {
            return missions.values();
        }

        public UUID getUuid() {
            return uuid;
        }

        public OfflinePlayer getCachedPlayer() {
            return cachedPlayer != null ? cachedPlayer :
                    uuid != null ? (this.cachedPlayer =
                            Bukkit.getOfflinePlayer(uuid)) : null;
        }
    }

}
