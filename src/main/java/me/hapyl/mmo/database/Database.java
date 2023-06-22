package me.hapyl.mmo.database;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.hapyl.mmo.Dependency;
import me.hapyl.mmo.Main;
import me.hapyl.spigotutils.module.util.Enums;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public class Database extends Dependency {

    private final Map<UUID, PlayerDatabase> playerDatabase;
    private final Map<DatabaseCollection, MongoCollection<Document>> collections;

    private MongoClient client;
    private MongoDatabase database;

    private ItemDatabase itemDatabase;

    public Database(Main plugin) {
        super(plugin);

        playerDatabase = Maps.newConcurrentMap();
        collections = Maps.newHashMap();

        createConnection();
        load();
    }

    public void saveAll() {
        Bukkit.getOnlinePlayers().forEach(player -> removePlayerDatabase(player.getUniqueId()));
    }

    private void load() {
        // MMO Data
        final MongoCollection<Document> mmoData = getCollection(DatabaseCollection.MMO_DATA);

        itemDatabase = new ItemDatabase(mmoData, new Filter("type", "item_database"));
    }

    public void stopConnection() {
        try {
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nonnull
    public MongoCollection<Document> getCollection(DatabaseCollection collection) {
        return collections.computeIfAbsent(collection, fn -> database.getCollection(collection.path));
    }

    @Nonnull
    public PlayerDatabase getPlayerDatabase(UUID uuid) {
        PlayerDatabase database = playerDatabase.get(uuid);

        if (database != null) {
            return database;
        }

        final MongoCollection<Document> players = getCollection(DatabaseCollection.PLAYERS);
        database = new PlayerDatabase(uuid, new Filter("uuid", uuid.toString()), players);

        playerDatabase.put(uuid, database);
        return database;
    }

    public ItemDatabase getItemDatabase() {
        return itemDatabase;
    }

    public void removePlayerDatabase(UUID uuid) {
        final PlayerDatabase database = playerDatabase.remove(uuid);

        if (database == null) {
            return;
        }

        // Save it async!
        database.updateAsync();
    }

    private void createConnection() {
        if (client != null) {
            throw new IllegalStateException("Already connected!");
        }

        final FileConfiguration config = getPlugin().getConfig();
        final String connectionLink = config.getString("database.connection_link", null);
        final DatabaseType databaseType = Enums.byName(DatabaseType.class, config.getString("database.type", ""));

        if (connectionLink == null || connectionLink.isEmpty() || connectionLink.isBlank()) {
            disablePlugin("No connection link provided.");
            return;
        }

        if (databaseType == null) {
            disablePlugin("Invalid database type!");
            return;
        }

        client = MongoClients.create(connectionLink);
        database = client.getDatabase("mmo");

        // Load collection
        for (DatabaseCollection value : DatabaseCollection.values()) {
            collections.put(value, database.getCollection(value.path));
        }

    }
}
