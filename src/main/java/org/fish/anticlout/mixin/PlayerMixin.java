package org.fish.anticlout.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.fish.anticlout.client.AnticloutClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

import static org.fish.anticlout.client.AnticloutClient.mc;

@Mixin(Player.class)
public class PlayerMixin {

    @Shadow @Final private GameProfile gameProfile;

    @Shadow public AbstractContainerMenu containerMenu;

    @ModifyReturnValue(method = "getName", at = @At("RETURN"))
    public Component getName(Component original) {
        if (mc.isLocalServer()) return original;
        for (Player player : mc.level.players()) {

            if (player == mc.player) {
                continue;
            }

            for (UUID uuid : AnticloutClient.getUUIDS())
                if (uuid.equals(this.gameProfile.getId())) {
                    if (player.position().closerThan(mc.player.position(), 5)) {
                        AnticloutClient.isChaserClose = true;
                        break;
                    }

                }
            AnticloutClient.isChaserClose = false;
        }

        for (UUID uuid : AnticloutClient.getUUIDS())
            if (uuid.equals(this.gameProfile.getId()))
                return Component.literal("lil pup");

        return original;
    }
}
