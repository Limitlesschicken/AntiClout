package org.fish.anticlout.client;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.NativeImage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.mixin.event.interaction.ServerPlayNetworkHandlerMixin;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.CloudRenderer;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ReloadableTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.network.ClientboundPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.GameProfileCache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.ArrayList;

public class AnticloutClient implements ClientModInitializer {

    public static ResourceLocation resources(String location) {
        return ResourceLocation.fromNamespaceAndPath("anticlout", location);
    }

    public static Minecraft mc;

    public static boolean isChaserClose = false;

    public static ArrayList<UUID> uuids = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        getUUIDS();
        Config.createFile();
        Config.loadConfig();
        mc = Minecraft.getInstance();
    }

    public static void getUUIDS() {
        Path configPath = FabricLoader.getInstance().getConfigDir();
        File uuidFile = configPath.resolve("AntiCloutUUIDS.txt").toFile();

        try {
            if (!uuidFile.exists()) {
                System.out.println("Creating UUID file");
                uuidFile.createNewFile();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(uuidFile))) {
            String line = reader.readLine();
            uuids.clear();
            while (line != null) {
                uuids.add(UUID.fromString(line));
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
