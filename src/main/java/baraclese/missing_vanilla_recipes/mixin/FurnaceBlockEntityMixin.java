package baraclese.missing_vanilla_recipes.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceBlockEntity.class)
public abstract class FurnaceBlockEntityMixin
{
    // disable smelting of mossy, cracked or chiseled stone bricks into cracked stone bricks
    @Inject(
            method = "method_525()Z",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/recipe/SmeltingRecipeRegistry;method_3490(I)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    private void disableInvalidStoneBrickSmelting(CallbackInfoReturnable<Boolean> cir)
    {
        ItemStack inputItems = ((FurnaceBlockEntity)(Object)this).getInvStack(0);
        if (inputItems.id == Block.STONE_BRICK.id &&
                (
                        inputItems.getMeta() == 1    // mossy
                     || inputItems.getMeta() == 2    // cracked
                     || inputItems.getMeta() == 3    // chiseled
                )
        )
        {
            cir.setReturnValue(false);
        }
    }
}
