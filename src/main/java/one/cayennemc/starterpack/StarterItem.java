package one.cayennemc.starterpack;

import net.minecraft.item.ItemStack;

public class StarterItem {
    private final int slot;
    private final ItemStack stack;

    public StarterItem(int slot, ItemStack stack) {
        this.slot = slot;
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getSlot() {
        return slot;
    }
}
