package org.fish.anticlout.client;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.mixin.event.interaction.ServerPlayNetworkHandlerMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.GameProfileCache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        mc = Minecraft.getInstance();

    }

    public static void getUUIDS()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(AnticloutClient.class.getResourceAsStream("/assets/anticlout/uuids.txt")));
        try {
            String line = reader.readLine();

            while (line != null) {
                uuids.add(UUID.fromString(line));
                line = reader.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
