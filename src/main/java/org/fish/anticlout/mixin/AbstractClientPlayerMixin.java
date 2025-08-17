package org.fish.anticlout.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import org.fish.anticlout.client.AnticloutClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

import static org.fish.anticlout.client.AnticloutClient.mc;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Shadow @Nullable protected abstract PlayerInfo getPlayerInfo();

    @Shadow @Nullable private PlayerInfo playerInfo;

    @ModifyReturnValue(method = "getSkin", at = @At("RETURN"))
    public PlayerSkin getSkin(PlayerSkin original)
    {
        if (mc.isLocalServer()) return original;
        PlayerInfo info = this.playerInfo;
        for (UUID uuid : AnticloutClient.getUUIDS()) {
            if (info == null) return original;
            if (uuid.equals(info.getProfile().getId()))
                return new PlayerSkin(AnticloutClient.resources("textures/skin.png"), original.textureUrl(), original.capeTexture(), original.elytraTexture(), original.model(), original.secure());
        }



        return original;
    }
}
