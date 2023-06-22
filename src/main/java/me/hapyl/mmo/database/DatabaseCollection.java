package me.hapyl.mmo.database;

/**
 * Represents a valid collections that are stored in the database.
 */
public enum DatabaseCollection {

    PLAYERS("players"),
    MMO_DATA("mmo_data"),

    ;

    public final String path;

    DatabaseCollection(String path) {
        this.path = path;
    }
}
