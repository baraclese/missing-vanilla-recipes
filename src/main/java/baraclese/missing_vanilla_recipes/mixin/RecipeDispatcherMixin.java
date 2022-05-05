package baraclese.missing_vanilla_recipes.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Mixin(RecipeDispatcher.class)
public abstract class RecipeDispatcherMixin
{
	@Shadow
	private List recipes = new ArrayList();

	@Shadow
	public abstract ShapedRecipeType registerShapedRecipe(ItemStack stack, Object... args);

	@Shadow
	public abstract void registerShapelessRecipe(ItemStack result, Object... args);

	@Inject(method = "<init>", at = @At("TAIL"))
	private void registerMissingRecipes(CallbackInfo ci)
	{
		// mossy cobblestone
		this.registerShapelessRecipe(new ItemStack(Block.MOSSY_COBBLESTONE, 1), Block.STONE_BRICKS, Block.VINE);
		// mossy stone brick
		this.registerShapelessRecipe(new ItemStack(Block.STONE_BRICK, 1, 1), Block.STONE_BRICK, Block.VINE);
		// chiseled stone brick
		this.registerShapedRecipe(new ItemStack(Block.STONE_BRICK, 1, 3), new Object[]{"#", "#", '#', new ItemStack(Block.STONE_SLAB, 1, 5)});

		Collections.sort(this.recipes, new Comparator<RecipeType>() {
			public int compare(RecipeType recipeType, RecipeType recipeType2) {
				if (recipeType instanceof ShapelessRecipeType && recipeType2 instanceof ShapedRecipeType) {
					return 1;
				} else if (recipeType2 instanceof ShapelessRecipeType && recipeType instanceof ShapedRecipeType) {
					return -1;
				} else if (recipeType2.getSize() < recipeType.getSize()) {
					return -1;
				} else {
					return recipeType2.getSize() > recipeType.getSize() ? 1 : 0;
				}
			}
		});
	}
}
