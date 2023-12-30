package fr.dynamx.addons.immersive.common.infos;

import fr.dynamx.addons.immersive.common.modules.HerseModule;
import fr.dynamx.api.contentpack.object.subinfo.ISubInfoTypeOwner;
import fr.dynamx.api.contentpack.object.subinfo.SubInfoType;
import fr.dynamx.api.contentpack.registry.DefinitionType;
import fr.dynamx.api.contentpack.registry.PackFileProperty;
import fr.dynamx.api.contentpack.registry.RegisteredSubInfoType;
import fr.dynamx.api.contentpack.registry.SubInfoTypeRegistries;
import fr.dynamx.api.entities.modules.ModuleListBuilder;
import fr.dynamx.common.contentpack.type.objects.PropObject;
import fr.dynamx.common.entities.PackPhysicsEntity;
import fr.dynamx.common.entities.PropsEntity;

@RegisteredSubInfoType(
        name = "HarrowAddon",
        registries = {SubInfoTypeRegistries.PROPS},
        strictName = false
)
public class HerseInfo<T extends PropObject<T>> extends SubInfoType<T> {

    @PackFileProperty(configNames = {"IsHarrow"}, type = DefinitionType.DynamXDefinitionTypes.BOOL)
    public boolean isHerse;

    public HerseInfo(ISubInfoTypeOwner<T> owner) {
        super(owner);
    }

    @Override
    public void appendTo(T owner) {
        owner.addSubProperty(this);
    }

    @Override
    public void addModules(PackPhysicsEntity<?, ?> entity, ModuleListBuilder modules) {
        modules.add(new HerseModule((PropsEntity) entity));
    }

    @Override
    public String getName() {
        return "HerseInfo of " + getOwner().getFullName();
    }

}