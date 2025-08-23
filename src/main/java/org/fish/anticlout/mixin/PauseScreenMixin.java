package org.fish.anticlout.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.fish.anticlout.client.AntiCloutScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.fish.anticlout.client.AnticloutClient.mc;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {


    protected PauseScreenMixin(Component component) {
        super(component);
    }

    @Unique
    public void createAntiCloutButton() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().alignHorizontallyRight().alignVerticallyTop();
        gridLayout.defaultCellSetting().padding(5);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(1);


        Button settingsButton = new Button.Builder(Component.literal("AntiClout"), (button) -> {
            mc.setScreen(new AntiCloutScreen(Component.literal("AntiClout")));
        }).build();

        rowHelper.addChild(settingsButton);
        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, 0, 0, this.width, this.height, 1.0F, 0.0F);
        gridLayout.visitWidgets(this::addRenderableWidget);

    }

    @Inject(method = "createPauseMenu", at = @At("HEAD"))
    public void createPauseMenu(CallbackInfo ci)
    {
        createAntiCloutButton();
    }
}
