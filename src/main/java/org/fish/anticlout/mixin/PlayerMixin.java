package org.fish.anticlout.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.fish.anticlout.client.AntiCloutScreen;
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
        if (mc.isSingleplayer()) return original;


        if (!AntiCloutScreen.blockName) return original;

        for (UUID uuid : AnticloutClient.uuids)
            if (uuid.equals(this.gameProfile.getId()))
                return Component.literal(AntiCloutScreen.name);

        return original;
    }
}
