package fr.dynamx.addons.immersive.client.keybind;

import fr.dynamx.addons.immersive.ImmersiveAddon;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {
    private static String category = (ImmersiveAddon.NAME);
    //public static KeyBinding changeseat = new KeyBinding(("Choisir siege"), Keyboard.KEY_NUMPAD0, category);

    private KeyBindings() {};

    public static void register()
    {
        registerKeys(
                //changeseat
                );
    }

    private static void registerKeys(KeyBinding... keys)
    {
        for(KeyBinding key : keys) ClientRegistry.registerKeyBinding(key);
    }
}
