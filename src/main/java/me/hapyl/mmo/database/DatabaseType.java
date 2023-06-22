package me.hapyl.mmo.database;

/**
 * Represents a database type.
 */
public enum DatabaseType {

    /**
     * Production database.
     */
    PROD(""),
    /**
     * Public testing database, used to test feature on public servers.
     */
    TEST("@test"),
    /**
     * Development database. Should only be used in development.
     */
    DEV("@dev");

    private final String suffix;

    DatabaseType(String suffix) {
        this.suffix = suffix;
    }

    public boolean isDroppable() {
        return this == TEST || this == DEV;
    }

    public String getName() {
        return name().toLowerCase() + suffix;
    }
}
