package fr.dynamx.addons.immersive.common.items;

import com.google.common.collect.Lists;
import fr.dynamx.addons.immersive.ImmersiveAddon;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = ImmersiveAddon.ID)
public class ItemsRegister{

    public static ItemsRegister INSTANCE = new ItemsRegister();

    private List<Item> items;
    public static Item TREUIL;
    public static Item REPAIRKIT;
    public static Item REPAIRWHEEL;

    public void init()
    {
        items = Lists.newArrayList();
        TREUIL = new ItemTreuil("treuil");
        REPAIRKIT = new ItemRepairKit("repairkit");
        REPAIRWHEEL = new ItemRepairWheel("repairwheel");
    }



    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        for(Item item : items)
        {
            registerItemsModels(item);
        }
    }

    public static void registerItemsModels(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(ImmersiveAddon.ID, item.getTranslationKey().substring(5)), "inventory"));
    }

    public List<Item> getItems() { return items; }

}
