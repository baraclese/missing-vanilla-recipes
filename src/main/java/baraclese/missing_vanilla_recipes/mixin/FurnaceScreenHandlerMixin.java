package baraclese.missing_vanilla_recipes.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.slot.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmeltingRecipeRegistry;
import net.minecraft.screen.FurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FurnaceScreenHandler.class)
public class FurnaceScreenHandlerMixin
{
    // disable shift clicking of mossy, cracked or chiseled stone bricks into the furnace input slot
    @Redirect(
            method = "transferSlot(Lnet/minecraft/entity/player/PlayerEntity;I)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/SmeltingRecipeRegistry;method_3490(I)Lnet/minecraft/item/ItemStack;")
    )
    private ItemStack verifyItemsToTransferIntoFurnace(SmeltingRecipeRegistry recipes, int itemId, PlayerEntity p, int invSlot)
    {
        if (itemId == Block.STONE_BRICK.id)
        {
            Slot s = ((FurnaceScreenHandler)(Object)this).getSlot(invSlot);
            if (s != null && s.hasStack())
            {
                ItemStack items = s.getStack();
                if (items.getMeta() == 1    // mossy
                 || items.getMeta() == 2    // cracked
                 || items.getMeta() == 3    // chiseled
                )
                {
                    return null;
                }
            }
        }
        return recipes.method_3490(itemId);
    }
}
