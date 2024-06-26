package fr.dynamx.addons.immersive.common.infos;

import com.jme3.math.Vector3f;
import fr.dynamx.api.contentpack.object.subinfo.ISubInfoTypeOwner;
import fr.dynamx.api.contentpack.object.subinfo.SubInfoType;
import fr.dynamx.api.contentpack.registry.DefinitionType;
import fr.dynamx.api.contentpack.registry.PackFileProperty;
import fr.dynamx.api.contentpack.registry.RegisteredSubInfoType;
import fr.dynamx.api.contentpack.registry.SubInfoTypeRegistries;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;

@RegisteredSubInfoType(name = "TowTruckInfo",registries = {SubInfoTypeRegistries.WHEELED_VEHICLES,  SubInfoTypeRegistries.HELICOPTER},strictName = false)
public class FlatbedTruckInfo extends SubInfoType<ModularVehicleInfo>
{
    @PackFileProperty(configNames = {"VehicleAttachPoint"}, type = DefinitionType.DynamXDefinitionTypes.VECTOR3F_INVERSED_Y)
    private Vector3f attachPointVehicle;

    public FlatbedTruckInfo(ISubInfoTypeOwner<ModularVehicleInfo> owner) { super(owner); }

    public void appendTo(ModularVehicleInfo owner) { owner.addSubProperty(this); }

    public String getName() { return "TowTruckInfo of " + getOwner().getName(); }

    public Vector3f getAttachPointVehicle() { return this.attachPointVehicle; }
}
