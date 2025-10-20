package net.bittorn.towsclient.mixin.client;

import net.bittorn.towsclient.TOWSClient;
//import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

//    private Entity entityTest = EntityType.VILLAGER.create(MinecraftClient.getInstance().world, SpawnReason.LOAD);

    @SuppressWarnings("CancellableInjectionUsage")
    @Inject(
            method = "shouldRender",
            at = @At("HEAD"),
            cancellable = true
    )

    private void onRender(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        // First check if mod is enabled
        if (!TOWSClient.CONFIG.enabled()) {
            return; // If mod is disabled, don't affect rendering
        }

        // Get entity ID and check if it should be hidden
        String entityId = Registries.ENTITY_TYPE.getId(entity.getType()).toString();
//        if (TOWSClient.CONFIG.debug()) {
//            cir.setReturnValue(false);
//            cir.cancel();
//        }
    }
}
