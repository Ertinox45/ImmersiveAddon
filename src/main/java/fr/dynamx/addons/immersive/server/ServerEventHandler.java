package fr.dynamx.addons.immersive.server;

import com.jme3.math.Vector3f;
import fr.dynamx.addons.immersive.ImmersiveAddon;
import fr.dynamx.addons.immersive.ImmersiveAddonConfig;
import fr.dynamx.addons.immersive.common.modules.DamageCarModule;
import fr.dynamx.api.events.PhysicsEvent;
import fr.dynamx.api.physics.BulletShapeType;
import fr.dynamx.api.physics.EnumBulletShapeType;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.modules.SeatsModule;
import fr.dynamx.utils.DynamXUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = {Side.SERVER}, modid = ImmersiveAddon.ID)
public class ServerEventHandler {
    @SubscribeEvent
    public void onDynxCollide(PhysicsEvent.PhysicsCollision e) {
        if(e.getCollisionInfo().getEntityB().getType().equals(EnumBulletShapeType.TERRAIN) && ImmersiveAddonConfig.enableCarDamage) {
            BulletShapeType<?> b = e.getCollisionInfo().getEntityB();
            if(e.getCollisionInfo().getEntityA().getType().equals(EnumBulletShapeType.VEHICLE)) {
                BaseVehicleEntity<?> vehicle = (BaseVehicleEntity<?>) e.getCollisionInfo().getEntityA().getObjectIn();

                Vector3f vel = vehicle.getPhysicsHandler().getLinearVelocity();

                if(!(vel.y < 0 && vel.y > -1)) {
                    if((vel.z > 5 || vel.z < -5) || (vel.x > 5 || vel.x < -5)) {
                        Entity player = vehicle.getModuleByType(SeatsModule.class).getControllingPassenger();


                        if(player == null) return;
                        if(ImmersiveAddonConfig.debug) {
                            player.sendMessage(new TextComponentString("§cVous avez percuté un obstacle à " + DynamXUtils.getSpeed(vehicle) + " km/h."));
                            player.sendMessage(new TextComponentString("Damage : " + (vel.z + vel.x) / 2));
                        }

                        if(vehicle.hasModuleOfType(DamageCarModule.class)) {
                            DamageCarModule damage = vehicle.getModuleByType(DamageCarModule.class);
                            damage.addPercentage(Math.abs((int) ((vel.z + vel.x) / 2)));
                            player.sendMessage(new TextComponentString("Total Damage : " + damage.getPercentage()));
                        } else {
                            player.sendMessage(new TextComponentString("§4Errored car."));
                        }
                    }
                }
            }
        }
    }
}
