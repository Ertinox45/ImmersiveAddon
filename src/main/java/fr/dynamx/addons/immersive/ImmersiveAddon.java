package fr.dynamx.addons.immersive;

import fr.dynamx.api.contentpack.DynamXAddon;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ImmersiveAddon.ID, name = ImmersiveAddon.NAME, version = "1.0", dependencies = "before: dynamxmod")
@DynamXAddon(modid = ImmersiveAddon.ID, name = ImmersiveAddon.NAME, version = "1.0")
public class ImmersiveAddon {
    public static final String ID = "dynamx_immersive";
    public static final String NAME = "ImmersiveAddon";

    //public static SimpleNetworkWrapper network;

    @DynamXAddon.AddonEventSubscriber
    public static void initAddon() {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            //...
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //MinecraftForge.EVENT_BUS.register(new Common());
        if (event.getSide().isClient()){
        //    MinecraftForge.EVENT_BUS.register(new Client());
        }

        //network = NetworkRegistry.INSTANCE.newSimpleChannel(ImmersiveAddon.ID);
        //network.registerMessage(PacketAttachTrailer.Handler.class, PacketAttachTrailer.class, 0, Side.SERVER);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    }
}