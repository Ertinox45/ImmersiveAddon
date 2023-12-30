package fr.dynamx.addons.immersive.common.infos;

import fr.dynamx.api.contentpack.object.subinfo.ISubInfoTypeOwner;
import fr.dynamx.api.contentpack.object.subinfo.SubInfoType;
import fr.dynamx.api.contentpack.registry.DefinitionType;
import fr.dynamx.api.contentpack.registry.PackFileProperty;
import fr.dynamx.api.contentpack.registry.RegisteredSubInfoType;
import fr.dynamx.api.contentpack.registry.SubInfoTypeRegistries;

@RegisteredSubInfoType(
        name = "HarrowAddon",
        registries = {SubInfoTypeRegistries.WHEELS},
        strictName = false
)
public class AntiPunctureInfo<T extends ISubInfoTypeOwner<T>> extends SubInfoType<T> {

    @PackFileProperty(configNames = {"CanFlattened"}, type = DefinitionType.DynamXDefinitionTypes.BOOL)
    public boolean canFlattened;

    public AntiPunctureInfo(ISubInfoTypeOwner<T> owner) {
        super(owner);
    }

    @Override
    public void appendTo(T partWheelInfo) {
        owner.addSubProperty(this);
    }

    @Override
    public String getName() {
        return "AntiPunctureInfo of " + getOwner().getFullName();
    }

    @Override
    public String getPackName() {
        return owner.getPackName();
    }

}