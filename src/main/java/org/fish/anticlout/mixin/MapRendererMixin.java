package org.fish.anticlout.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.fish.anticlout.client.AntiCloutScreen;
import org.fish.anticlout.client.AnticloutClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

import static org.fish.anticlout.client.AnticloutClient.mc;

@Mixin(MapRenderer.class)
public class MapRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(MapRenderState mapRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, boolean bl, int i, CallbackInfo ci) {
        if (mc.isSingleplayer()) return;
        if (!AntiCloutScreen.blockMaps) return;

        System.out.println(AnticloutClient.isChaserClose);

        if (AnticloutClient.isChaserClose)
            ci.cancel();

    }
}
