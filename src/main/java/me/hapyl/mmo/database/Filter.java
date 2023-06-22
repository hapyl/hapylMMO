package me.hapyl.mmo.database;

import org.bson.Document;

public class Filter extends Document {

    public Filter(String name, Object value) {
        put(name, value);
    }

}
