package fr.dynamx.addons.immersive.common.infos;

import fr.dynamx.addons.immersive.common.modules.RadioModule;
import fr.dynamx.api.contentpack.object.subinfo.ISubInfoTypeOwner;
import fr.dynamx.api.contentpack.object.subinfo.SubInfoType;
import fr.dynamx.api.contentpack.registry.RegisteredSubInfoType;
import fr.dynamx.api.contentpack.registry.SubInfoTypeRegistries;
import fr.dynamx.api.entities.modules.ModuleListBuilder;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.PackPhysicsEntity;

@RegisteredSubInfoType(
        name = "RadioAddon",
        registries = {SubInfoTypeRegistries.WHEELED_VEHICLES},
        strictName = false
)
public class RadioAddonInfos<T extends ISubInfoTypeOwner<T>> extends SubInfoType<T> {

    public RadioAddonInfos(ISubInfoTypeOwner<T> owner) {
        super(owner);
    }

    @Override
    public void appendTo(T t) {
        owner.addSubProperty(this);
    }

    @Override
    public void addModules(PackPhysicsEntity<?, ?> entity, ModuleListBuilder modules) {
        if (modules.hasModuleOfClass(RadioModule.class)) { //Module yet added
            throw new IllegalStateException("More than one radio addon infos ("+getFullName()+") added to "+entity.getPackInfo().getFullName()+" "+entity);
        } else { //Module not yet added
            modules.add(new RadioModule((BaseVehicleEntity<?>) entity,this));
        }
    }

    @Override
    public String getName() {
        return "RadioAddon of " + owner.getName();
    }

    @Override
    public String getPackName() {
        return owner.getPackName();
    }
}
