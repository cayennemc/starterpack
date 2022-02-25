package one.cayennemc.starterpack;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import one.cayennemc.starterpack.event.PlayerJoinCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class StarterPack implements ModInitializer {
    private List<StarterItem> items;

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

        items = cfg.getStarterItems();

        PlayerJoinCallback.EVENT.register(player -> {
            IServerPlayerEntity iPlayer = (IServerPlayerEntity) player;
            if (!iPlayer.isPlayedBefore()) {
                PlayerInventory inventory = player.getInventory();
                if (!player.getWorld().isClient()) {
                    for (StarterItem item: items) {
                        ItemStack stack = item.getStack().copy();
                        int slot = item.getSlot();
                        if (slot == -1) {
                            inventory.insertStack(stack);
                        } else {
                            inventory.setStack(slot, stack);
                        }
                    }
                }
                iPlayer.setPlayedBefore(true);
            }
        });
    }
}
