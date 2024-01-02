package fr.dynamx.addons.immersive;

import fr.dynamx.addons.immersive.client.HandAnimClientEventHandler;
import fr.dynamx.addons.immersive.client.keybind.KeyBindings;
import fr.dynamx.addons.immersive.common.HandAnimationEventHandler;
import fr.dynamx.addons.immersive.common.helpers.RadioPlayer;
import fr.dynamx.addons.immersive.common.network.ImmersiveAddonPacketHandler;
import fr.dynamx.addons.immersive.utils.ModSyncedDataKeys;
import fr.dynamx.api.contentpack.DynamXAddon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.IOException;

@Mod(modid = ImmersiveAddon.ID, name = ImmersiveAddon.NAME, version = "1.0", dependencies = "before: dynamxmod")
@DynamXAddon(modid = ImmersiveAddon.ID, name = ImmersiveAddon.NAME, version = "1.0")
public class ImmersiveAddon {
    public static final String ID = "dynamx_immersive";
    public static final String NAME = "ImmersiveAddon";

    public static RadioPlayer radioPlayer = new RadioPlayer();

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
            //MinecraftForge.EVENT_BUS.register(new Common());
            if (event.getSide().isClient()) {
                //    MinecraftForge.EVENT_BUS.register(new Client());
                KeyBindings.register();
            }

            ImmersiveAddonConfig.init(event.getSuggestedConfigurationFile());
            ImmersiveAddonPacketHandler.getInstance().registerPackets();

            //network = NetworkRegistry.INSTANCE.newSimpleChannel(ImmersiveAddon.ID);
            //network.registerMessage(PacketAttachTrailer.Handler.class, PacketAttachTrailer.class, 0, Side.SERVER);
        }
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    }
}