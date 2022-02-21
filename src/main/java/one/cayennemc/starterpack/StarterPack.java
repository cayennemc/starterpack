package one.cayennemc.starterpack;

import net.fabricmc.api.ModInitializer;

import net.minecraft.item.ItemStack;
import one.cayennemc.starterpack.event.PlayerJoinCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class StarterPack implements ModInitializer {
    private List<ItemStack> stacks;

    @Override
    public void onInitialize() {
        Configuration cfg = new Configuration("config/starterpack.json");
        if (!cfg.isExists()) {
            try {
                cfg.restore();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            cfg.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        stacks = cfg.getItemStacks();

        PlayerJoinCallback.EVENT.register(player -> {
            IServerPlayerEntity iPlayer = (IServerPlayerEntity) player;
            if (!iPlayer.isPlayedBefore()) {
                if (!player.getWorld().isClient()) {
                    for (ItemStack itemStack : stacks) {
                        ItemStack stack = itemStack.copy();
                        player.getInventory().insertStack(stack);
                    }
                }
                iPlayer.setPlayedBefore(true);
            }
        });
    }
}
