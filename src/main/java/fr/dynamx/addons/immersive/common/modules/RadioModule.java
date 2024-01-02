package fr.dynamx.addons.immersive.common.modules;

import fr.dynamx.addons.immersive.ImmersiveAddon;
import fr.dynamx.addons.immersive.client.controllers.RadioController;
import fr.dynamx.addons.immersive.common.helpers.ConfigReader;
import fr.dynamx.addons.immersive.common.helpers.RadioPlayer;
import fr.dynamx.addons.immersive.common.infos.RadioAddonInfos;
import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.entities.modules.IVehicleController;
import fr.dynamx.api.network.sync.SimulationHolder;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.modules.SeatsModule;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;

@SynchronizedEntityVariable.SynchronizedPhysicsModule(modid = ImmersiveAddon.ID)
public class RadioModule implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, IPhysicsModule.IEntityUpdateListener {
    private RadioController controller;
    private final BaseVehicleEntity<?> entity;

    private final RadioAddonInfos infos;

    private int currentRadioIndex = 0;
    private boolean isRadioOn;

    public RadioModule(BaseVehicleEntity<?> entity, RadioAddonInfos<?> infos) {
        System.out.println("RadioModule");
        this.entity = entity;
        this.infos = infos;
        if (entity.world.isRemote) {
            controller = new RadioController(entity, this);
        }
    }

    @Override
    public boolean listenEntityUpdates(Side side) {
        return true;
    }

    private int cachedRadioIndex = -1;
    private boolean cachedRadioOn = false;

    public void resetCached()
    {
        cachedRadioOn = false;
        cachedRadioIndex = -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateEntity() {
        if(FMLCommonHandler.instance().getSide().isClient())
        {
            if(!entity.world.isRemote)
                return;

            SeatsModule seatsModule = entity.getModuleByType(SeatsModule.class);
            if(seatsModule == null)
                return;

            if(!seatsModule.isEntitySitting(Minecraft.getMinecraft().player))
                return;

            if(ImmersiveAddon.radioPlayer == null)
            {
                ImmersiveAddon.radioPlayer = new RadioPlayer();
            }

            if(ImmersiveAddon.radioPlayer != null)
            {
                ImmersiveAddon.radioPlayer.setGain(Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS));
            }

            if(cachedRadioOn != isRadioOn)
            {
                cachedRadioOn = isRadioOn;
                if(isRadioOn)
                {
                    System.out.println("RadioModule.updateEntity() - isRadioOn");
                    ImmersiveAddon.radioPlayer.resume();
                    Minecraft.getMinecraft().player.sendStatusMessage(new net.minecraft.util.text.TextComponentString("§c§lRadio: §r§e" + ConfigReader.frequencies.get(getCurrentRadioIndex()).getName()), true);
                    try {
                        ImmersiveAddon.radioPlayer.playRadio(new URL(ConfigReader.frequencies.get(getCurrentRadioIndex()).getUrl()));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Minecraft.getMinecraft().player.sendStatusMessage(new net.minecraft.util.text.TextComponentString("§c§lRadio: §r§eRadio is off"), true);
                    ImmersiveAddon.radioPlayer.pause();
                    resetCached();
                }
            }
            if(cachedRadioIndex != getCurrentRadioIndex())
            {
                cachedRadioIndex = getCurrentRadioIndex();
                if(isRadioOn)
                {
                    try {
                        Minecraft.getMinecraft().player.sendStatusMessage(new net.minecraft.util.text.TextComponentString("§c§lRadio: §r§e" + ConfigReader.frequencies.get(getCurrentRadioIndex()).getName()), true);
                        ImmersiveAddon.radioPlayer.playRadio(new URL(ConfigReader.frequencies.get(getCurrentRadioIndex()).getUrl()));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public IVehicleController createNewController() {
        return controller;
    }

    public int getCurrentRadioIndex() {
        return currentRadioIndex;
    }

    public void setCurrentRadioIndex(int currentRadioIndex) {
        this.currentRadioIndex = currentRadioIndex;
    }

    public boolean isRadioOn() {
        return isRadioOn;
    }

    public void setRadioOn(boolean radioOn) {
        isRadioOn = radioOn;
    }

    public RadioAddonInfos getInfos() {
        return infos;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger("currentRadioIndex", currentRadioIndex);
        tag.setBoolean("isRadioOn", isRadioOn);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        currentRadioIndex = tag.getInteger("currentRadioIndex");
        isRadioOn = tag.getBoolean("isRadioOn");
    }
}