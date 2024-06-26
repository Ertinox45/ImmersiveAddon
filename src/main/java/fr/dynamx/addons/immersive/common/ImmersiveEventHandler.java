package fr.dynamx.addons.immersive.common;

import com.jme3.math.Vector3f;
import fr.dynamx.addons.immersive.ImmersiveAddon;
import fr.dynamx.addons.immersive.ImmersiveAddonConfig;
import fr.dynamx.addons.immersive.common.items.ItemRepairWheel;
import fr.dynamx.addons.immersive.common.items.ItemRepairKit;
import fr.dynamx.addons.immersive.common.items.ItemTreuil;
import fr.dynamx.addons.immersive.common.modules.DamageCarModule;
import fr.dynamx.addons.immersive.common.modules.DamageModule;
import fr.dynamx.api.entities.VehicleEntityProperties;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.api.events.PhysicsEvent;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.api.physics.EnumBulletShapeType;
import fr.dynamx.common.contentpack.parts.PartWheel;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.PropsEntity;
import fr.dynamx.common.entities.modules.SeatsModule;
import fr.dynamx.common.entities.modules.WheelsModule;
import fr.dynamx.common.entities.modules.engines.HelicopterEngineModule;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.common.entities.vehicles.HelicopterEntity;
import fr.dynamx.common.physics.entities.modules.WheelsPhysicsHandler;
import fr.dynamx.common.physics.entities.parts.wheel.WheelPhysics;
import fr.dynamx.utils.DynamXUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ImmersiveAddon.ID)
public class ImmersiveEventHandler {

    @SubscribeEvent
    public void initModules(PhysicsEntityEvent.CreateModules<BaseVehicleEntity> event) {
        BaseVehicleEntity<?> entity = event.getEntity();
        event.getModuleList().add(new DamageModule(entity));
        event.getModuleList().add(new DamageCarModule(entity));
    }

    @SubscribeEvent
    public void onDynxCollide(PhysicsEvent.PhysicsCollision event) {
        if(ImmersiveAddonConfig.enableCarDamage) {
            if (ImmersiveAddonConfig.versionCarModule == 1 || ImmersiveAddonConfig.versionCarModule == 3) {
                if(event.getCollisionInfo().getEntityB().getType().equals(EnumBulletShapeType.SLOPE)){
                    if (event.getCollisionInfo().getEntityA().getType().equals(EnumBulletShapeType.VEHICLE)) {
                        BaseVehicleEntity<?> vehicle = (BaseVehicleEntity<?>) event.getCollisionInfo().getEntityA().getObjectIn();
                        if (vehicle.hasModuleOfType(DamageModule.class)) {
                            DamageModule damage = vehicle.getModuleByType(DamageModule.class);
                            damage.addDamage(0);
                        }
                    }
                }else if (event.getCollisionInfo().getEntityB().getType().equals(EnumBulletShapeType.TERRAIN)) {
                    if (event.getCollisionInfo().getEntityA().getType().equals(EnumBulletShapeType.VEHICLE)) {
                        BaseVehicleEntity<?> vehicle = (BaseVehicleEntity<?>) event.getCollisionInfo().getEntityA().getObjectIn();

                        Vector3f vel = vehicle.getPhysicsHandler().getLinearVelocity();

                        if (!(vel.y < 0 && vel.y > -1)) {
                            if ((vel.z > 5 || vel.z < -5) || (vel.x > 5 || vel.x < -5)) {
                                Entity player = vehicle.getModuleByType(SeatsModule.class).getControllingPassenger();

                                if (player == null) return;
                                if (ImmersiveAddonConfig.debug) {
                                    player.sendMessage(new TextComponentString("§cVous avez percuté un obstacle à " + DynamXUtils.getSpeed(vehicle) + " km/h."));
                                    player.sendMessage(new TextComponentString("Damage : " + (vel.z + vel.x) / 2));
                                }

                                if (vehicle.hasModuleOfType(DamageCarModule.class)) {
                                    DamageCarModule damage = vehicle.getModuleByType(DamageCarModule.class);
                                    damage.addPercentage(Math.abs((int) ((vel.z + vel.x) / 2)));
                                    if (ImmersiveAddonConfig.debug) {
                                        player.sendMessage(new TextComponentString("Total Damage : " + damage.getPercentage()));
                                    }
                                } else {
                                    player.sendMessage(new TextComponentString("§4Errored car."));
                                }


                                if (ImmersiveAddonConfig.versionCarModule == 3) {
                                    if (vehicle.hasModuleOfType(DamageModule.class)) {
                                        DamageModule damage = vehicle.getModuleByType(DamageModule.class);
                                        int speed = Math.round(DynamXUtils.getSpeed(vehicle));
                                        long damageCpt = Math.round(0.00243035 * (speed) + 0.434288 * speed);
                                        //damage.addDamage(Math.abs((vel.z + vel.x) / ImmersiveAddonConfig.division));
                                        damage.addDamage((float) damageCpt / ImmersiveAddonConfig.division);
                                        if (ImmersiveAddonConfig.debug) {
                                            player.sendMessage(new TextComponentString("Total Damage : " + damage.getDamage()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                HelicopterEntity helicopterEntity = null;
                if (event.getObject1().getObjectIn() instanceof HelicopterEntity && event.getObject2().getType() == EnumBulletShapeType.TERRAIN) {
                    helicopterEntity = (HelicopterEntity) event.getObject1().getObjectIn();
                } else if (event.getObject2().getObjectIn() instanceof HelicopterEntity && event.getObject1().getType() == EnumBulletShapeType.TERRAIN) {
                    helicopterEntity = (HelicopterEntity) event.getObject2().getObjectIn();
                }
                if (helicopterEntity != null) {
                    HelicopterEngineModule engine = (HelicopterEngineModule) helicopterEntity.getModuleByType(HelicopterEngineModule.class);
                    DamageModule helicopterDamageModule = (DamageModule) helicopterEntity.getModuleByType(DamageModule.class);
                    if (engine != null) {
                        float[] ab = engine.getEngineProperties();
                        if ((int) Math.abs(ab[VehicleEntityProperties.EnumEngineProperties.SPEED.ordinal()]) > 50) {
                            //DynamXPhysicsHelper.createExplosion(helicopterEntity, helicopterEntity.physicsPosition, 1);
                            helicopterEntity.world.createExplosion(null, helicopterEntity.posX, helicopterEntity.posY, helicopterEntity.posZ, 1, false);
                            helicopterEntity.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, true, helicopterEntity.posX, helicopterEntity.posY, helicopterEntity.posZ, 40, 40, 40, 40, 40, 40);
                            engine.setEngineStarted(false);
                            helicopterDamageModule.addDamage(100);
                        }
                    }
                }

            }
            else if (ImmersiveAddonConfig.versionCarModule == 2) {

                if (event.getObject1().getObjectIn() instanceof CarEntity && event.getObject2().getObjectIn() instanceof CarEntity) {
                    CarEntity<?> carO1 = (CarEntity) event.getObject1().getObjectIn();
                    CarEntity<?> carO2 = (CarEntity) event.getObject2().getObjectIn();
                    int speed = Math.max(DynamXUtils.getSpeed(carO1), DynamXUtils.getSpeed(carO2));
                    if (speed < 15) {
                        return;
                    }
                    DamageModule damageModule1 = carO1.getModuleByType(DamageModule.class);
                    DamageModule damageModule2 = carO2.getModuleByType(DamageModule.class);
                    long damage = Math.round(0.00243035 * (speed) + 0.434288 * speed);
                    if (damageModule1 != null)
                        damageModule1.addDamage(damage);
                    if (damageModule2 != null)
                        damageModule2.addDamage(damage);
                }

            }
        }
    }


    @SubscribeEvent
    public void interactVehicle(VehicleEntityEvent.PlayerInteract event) {

        EntityPlayer player = event.getPlayer();
        Item item = player.getHeldItemMainhand().getItem();
        if (item instanceof ItemTreuil) {
            ((ItemTreuil) item).interact(player);
            event.setCanceled(true);
        }

        if (event.getPart() instanceof PartWheel) {
            if (item instanceof ItemRepairWheel) {

                WheelsModule wheel = event.getEntity().getModuleByType(WheelsModule.class);
                if (wheel != null) {
                    WheelsPhysicsHandler wheelsPhysics = (WheelsPhysicsHandler) wheel.getPhysicsHandler();
                    if (wheelsPhysics != null) {
                        WheelPhysics w = wheelsPhysics.getWheelByPartIndex(event.getPart().getId());
                        if(w.isFlattened()) {
                            w.setFlattened(false);
                            player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Roue réparée."));
                            player.inventory.clearMatchingItems(item, -1, 1, null);
                        }
                    }
                }
            }
        }

        if(item instanceof ItemRepairKit){
            player.inventory.clearMatchingItems(item, -1, 1, null);
            DamageModule damage = event.getEntity().getModuleByType(DamageModule.class);
            if(damage != null){
                damage.repair(ImmersiveAddonConfig.repairKitValue);
                player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Réparation effectuée."));
                event.setCanceled(true);
            }
        }

    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        if(ImmersiveAddonConfig.enablePickProps) {
            if (player.isSneaking() && event.getTarget() instanceof PropsEntity) {
                PropsEntity<?> propsEntity = (PropsEntity<?>) event.getTarget();
                player.addItemStackToInventory(propsEntity.getPackInfo().getPickedResult(propsEntity.getMetadata()));
                propsEntity.setDead();
            }
        }
    }
}
