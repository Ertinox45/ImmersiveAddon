package fr.dynamx.addons.immersive.proxy;

import fr.dynamx.addons.immersive.common.items.ItemsRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(ItemsRegister.INSTANCE);
    }
}
