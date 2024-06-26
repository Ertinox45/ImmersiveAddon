package fr.dynamx.addons.immersive.common.modules;

import fr.dynamx.addons.immersive.ImmersiveAddon;
import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.network.sync.EntityVariable;
import fr.dynamx.api.network.sync.SynchronizationRules;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.common.entities.PackPhysicsEntity;
import fr.dynamx.common.entities.modules.engines.CarEngineModule;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
@SynchronizedEntityVariable.SynchronizedPhysicsModule(modid = ImmersiveAddon.ID)
public class DamageCarModule implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, IPhysicsModule.IEntityUpdateListener {

    public enum CarDamageType {
        WHEELS(1),
        ENGINE(2),
        CHASSIS(4),
        DOORS(8),
        AIRBAGS(16),
        LOW_REGIME(32);

        private int value;

        CarDamageType(int i) {
            this.value = i;
        }

        List<CarDamageType> getDamageTypes(int i) {
            List<CarDamageType> damageTypes = new ArrayList<>();
            for (CarDamageType type : CarDamageType.values()) {
                if ((i & type.value) == type.value) {
                    damageTypes.add(type);
                }
            }
            return damageTypes;
        }
    }

    private final PackPhysicsEntity<?, ?> entity;


    @SynchronizedEntityVariable(name = "percentage")
    private final EntityVariable<Integer> percentage = new EntityVariable<>(SynchronizationRules.SERVER_TO_CLIENTS, 0);

    @SynchronizedEntityVariable(name = "damagestypes")
    private final EntityVariable<Integer> damagestypes = new EntityVariable<>(SynchronizationRules.SERVER_TO_CLIENTS, 0);

    public DamageCarModule(PackPhysicsEntity<?, ?> entity) {
        this.entity = entity;
    }


    public Integer getDamagesTypes() {
        return damagestypes.get();
    }

    public void addDamageType(CarDamageType type) {
        this.damagestypes.set(this.damagestypes.get() | type.value);
    }

    public void removeDamageType(CarDamageType type) {
        this.damagestypes.set(this.damagestypes.get() & ~type.value);
    }

    public boolean hasDamageType(CarDamageType type) {
        return (this.damagestypes.get() & type.value) == type.value;
    }

    public void addPercentage(int percentage) {
        this.percentage.set(this.percentage.get() + percentage);
    }

    public void removePercentage(int percentage) {
        this.percentage.set(this.percentage.get() - percentage);
    }

    public void setDamagesTypes(Integer act) {
        this.damagestypes.set(act);
    }

    public Integer getPercentage() {
        return percentage.get();
    }

    public void setPercentage(Integer act) {
        this.percentage.set(act);
    }


    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger("percentage", percentage.get());
        tag.setInteger("damagestypes", damagestypes.get());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        percentage.set(tag.getInteger("percentage"));
        damagestypes.set(tag.getInteger("damagestypes"));
    }

}