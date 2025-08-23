package org.fish.anticlout.client;


import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

import static org.fish.anticlout.client.AnticloutClient.mc;

public class SelectUUIDScreen extends Screen {
    protected SelectUUIDScreen(Component component) {
        super(component);
    }

    private EditBox selectUUID;
    private Button addUUID;

    @Override
    public void init() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(2);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);

        this.addRenderableWidget(new StringWidget(0, 100, this.width, 9, Component.literal("Add UUID"), this.font));
        selectUUID = new EditBox(this.font, 0, 0, 150, 20, Component.literal("tex"));
        selectUUID.setMaxLength(36);
        addUUID = new Button.Builder(Component.literal("Add"), (button) -> {
            addUUID(selectUUID.getValue());
        }).build();

        addUUID.active = false;

        rowHelper.addChild(selectUUID);
        rowHelper.addChild(addUUID);

        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, 0, 0, this.width, this.height, 0.5f, 0.5f);
        gridLayout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    public void tick() {
        addUUID.active = verifyUUID(selectUUID.getValue());
    }

    @Override
    public void onClose() {
        mc.setScreen(new AntiCloutPlayerScreen(Component.literal("Player list")));
    }

    private void addUUID(String uuid) {
        Path configPath = FabricLoader.getInstance().getConfigDir();
        Path uuidFile = configPath.resolve("AntiCloutUUIDS.txt");

        if (!verifyUUID(uuid))
            return;

        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(uuidFile));
            lines.add(uuid);
            Files.write(uuidFile, lines);

            AnticloutClient.getUUIDS();
            mc.setScreen(new AntiCloutPlayerScreen(Component.literal("Player list")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean verifyUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
