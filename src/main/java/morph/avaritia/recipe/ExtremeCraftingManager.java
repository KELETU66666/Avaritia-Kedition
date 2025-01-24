package morph.avaritia.recipe;

import morph.avaritia.Avaritia;
import morph.avaritia.recipe.extreme.ExtremeShapedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;

public class ExtremeCraftingManager {
	
	public static void addRecipe(String regName, ItemStack result, Object...objects) {
		Object[] list = new Object[objects.length + 1];
		list[0] = false;
		System.arraycopy(objects, 0, list, 1, objects.length);
		
		ShapedPrimer primer = CraftingHelper.parseShaped(list);
		primer.mirrored = false;
		
		ExtremeShapedRecipe recipe = new ExtremeShapedRecipe(result, primer);
		recipe.setRegistryName(Avaritia.MOD_ID, regName);
		AvaritiaRecipeManager.EXTREME_RECIPES.put(recipe.getRegistryName(), recipe);
	}

}