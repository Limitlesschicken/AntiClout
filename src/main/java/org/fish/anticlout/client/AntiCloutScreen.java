package org.fish.anticlout.client;

import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;

import static org.fish.anticlout.client.AnticloutClient.mc;

public class AntiCloutScreen extends Screen {

    public static boolean blockSkin = true;
    public static boolean blockName = true;
    public static String name = "lil pup";
    public static boolean blockMaps = true;
    public static double mapBlockDistance = 30.0;
    private Button SkinButton;
    private Button nameButton;
    private Button mapButton;

    public AntiCloutScreen(Component component) {
        super(component);
    }

    private String boolToText(boolean bool) {
        if (bool)
            return "ON";
        else
            return "OFF";
    }

    @Override
    protected void init() {
        this.addRenderableWidget(new StringWidget(0, 40, this.width, 9, this.title, this.font));

        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(2);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(1);

        rowHelper.addChild(Button.builder(Component.literal("Player List"), (button) -> {
            mc.setScreen(new AntiCloutPlayerScreen(Component.literal("Player list")));
        }).build());

        SkinButton = new Button.Builder(Component.literal("Change skin: ").append(boolToText(blockSkin)), (button) -> {
            blockSkin = !blockSkin;
            SkinButton.setMessage(Component.literal("Change skin: ").append(boolToText(blockSkin)));
            Config.saveConfig();
        }).build();
        rowHelper.addChild(SkinButton);

        nameButton = new Button.Builder(Component.literal("Change name: ").append(boolToText(blockName)), (button) -> {
            blockName = !blockName;
            nameButton.setMessage(Component.literal("Change name: ").append(boolToText(blockName)));
            Config.saveConfig();
        }).build();
        rowHelper.addChild(nameButton);

        EditBox nameEdit = new EditBox(this.font, 0, 0, 150, 20, Component.literal("tex"));
        nameEdit.setValue(name);
        nameEdit.setResponder((text) -> {
            name = text;
            Config.saveConfig();
        });
        rowHelper.addChild(nameEdit);

        mapButton = new Button.Builder(Component.literal("Block maps: ").append(boolToText(blockMaps)), (button) -> {
            blockMaps = !blockMaps;
            mapButton.setMessage(Component.literal("Block maps: ").append(boolToText(blockMaps)));
            Config.saveConfig();
        }).build();
        rowHelper.addChild(mapButton);

        AbstractSliderButton mapDistance = new AbstractSliderButton(0, 0, 150, 20, Component.literal("Map block distance: ").append(String.valueOf((int) mapBlockDistance)), mapBlockDistance / 100) {
            @Override
            protected void updateMessage() {
                setMessage(Component.literal("Map block distance: " + (int) mapBlockDistance));
            }

            @Override
            protected void applyValue() {
                mapBlockDistance = this.value;
                mapBlockDistance *= 100.0;
                mapBlockDistance = (int) mapBlockDistance;
                Config.saveConfig();
            }
        };
        rowHelper.addChild(mapDistance);


        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, 0, 0, this.width, this.height, 0.5f, 0.5f);
        gridLayout.visitWidgets(this::addRenderableWidget);
    }
}
