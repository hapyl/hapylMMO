package me.hapyl.mmo.database;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import me.hapyl.mmo.Dependency;
import me.hapyl.mmo.Main;

import java.util.Map;
import java.util.UUID;

public class Database extends Dependency {

    private final Map<UUID, PlayerDatabase> playerDatabase;

    private MongoClient client;
    private MongoDatabase database;

    public Database(Main plugin) {
        super(plugin);

        playerDatabase = Maps.newConcurrentMap();

        createConnection();
    }

    public void stopConnection() {
        try {
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createConnection() {
        if (client != null) {
            throw new IllegalStateException("Connection already created.");
        }

        final String connectionLink = getPlugin().getConfig().getString("database.connection_link", null);

        if (connectionLink == null) {
            disablePlugin("No connection link provided.");
            return;
        }

        client = MongoClients.create(connectionLink);
        database = client.getDatabase("mmo");

        // Collections

    }

    public void save(UUID uuid) {

    }

    public void saveAll() {
        // TODO (hapyl): 005, May 5, 2023:
        for (PlayerDatabase value : playerDatabase.values()) {
        }
    }
}
