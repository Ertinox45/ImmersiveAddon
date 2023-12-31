package fr.dynamx.addons.immersive;

import fr.dynamx.addons.immersive.client.HandAnimClientEventHandler;
import fr.dynamx.addons.immersive.common.HandAnimationEventHandler;
import fr.dynamx.addons.immersive.utils.ModSyncedDataKeys;
import fr.dynamx.addons.immersive.utils.Utils;
import fr.dynamx.api.contentpack.DynamXAddon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ImmersiveAddon.ID, name = ImmersiveAddon.NAME, version = "1.0", dependencies = "before: dynamxmod; after: obfuscate")
@DynamXAddon(modid = ImmersiveAddon.ID, name = ImmersiveAddon.NAME, version = "1.0")
public class ImmersiveAddon {
    public static final String ID = "dynamx_immersive";
    public static final String NAME = "ImmersiveAddon";

    //public static SimpleNetworkWrapper network;

    @DynamXAddon.AddonEventSubscriber
    public static void initAddon() {
        //if (FMLCommonHandler.instance().getSide().isClient()) {}
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModSyncedDataKeys.initKeys();
        MinecraftForge.EVENT_BUS.register(new HandAnimationEventHandler());
        if (event.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new HandAnimClientEventHandler());
        }
        //network = NetworkRegistry.INSTANCE.newSimpleChannel(ImmersiveAddon.ID);
        //network.registerMessage(PacketAttachTrailer.Handler.class, PacketAttachTrailer.class, 0, Side.SERVER);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    }
}