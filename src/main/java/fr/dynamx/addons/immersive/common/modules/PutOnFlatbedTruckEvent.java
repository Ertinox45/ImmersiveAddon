package fr.dynamx.addons.immersive.common.modules;


import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.PhysicsEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

public class PutOnFlatbedTruckEvent extends Event {

    @Cancelable
    public static class PutVehicleOnFlatbedEvent extends VehicleEntityEvent {
        private final PhysicsEntity<?> putEntity;
        private final ItemStack treuil;

        public PutVehicleOnFlatbedEvent(BaseVehicleEntity<?> vehicleEntity, PhysicsEntity<?> putEntity, ItemStack treuil) {
            super(Side.SERVER, vehicleEntity);
            this.putEntity = putEntity;
            this.treuil = treuil;
        }


        public BaseVehicleEntity<?> getFlatbedEntity() { return (BaseVehicleEntity<?>) getEntity(); }



        public PhysicsEntity<?> getPutEntity() { return this.putEntity; }



        public ItemStack getTreuil() { return this.treuil; }
    }

}
