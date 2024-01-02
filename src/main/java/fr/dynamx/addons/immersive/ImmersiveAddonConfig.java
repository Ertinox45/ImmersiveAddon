package fr.dynamx.addons.immersive;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ImmersiveAddonConfig {
    private static Configuration configuration;
    public static boolean enableCarDamage;
    public static boolean debug;
    public static void init(File file) {
        Configuration configuration = new Configuration(file);
        configuration.load();
        debug = configuration.getBoolean("Debug", "General", false, "If enabled, debug messages will be printed in the console.");
        enableCarDamage = configuration.getBoolean("EnableCarDamage", "CarConfig", false, "If enabled, cars will take damage when they crash.");
        configuration.save();
    }
}
