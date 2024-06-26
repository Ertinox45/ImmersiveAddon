package fr.dynamx.addons.immersive.client;

import com.modularwarfare.api.RenderBonesEvent;
import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import fr.dynamx.addons.immersive.ImmersiveAddonConfig;
import fr.dynamx.addons.immersive.utils.ModSyncedDataKeys;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.BaseVehicleEntity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandAnimClientEventHandler {

    @SubscribeEvent
    public void onSetupAngles(ModelPlayerEvent.SetupAngles.Post event){
        setModel(event.getEntityPlayer(), event.getModelPlayer());
    }

    @SubscribeEvent
    public void onRenderBonesEvent(RenderBonesEvent.RotationAngles event) {
        if (event.entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityIn;
            setModel(player, event.bones);
        }
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
        if(ImmersiveAddonConfig.enableAnimationPickingProps) {
            if (DynamXContext.getPlayerPickingObjects().containsKey(player.getEntityId())) {
                model.bipedRightArm.rotateAngleX = -1.48F + (model).bipedHead.rotateAngleX;
                model.bipedLeftArm.rotateAngleX = -1.48F + (model).bipedHead.rotateAngleX;
                model.bipedRightArm.rotateAngleY = -0.1F + (model).bipedHead.rotateAngleY;
                model.bipedLeftArm.rotateAngleY = -0.1F + (model).bipedHead.rotateAngleY;
            }
        }
    }


}