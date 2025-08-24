package org.fish.anticlout.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.Button;
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

public class AntiCloutPlayerScreen extends Screen {

    protected AntiCloutPlayerScreen(Component component) {
        super(component);
    }

    @Override
    public void init() {
        this.addRenderableWidget(new StringWidget(0, 10, this.width, 9, this.title, this.font));
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(2);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(3);

        for (int i = 0; i < AnticloutClient.uuids.size(); i++) {
            UUID uuid = AnticloutClient.uuids.get(i);

            StringWidget uuidString = new StringWidget(0, 0, mc.font.width(uuid.toString()), mc.font.lineHeight, Component.literal(uuid.toString()), this.font).alignCenter();
            rowHelper.addChild(uuidString, gridLayout.newCellSettings().padding(2, 8));

            int finalI = i;
            Button removeButton = new Button.Builder(Component.literal("Remove"), (button) -> {
                deleteUUID(finalI);
            }).build();
            rowHelper.addChild(removeButton);

            Button copyUUID = new Button.Builder(Component.literal("Copy"), (button) ->{
                mc.keyboardHandler.setClipboard(uuid.toString());
            }).build();
            rowHelper.addChild(copyUUID);
        }

        Button addUUID = new Button.Builder(Component.literal("+"), (button) -> {
            mc.setScreen(new SelectUUIDScreen(Component.literal("Select UUID")));
        }).build();

        rowHelper.addChild(addUUID, 3, gridLayout.newCellSettings().alignHorizontallyCenter());

        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, 0, 0, this.width, this.height, 0.5f, 0.1f);
        gridLayout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    public void onClose() {
        mc.setScreen(new AntiCloutScreen(Component.literal("AntiClout")));
    }

    private void deleteUUID(int index) {
        Path configPath = FabricLoader.getInstance().getConfigDir();
        Path uuidFile = configPath.resolve("AntiCloutUUIDS.txt");

        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(uuidFile));
            lines.remove(index);
            Files.write(uuidFile, lines);

            AnticloutClient.getUUIDS();
            mc.setScreen(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
