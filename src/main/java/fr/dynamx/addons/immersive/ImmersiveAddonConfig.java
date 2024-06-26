package fr.dynamx.addons.immersive;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ImmersiveAddonConfig {
    private static Configuration configuration;
    public static boolean enableCarDamage;
    public static boolean enableBunnyHop;
    public static boolean enableAnimationPickingProps;
    public static boolean enableBlockOutline;
    public static boolean enablePickProps;
    public static boolean enableHideName;
    public static int versionCarModule;
    public static int division;
    public static int repairKitValue;
    public static boolean debug;
    public static void init(File file) {
        Configuration configuration = new Configuration(file);
        configuration.load();
        debug = configuration.getBoolean("Debug", "General", false, "If enabled, debug messages will be printed in the console.");
        enableCarDamage = configuration.getBoolean("EnableCarDamage", "CarConfig", true, "If enabled, cars will take damage when they crash.");
        enableBunnyHop = configuration.getBoolean("EnableBunnyHop", "General", true, "If enabled, anti bunny hop will be enabled.");
        enableBlockOutline = configuration.getBoolean("EnableBlockOutline", "ClientGeneral", false, "If enabled, the block outline will be enabled.");
        enablePickProps = configuration.getBoolean("EnablePickProps", "General", true, "If enabled, you will be able to pick up props with sneak click.");
        enableAnimationPickingProps = configuration.getBoolean("EnableAnimationPickingProps", "General", true, "If enabled, the animation for picking up props will be enabled.");
        enableHideName = configuration.getBoolean("EnableHideName", "General", true, "If enabled, the name of players will be hidden.");
        versionCarModule = configuration.getInt("VersionCarModule", "CarConfig", 3, 1, 3, "v1 : damage system with percentage and other vehicles, v2 : damage system with terrain, v3 : v1 and v2");
        division = configuration.getInt("Division", "CarConfig", 2, 1, 50, "The higher the number, the less damage the car will take.");
        repairKitValue = configuration.getInt("RepairKitValue", "CarConfig", 100, 1, 100, "The value of the repair kit in percentage. The higher the number, the more the car will be repaired.");
        configuration.save();
    }
}
