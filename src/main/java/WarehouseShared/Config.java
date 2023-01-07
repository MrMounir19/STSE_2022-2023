package WarehouseShared;

import com.google.gson.JsonObject;

/**
 * A way for behaviours to access the config through a static variable.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @since 07/01/2023
 */
public class Config {
    protected static JsonObject config;

    public static JsonObject getConfig() {
        return config;
    }

    public static void setConfig(JsonObject config) {
        Config.config = config;
    }
}
