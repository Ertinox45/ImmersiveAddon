package fr.dynamx.addons.immersive.common.items;


import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisterHandler {

    @SubscribeEvent
    public void onItemsRegister(RegistryEvent.Register<Item> e) {
        ItemsRegister.INSTANCE.init();
        e.getRegistry().registerAll(ItemsRegister.INSTANCE.getItems().toArray(new Item[0]));
    }

}
