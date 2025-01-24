package morph.avaritia.compat.botania;

import morph.avaritia.Avaritia;
import morph.avaritia.compat.Compat;
import static morph.avaritia.init.ModBlocks.*;
import morph.avaritia.init.ModItems;
import morph.avaritia.recipe.ExtremeCraftingManager;
import morph.avaritia.recipe.extreme.ExtremeShapedRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.block.ModBlocks;

public class Tsundere {

    public static void preInit() throws Compat.ItemNotFoundException {

        gaia_block = registerBlock(new BlockGaia());
        registerItemBlock(gaia_block);

        infinitato = registerBlock(new BlockInfinitato());
        registerItemBlock(infinitato);
        GameRegistry.registerTileEntity(TileInfinitato.class, "avaritia_infinitato");

        BotaniaAPI.registerSubTile("asgardandelion", SubTileAsgardandelion.class);
        BotaniaAPI.registerSubTileSignature(SubTileAsgardandelion.class, new Signature("asgardandelion"));
        BotaniaAPI.addSubTileToCreativeMenu("asgardandelion");

        BotaniaAPI.registerSubTile("soarleander", SubTileSoarleander.class);
        BotaniaAPI.registerSubTileSignature(SubTileSoarleander.class, new Signature("soarleander"));
        BotaniaAPI.addSubTileToCreativeMenu("soarleander");
    }

    public static void init() throws Compat.ItemNotFoundException{

        ItemStack cheaty = getFlower("asgardandelion");

        SubTileAsgardandelion.lexicon = new LudicrousLexicon("asgardandelion", BotaniaAPI.categoryGenerationFlowers);
        SubTileAsgardandelion.lexicon.addPage(BotaniaAPI.internalHandler.textPage("avaritia.lexicon.asgardandelion.0"));
        SubTileAsgardandelion.lexicon.setIcon(cheaty);
        ExtremeCraftingManager.addRecipe("asgardandelion", cheaty,
                "   III   ",
                "  IIIII  ",
                "  IIXII  ",
                "  IIIII  ",
                "   III   ",
                " nn N nn ",
                "nnnnNnnnn",
                " nn N nn ",
                "    N    ",
                'I', "ingotInfinity",
                'X', new ItemStack(ModItems.resource, 1, 5),
                'N', "ingotCosmicNeutronium",
                'n', new ItemStack(ModItems.resource, 1, 3));

        ItemStack chicken = getFlower("soarleander");
        BotaniaAPI.registerRuneAltarRecipe(
                chicken, 8000, getFlower("gourmaryllis"),
                new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN),
                new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN),
                new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN),
                new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN)
        );
        SubTileSoarleander.lexicon = new LudicrousLexicon("soarleander", BotaniaAPI.categoryGenerationFlowers);
        SubTileSoarleander.lexicon.addPage(BotaniaAPI.internalHandler.textPage("avaritia.lexicon.soarleander.0"));
        SubTileSoarleander.lexicon.setIcon(chicken);
        SubTileSoarleander.lexicon.setLexiconPages(
                BotaniaAPI.internalHandler.textPage(Avaritia.MOD_ID + ".lexicon.soarleander.0"),
                BotaniaAPI.internalHandler.runeRecipePage(Avaritia.MOD_ID + ".lexicon.soarleander.1", new RecipeRuneAltar(
                        chicken, 8000, getFlower("gourmaryllis"),
                        new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN),
                        new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN),
                        new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN),
                        new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN)
                ))
        );

        Block potato = ModBlocks.tinyPotato;

        ExtremeCraftingManager.addRecipe("infinitato", new ItemStack(infinitato),
                "IIIIIIIII",
                "IIIIIIIII",
                "IIISISIII",
                "IIIIIIIII",
                "IISIXISII",
                "IIISSSIII",
                "IIIIIIIII",
                "IIIIIIIII",
                "IIIIIIIII",
                'I', new ItemStack(potato),
                'X', new ItemStack(ModItems.resource, 1, 5),
                'S', new ItemStack(Items.DIAMOND));

        BlockInfinitato.lexiconEntry = new LudicrousLexicon("infinitato", BotaniaAPI.categoryMisc);
        BlockInfinitato.lexiconEntry.setLexiconPages(
                BotaniaAPI.internalHandler.textPage("avaritia.lexicon.infinitato.0"),
                new PageLudicrousRecipe("avaritia.lexicon.infinitato.1", new ExtremeShapedRecipe(new ItemStack(infinitato), CraftingHelper.parseShaped(
                        "IIIIIIIII",
                        "IIIIIIIII",
                        "IIISISIII",
                        "IIIIIIIII",
                        "IISIXISII",
                        "IIISSSIII",
                        "IIIIIIIII",
                        "IIIIIIIII",
                        "IIIIIIIII",
                        'I', new ItemStack(potato),
                        'X', new ItemStack(ModItems.resource, 1, 5),
                        'S', new ItemStack(Items.DIAMOND))))
        ).setIcon(new ItemStack(infinitato));
    }
    private static ItemStack getFlower(String type) {
        ItemStack flower = new ItemStack(ModBlocks.specialFlower, 1, 0);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", type);
        flower.setTagCompound(tag);
        return flower;
    }
}
