package fr.dynamx.addons.immersive.common.modules;

import com.jme3.math.Vector3f;
import fr.dynamx.addons.immersive.ImmersiveAddon;
import fr.dynamx.addons.immersive.common.infos.AntiPunctureInfo;
import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.PropsEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.dynamx.common.physics.entities.modules.WheelsPhysicsHandler;
import fr.dynamx.common.physics.entities.parts.wheel.WheelPhysics;
import fr.dynamx.utils.optimization.Vector3fPool;
import net.minecraft.util.math.Vec3d;

import java.util.List;

@SynchronizedEntityVariable.SynchronizedPhysicsModule(modid = ImmersiveAddon.ID)
public class HerseModule implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, IPhysicsModule.IPhysicsUpdateListener {

    private final PropsEntity<?> entity;

    public HerseModule(PropsEntity entity) {
        this.entity = entity;
    }

    @Override
    public void preUpdatePhysics(boolean simulatePhysics) {
        if (!simulatePhysics) {
            return;
        }

        List<CarEntity> entityList = entity.world.getEntitiesWithinAABB(CarEntity.class, entity.getEntityBoundingBox().grow(0.5, 0.5, 0.5));
        entityList.forEach(carEntity -> {
            if(carEntity.ticksExisted > 10) {
                WheelsPhysicsHandler wheelsPhysics = carEntity.getWheels().getPhysicsHandler();
                if (wheelsPhysics != null) {
                    for (WheelPhysics wheel : wheelsPhysics.getVehicleWheelData()) {
                        if (wheel != null) {
                            BaseVehicleEntity<?> vehicleEntity = carEntity;
                            AntiPunctureInfo info = wheel.getWheelInfo().getSubPropertyByType(AntiPunctureInfo.class);

                            if (info == null || info.canFlattened) {
                                if (!wheel.isFlattened()) {
                                    Vector3f pos = Vector3fPool.get();
                                    Vec3d vec = new Vec3d(wheel.getPhysicsWheel().getCollisionLocation(pos).x, wheel.getPhysicsWheel().getCollisionLocation(pos).y, wheel.getPhysicsWheel().getCollisionLocation(pos).z);
                                    if (entity.getEntityBoundingBox().contains(vec)) {
                                        wheel.setFlattened(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

}