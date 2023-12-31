package fr.dynamx.addons.immersive.common;

import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import fr.dynamx.addons.immersive.ImmersiveAddon;
import fr.dynamx.addons.immersive.utils.ModSyncedDataKeys;
import fr.dynamx.api.entities.VehicleEntityProperties;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.modules.SeatsModule;
import fr.dynamx.common.entities.modules.WheelsModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ImmersiveAddon.ID)
public class HandAnimationEventHandler {

    @SubscribeEvent
    public void onPhysicsUpdate(PhysicsEntityEvent.Update event) {
        if(!(event.getEntity() instanceof BaseVehicleEntity)){
            return;
        }
        BaseVehicleEntity<?> vehicleEntity = (BaseVehicleEntity<?>) event.getEntity();
        if(vehicleEntity.hasModuleOfType(SeatsModule.class) && vehicleEntity.hasModuleOfType(WheelsModule.class)) {
            SeatsModule seatsModule = vehicleEntity.getModuleByType(SeatsModule.class);
            WheelsModule wheelsModule = vehicleEntity.getModuleByType(WheelsModule.class);

            if (seatsModule.getControllingPassenger() == null || !(seatsModule.getControllingPassenger() instanceof EntityPlayer)) {
                return;
            }

            int directingWheel = VehicleEntityProperties.getPropertyIndex(vehicleEntity.getPackInfo().getDirectingWheel(), VehicleEntityProperties.EnumVisualProperties.STEER_ANGLE);

            EntityPlayer driverEntity = (EntityPlayer) seatsModule.getControllingPassenger();
            SyncedPlayerData.instance().set(driverEntity, ModSyncedDataKeys.ROTATION_DRIVING, wheelsModule.visualProperties[directingWheel] / 2);
        }
    }

    @SubscribeEvent
    public void onEntityMount(VehicleEntityEvent.EntityMount event) {
        if(!event.getSeat().isDriver()){
            return;
        }
        if(event.getEntityMounted() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityMounted();
            SyncedPlayerData.instance().set(player, ModSyncedDataKeys.DRIVING, true);
        }
    }

    @SubscribeEvent
    public void onEntityDismount(VehicleEntityEvent.EntityDismount event) {
        if(!event.getSeat().isDriver()){
            return;
        }
        if(event.getEntityDismounted() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityDismounted();
            SyncedPlayerData.instance().set(player, ModSyncedDataKeys.DRIVING, false);
            SyncedPlayerData.instance().set(player, ModSyncedDataKeys.ROTATION_DRIVING, 0f);
        }
    }
}