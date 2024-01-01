package fr.dynamx.addons.immersive.client;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import fr.dynamx.addons.immersive.ImmersiveAddon;
import fr.dynamx.addons.immersive.utils.ModSyncedDataKeys;
import fr.dynamx.common.entities.BaseVehicleEntity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ImmersiveAddon.ID, value = Side.CLIENT)
public class HandAnimClientEventHandler {

    @SubscribeEvent
    public void onSetupAngles(ModelPlayerEvent.SetupAngles.Post event){
        setModel(event.getEntityPlayer(), event.getModelPlayer());
    }

    public static void setModel(EntityPlayer player, ModelBiped model) {
        if (player == null) {
            return;
        }
        if (SyncedPlayerData.instance().get(player, ModSyncedDataKeys.DRIVING)) {
            if (player.getRidingEntity() instanceof BaseVehicleEntity) {
                float rotation = SyncedPlayerData.instance().get(player, ModSyncedDataKeys.ROTATION_DRIVING);
                model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-90f + rotation);
                model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90f - rotation);
            }
        }
    }
}