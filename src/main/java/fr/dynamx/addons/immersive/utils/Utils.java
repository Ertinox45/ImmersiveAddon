package fr.dynamx.addons.immersive.utils;

public class Utils {

    public static boolean isUsingMod(String mainClass) {
        try {
            Class.forName(mainClass);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
