package fr.dynamx.addons.immersive.common.items;

import com.jme3.bullet.joints.JointEnd;
import com.jme3.math.Vector3f;
import fr.dynamx.addons.immersive.common.infos.FlatbedTruckInfo;
import fr.dynamx.addons.immersive.common.modules.PutOnFlatbedTruckEvent;
import fr.dynamx.api.physics.BulletShapeType;
import fr.dynamx.api.physics.EnumBulletShapeType;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.PhysicsEntity;
import fr.dynamx.common.entities.modules.MovableModule;
import fr.dynamx.common.entities.modules.movables.AttachObjects;
import fr.dynamx.common.items.DynamXItemRegistry;
import fr.dynamx.common.physics.joints.EntityJoint;
import fr.dynamx.common.physics.joints.JointHandlerRegistry;
import fr.dynamx.utils.DynamXUtils;
import fr.dynamx.utils.maths.DynamXGeometry;
import fr.dynamx.utils.optimization.QuaternionPool;
import fr.dynamx.utils.optimization.Vector3fPool;
import fr.dynamx.utils.physics.PhysicsRaycastResult;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import java.util.function.Predicate;

public class ItemTreuil extends Item {

    public ItemTreuil(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(DynamXItemRegistry.objectTab);
        this.setMaxStackSize(1);
        ItemsRegister.INSTANCE.getItems().add(this);
    }

    public static void writeEntity(ItemStack stack, PhysicsEntity<?> entity) {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("Entity1", entity.getEntityId());
    }

    public static boolean hasEntity(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("Entity1", Constants.NBT.TAG_INT);
    }

    public static void removeEntity(ItemStack stack) {
        if (stack.hasTagCompound()) {
            stack.getTagCompound().removeTag("Entity1");
        }
    }

    public static PhysicsEntity<?> getEntity(ItemStack stack, World world) {
        if (hasEntity(stack)) {
            Entity e = world.getEntityByID(stack.getTagCompound().getInteger("Entity1"));
            if (e instanceof PhysicsEntity) {
                return (PhysicsEntity<?>) e;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (player.isSneaking()) {
            removeEntity(player.getHeldItemMainhand());

            sendChatMessage(player, "Reset de l'Item Treuil");
        } else {
            putOnTruck(player, false);
        }
        return true;
    }

    public void interact(EntityPlayer player) {
        if (player.isSneaking()) {
            removeEntity(player.getHeldItemMainhand());

            sendChatMessage(player, "Reset de l'Item Treuil");
        } else {
            putOnTruck(player, true);
        }
    }

    protected void putOnTruck(EntityPlayer player, boolean destroy) {

        Predicate<EnumBulletShapeType> predicateShape = p -> !p.isPlayer();

        PhysicsRaycastResult result = DynamXUtils.castRayFromEntity(player, 3.0F, predicateShape);

        if (result != null) {
            BulletShapeType<?> shapeType = (BulletShapeType)result.hitBody.getUserObject();
            if (destroy) {
                if (shapeType.getType().isBulletEntity()) {
                    if(((PhysicsEntity)shapeType.getObjectIn()).getJointsHandler().getJoints().size() > 0){
                        for (EntityJoint joint : ((PhysicsEntity)shapeType.getObjectIn()).getJointsHandler().getJoints()){
                            if(joint.getType().equals(MovableModule.JOINT_NAME) && joint.getJointId() == (byte)2){
                                ((PhysicsEntity)shapeType.getObjectIn()).getJointsHandler().removeJointsOfType(MovableModule.JOINT_NAME, (byte)2);
                                sendChatMessage(player, "Véhicule décroché");
                                return;
                            }

                            sendChatMessage(player, "Aucun véhicule accroché");
                        }
                    }
                }
            } else {
                ItemStack itemStack = player.getHeldItemMainhand();
                if (!hasEntity(itemStack)) {
                    if (!shapeType.getType().isTerrain()) {
                        if (!(shapeType.getObjectIn() instanceof BaseVehicleEntity) || ((ModularVehicleInfo) ((BaseVehicleEntity) shapeType.getObjectIn()).getPackInfo()).getSubPropertyByType(FlatbedTruckInfo.class) == null) {
                            if(shapeType.getObjectIn() instanceof PhysicsEntity) {
                                MovableModule movableModule = (MovableModule) ((PhysicsEntity) shapeType.getObjectIn()).getModuleByType(MovableModule.class);
                                movableModule.attachObjects.initObject(result.hitBody, result.hitBody.getPhysicsLocation(Vector3fPool.get()).subtractLocal(0.0F, -0.4F, 0.0F), JointEnd.A);
                                writeEntity(itemStack, (PhysicsEntity) shapeType.getObjectIn());

                                sendChatMessage(player, "Clique maintenant sur la dépanneuse");
                            }
                        } else {

                            sendChatMessage(player, "Clique d'abord sur le véhicule à remorquer");
                        }
                    }

                    else { sendChatMessage(player, "Ceci n'est pas un véhicule"); }

                } else {
                    PhysicsEntity<?> containedEntity = getEntity(itemStack, player.world);
                    if (shapeType.getObjectIn() == containedEntity) {

                        sendChatMessage(player, TextFormatting.RED + "● Vous devez sélectionner votre dépanneuse.");

                    } else if (!(shapeType.getObjectIn() instanceof BaseVehicleEntity) || ((ModularVehicleInfo)((BaseVehicleEntity)shapeType.getObjectIn()).getPackInfo()).getSubPropertyByType(FlatbedTruckInfo.class) == null) {

                        sendChatMessage(player, TextFormatting.RED + "● Vous devez sélectionner votre dépanneuse.");
                        sendChatMessage(player, TextFormatting.RED + "Pour réinitialiser votre selection, faites Clique Droit sur le véhicule.");

                    }else if(containedEntity.getDistance(((BaseVehicleEntity) shapeType.getObjectIn())) > 30){

                        sendChatMessage(player, TextFormatting.RED + "● Le véhicule est trop loin de votre dépanneuse.");

                        removeEntity(player.getHeldItemMainhand());
                        sendChatMessage(player, "Reset de l'Item Treuil");
                    } else if (containedEntity != null) {
                        if (MinecraftForge.EVENT_BUS.post(new PutOnFlatbedTruckEvent.PutVehicleOnFlatbedEvent((BaseVehicleEntity)shapeType.getObjectIn(), containedEntity, itemStack))) {
                            return;
                        }
                        FlatbedTruckInfo info = ((ModularVehicleInfo)((BaseVehicleEntity)shapeType.getObjectIn()).getPackInfo()).getSubPropertyByType(FlatbedTruckInfo.class);
                        Vector3f offset = DynamXGeometry.rotateVectorByQuaternion(info.getAttachPointVehicle(), result.hitBody.getPhysicsRotation(QuaternionPool.get()));
                        containedEntity.getPhysicsHandler().setPhysicsPosition(result.hitBody.getPhysicsLocation(Vector3fPool.get()).addLocal(offset));
                        containedEntity.getPhysicsHandler().setPhysicsRotation(result.hitBody.getPhysicsRotation(QuaternionPool.get()));
                        containedEntity.getPhysicsHandler().activate();

                        AttachObjects attachObjects = (containedEntity.getModuleByType(MovableModule.class)).attachObjects;
                        if (!shapeType.getType().isTerrain()) {
                            attachObjects.initObject(result.hitBody, result.hitBody.getPhysicsLocation(Vector3fPool.get()).addLocal(offset).addLocal(Vector3fPool.get(0.0F, 0.3F, 0.0F)), JointEnd.B);
                        } else {
                            sendChatMessage(player, "Ceci n'est pas un véhicule");
                        }
                        if (shapeType.getType().isBulletEntity()) {
                            DynamXContext.getPhysicsWorld(player.world).schedule(() -> JointHandlerRegistry.createJointWithOther(MovableModule.JOINT_NAME, containedEntity, (PhysicsEntity)shapeType.getObjectIn(), (byte)2));
                        } else {
                            DynamXContext.getPhysicsWorld(player.world).schedule(() -> JointHandlerRegistry.createJointWithSelf(MovableModule.JOINT_NAME, containedEntity, (byte)2));
                        }
                        sendChatMessage(player, "Véhicule accroché");
                        removeEntity(itemStack);
                    }
                }
            }
        }
    }

    private void sendChatMessage(EntityPlayer player, String message){
        if(!player.world.isRemote){
            player.sendMessage(new TextComponentString(message));
        }
    }
}