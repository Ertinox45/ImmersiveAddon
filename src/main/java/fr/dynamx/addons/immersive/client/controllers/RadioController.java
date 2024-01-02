package fr.dynamx.addons.immersive.client.controllers;

import fr.aym.acsguis.component.GuiComponent;
import fr.dynamx.addons.immersive.common.helpers.ConfigReader;
import fr.dynamx.addons.immersive.common.modules.RadioModule;
import fr.dynamx.api.entities.modules.IVehicleController;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.utils.DynamXConfig;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.Collections;
import java.util.List;

public class RadioController implements IVehicleController {
    @SideOnly(Side.CLIENT)
    public static final KeyBinding play = new KeyBinding("Play (radio)", Keyboard.KEY_RMENU, "DynamX radio");
    @SideOnly(Side.CLIENT)
    public static final KeyBinding right = new KeyBinding("Change -> (radio)", Keyboard.KEY_RIGHT, "DynamX radio");
    @SideOnly(Side.CLIENT)
    public static final KeyBinding left = new KeyBinding("Change <- (radio)", Keyboard.KEY_LEFT, "DynamX radio");

    private final BaseVehicleEntity<?> entity;
    private final RadioModule module;

    public RadioController(BaseVehicleEntity<?> entity, RadioModule module) {
        this.entity = entity;
        this.module = module;

        unpress(play);
        unpress(right);
        unpress(left);
    }

    private void unpress(KeyBinding key) {
        while (key.isPressed()) {
        }
    }

    private int delay = 0;

    @Override
    @SideOnly(Side.CLIENT)
    public void update() {
        if (!entity.world.isRemote)
            return;

        if (module.getInfos() == null)
            return;
        if (delay > 3) {
            if (play.isPressed()) {
                module.setRadioOn(!module.isRadioOn());
                delay = 0;
            } else {
                if (module.isRadioOn()) {
                    if (right.isPressed()) {
                        module.setCurrentRadioIndex(Math.min(module.getCurrentRadioIndex() + 1, ConfigReader.frequencies.size() - 1));
                        delay = 0;
                    }
                    if (left.isPressed()) {
                        module.setCurrentRadioIndex(Math.max(module.getCurrentRadioIndex() - 1, 0));
                        delay = 0;
                    }
                }
            }
        } else {
            delay++;
        }
    }

    @Override
    public GuiComponent<?> createHud() {
        return null;
    }

    @Override
    public List<ResourceLocation> getHudCssStyles() {
        return Collections.EMPTY_LIST;
    }
}