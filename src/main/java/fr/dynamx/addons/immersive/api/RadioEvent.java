package fr.dynamx.addons.immersive.api;

import fr.dynamx.addons.immersive.ImmersiveAddon;
import fr.dynamx.addons.immersive.common.helpers.ConfigReader;
import fr.dynamx.addons.immersive.common.modules.RadioModule;
import fr.dynamx.addons.immersive.common.network.ImmersiveAddonPacketHandler;
import fr.dynamx.addons.immersive.common.network.packets.SendRadioFreqConfig;
import fr.dynamx.api.events.VehicleEntityEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = ImmersiveAddon.ID, value = Side.CLIENT)
public class RadioEvent {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void leave(VehicleEntityEvent.EntityDismount event) {
        if(ImmersiveAddon.radioPlayer != null) {
            if(event.getEntity().hasModuleOfType(RadioModule.class)) {
                RadioModule module = event.getEntity().getModuleByType(RadioModule.class);
                module.resetCached();
            }
            ImmersiveAddon.radioPlayer.stopRadio();
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        if(ImmersiveAddon.radioPlayer != null) {
            ImmersiveAddon.radioPlayer.stopRadio();
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedInEvent event) {
        try {
            ImmersiveAddonPacketHandler.getInstance().getNetwork().sendTo(new SendRadioFreqConfig(ConfigReader.getFileContent()), (EntityPlayerMP) event.player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
