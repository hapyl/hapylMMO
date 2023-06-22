package me.hapyl.mmo.database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MMODataDatabase extends DatabaseElement {
    public MMODataDatabase(MongoCollection<Document> collection, Filter filter) {
        super(collection, filter);
    }
}
