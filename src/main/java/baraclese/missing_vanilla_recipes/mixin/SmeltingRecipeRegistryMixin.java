package baraclese.missing_vanilla_recipes.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmeltingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmeltingRecipeRegistry.class)
public abstract class SmeltingRecipeRegistryMixin
{
    @Shadow
    public abstract void method_3488(int i, ItemStack itemStack, float f);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerMissingRecipes(CallbackInfo ci)
    {
        // stone brick -> cracked stone brick
        this.method_3488(Block.STONE_BRICK.id, new ItemStack(Block.STONE_BRICK, 1, 2), 0.1F);
    }
}
