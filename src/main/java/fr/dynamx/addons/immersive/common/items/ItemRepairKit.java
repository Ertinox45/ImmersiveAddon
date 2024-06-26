package fr.dynamx.addons.immersive.common.items;

import fr.dynamx.common.items.DynamXItemRegistry;
import net.minecraft.item.Item;

public class ItemRepairKit extends Item {

    public ItemRepairKit(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(DynamXItemRegistry.objectTab);
        this.setMaxStackSize(1);
        ItemsRegister.INSTANCE.getItems().add(this);
    }
}