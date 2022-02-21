package one.cayennemc.starterpack.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import one.cayennemc.starterpack.event.PlayerJoinCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

    @Inject(at = @At("RETURN"), method = "onPlayerConnect")
    public void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerJoinCallback.EVENT.invoker().join(player);
    }
}
