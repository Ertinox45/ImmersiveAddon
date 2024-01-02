package fr.dynamx.addons.immersive.utils;

import com.mrcrayfish.obfuscate.common.data.Serializers;
import com.mrcrayfish.obfuscate.common.data.SyncedDataKey;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import fr.dynamx.addons.immersive.ImmersiveAddon;
import net.minecraft.util.ResourceLocation;

public class ModSyncedDataKeys {

    public static final SyncedDataKey<Boolean> DRIVING = SyncedDataKey.builder(Serializers.BOOLEAN)
            .id(new ResourceLocation(ImmersiveAddon.ID, "driving"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();

    public static final SyncedDataKey<Float> ROTATION_DRIVING = SyncedDataKey.builder(Serializers.FLOAT)
            .id(new ResourceLocation(ImmersiveAddon.ID, "rotation_driving"))
            .defaultValueSupplier(() -> 0f)
            .resetOnDeath()
            .build();

    public static void initKeys() {
        SyncedPlayerData.instance().registerKey(ModSyncedDataKeys.DRIVING);
        SyncedPlayerData.instance().registerKey(ModSyncedDataKeys.ROTATION_DRIVING);
    }

}
