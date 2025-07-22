package net.bittorn.towsclient.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ClientLoadMixin {
	@Inject(at = @At("HEAD"), method = "onFinishedLoading")
	private void init(CallbackInfo info) {
		MinecraftClient.getInstance().getToastManager().add(
				SystemToast.create(MinecraftClient.getInstance(), SystemToast.Type.NARRATOR_TOGGLE, Text.of("TOWS Client loaded successfully"), Text.empty())
		);
	}
}