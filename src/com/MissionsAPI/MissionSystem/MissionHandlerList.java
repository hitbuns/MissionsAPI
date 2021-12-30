package com.MissionsAPI.MissionSystem;

import com.MissionsAPI.JSONSerializer;
import com.MissionsAPI.iJsonable;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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


    public static abstract class Mission {

        private final UUID uuid;
        private final int id = MissionHandlerList.getInstance().generateNextID();
        private OfflinePlayer cachedPlayer;

        public OfflinePlayer getCachedPlayer() {
            return cachedPlayer != null ? cachedPlayer :
                    uuid != null ? (this.cachedPlayer = Bukkit.getOfflinePlayer(
                            uuid
                    )) : null;
        }

        public Mission(UUID uuid) {
            this.uuid = uuid;
        }

        public UUID getUuid() {
            return uuid;
        }

        public int getId() {
            return id;
        }
    }
}
