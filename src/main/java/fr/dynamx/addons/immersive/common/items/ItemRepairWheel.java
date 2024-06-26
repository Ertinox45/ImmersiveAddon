package fr.dynamx.addons.immersive.common.items;

import fr.dynamx.common.items.DynamXItemRegistry;
import net.minecraft.item.Item;

public class ItemRepairWheel extends Item {

    public ItemRepairWheel(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(DynamXItemRegistry.objectTab);
        this.setMaxStackSize(4);
        ItemsRegister.INSTANCE.getItems().add(this);
    }
}