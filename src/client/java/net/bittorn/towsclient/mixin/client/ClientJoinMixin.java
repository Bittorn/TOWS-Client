package net.bittorn.towsclient.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientJoinMixin {
	@Inject(at = @At("RETURN"), method = "onGameJoin")
	private void init(CallbackInfo info) {
		MinecraftClient.getInstance().getToastManager().add(
				SystemToast.create(MinecraftClient.getInstance(), SystemToast.Type.NARRATOR_TOGGLE, Text.of("TOWS Client loaded"), Text.empty())
		);
	}
}