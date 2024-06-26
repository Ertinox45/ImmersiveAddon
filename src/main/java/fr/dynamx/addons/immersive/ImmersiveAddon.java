package fr.dynamx.addons.immersive;

import fr.dynamx.addons.immersive.client.ClientEventHandler;
import fr.dynamx.addons.immersive.client.HandAnimClientEventHandler;
import fr.dynamx.addons.immersive.common.HandAnimationEventHandler;
import fr.dynamx.addons.immersive.common.ImmersiveEventHandler;
import fr.dynamx.addons.immersive.common.items.RegisterHandler;
import fr.dynamx.addons.immersive.common.network.ImmersiveAddonPacketHandler;
import fr.dynamx.addons.immersive.proxy.CommonProxy;
import fr.dynamx.addons.immersive.server.commands.CommandShowNames;
import fr.dynamx.addons.immersive.utils.ModSyncedDataKeys;
import fr.dynamx.addons.immersive.utils.Utils;
import fr.dynamx.api.contentpack.DynamXAddon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ImmersiveAddon.ID, name = ImmersiveAddon.NAME, version = "1.0", dependencies = "before: dynamxmod")
@DynamXAddon(modid = ImmersiveAddon.ID, name = ImmersiveAddon.NAME, version = "1.0")
public class ImmersiveAddon {
    public static final String ID = "dynamx_immersive";
    public static final String NAME = "ImmersiveAddon";


    @SidedProxy(modId = ID, clientSide = "fr.dynamx.addons.immersive.proxy.ClientProxy", serverSide = "fr.dynamx.addons.immersive.proxy.ServerProxy")
    public static CommonProxy proxy;
    //public static RadioPlayer radioPlayer = new RadioPlayer();

    @DynamXAddon.AddonEventSubscriber
    public static void initAddon() {
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        MinecraftForge.EVENT_BUS.register(new RegisterHandler());
        if(Utils.isUsingMod("com.mrcrayfish.obfuscate.Obfuscate") || Loader.isModLoaded("obfuscate")) {
            MinecraftForge.EVENT_BUS.register(new HandAnimationEventHandler());

            if (event.getSide().isClient()) {
                MinecraftForge.EVENT_BUS.register(new HandAnimClientEventHandler());
            }
        }

        if (event.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
            //KeyBindings.register();
        }

        MinecraftForge.EVENT_BUS.register(new ImmersiveEventHandler());

        ImmersiveAddonConfig.init(event.getSuggestedConfigurationFile());
        ImmersiveAddonPacketHandler.getInstance().registerPackets();

        //network = NetworkRegistry.INSTANCE.newSimpleChannel(ImmersiveAddon.ID);
        //network.registerMessage(PacketAttachTrailer.Handler.class, PacketAttachTrailer.class, 0, Side.SERVER);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {

        if(Utils.isUsingMod("com.mrcrayfish.obfuscate.Obfuscate") || Loader.isModLoaded("obfuscate")) {
            ModSyncedDataKeys.initKeys();
        }
        //network = NetworkRegistry.INSTANCE.newSimpleChannel(ImmersiveAddon.ID);
        //network.registerMessage(PacketAttachTrailer.Handler.class, PacketAttachTrailer.class, 0, Side.SERVER);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandShowNames());
    }
}