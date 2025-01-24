package morph.avaritia.proxy;

import com.mojang.authlib.GameProfile;
import morph.avaritia.Avaritia;
import morph.avaritia.api.registration.IModelRegister;
import morph.avaritia.client.gui.GUIHandler;
import morph.avaritia.compat.Compat;
import static morph.avaritia.compat.Compat.*;
import morph.avaritia.compat.bloodmagic.Bloody;
import morph.avaritia.compat.botania.InfiniteFoxes;
import morph.avaritia.compat.botania.Tsundere;
import morph.avaritia.compat.thaumcraft.Lucrum;
import morph.avaritia.entity.EntityEndestPearl;
import morph.avaritia.entity.EntityGapingVoid;
import morph.avaritia.entity.EntityHeavenArrow;
import morph.avaritia.entity.EntityHeavenSubArrow;
import morph.avaritia.handler.AbilityHandler;
import morph.avaritia.handler.AvaritiaEventHandler;
import morph.avaritia.handler.ConfigHandler;
import morph.avaritia.init.ModBlocks;
import morph.avaritia.init.ModItems;
import morph.avaritia.recipe.ExtremeCraftingManager;
import morph.avaritia.util.Lumberjack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;

import java.util.UUID;

public class Proxy {

    public static final GameProfile avaritiaFakePlayer = new GameProfile(UUID.fromString("32283731-bbef-487c-bb69-c7e32f84ed27"), "[Avaritia]");

    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        ModItems.init();
        ModBlocks.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Avaritia.instance, new GUIHandler());
        MinecraftForge.EVENT_BUS.register(new AbilityHandler());
        MinecraftForge.EVENT_BUS.register(new AvaritiaEventHandler());

        EntityRegistry.registerModEntity(new ResourceLocation("avaritia:endest_pearl"), EntityEndestPearl.class, "EndestPearl", 1, Avaritia.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation("avaritia:gaping_void"), EntityGapingVoid.class, "GapingVoid", 2, Avaritia.instance, 256, 10, false);
        EntityRegistry.registerModEntity(new ResourceLocation("avaritia:heaven_arrow"), EntityHeavenArrow.class, "HeavenArrow", 3, Avaritia.instance, 32, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation("avaritia:heaven_sub_arrow"), EntityHeavenSubArrow.class, "HeavenSubArrow", 4, Avaritia.instance, 32, 2, true);


        if(Compat.thaumic) {
            try {
                Lucrum.preInit();
            } catch(Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia accumulated too much Warp!");
                e.printStackTrace();
                Compat.thaumic = false;
            }
        }

        if(Compat.blood) {
            try {
                Bloody.preInit();
            } catch (Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia decided to use a Fallen Kanade instead.");
                e.printStackTrace();
                Compat.blood = false;
            }
        }

        if(Compat.botan) {
            try {
                Tsundere.preInit();
            } catch(Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia is wondering where all the dayblooms went.");
                e.printStackTrace();
                Compat.thaumic = false;
            }
        }
    }

    public void init(FMLInitializationEvent event) {
        if(Compat.thaumic) {
            try {
                Lucrum.init();
            } catch(Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia accumulated too much Warp!");
                e.printStackTrace();
                Compat.thaumic = false;
            }
        }

        if(Compat.botan) {
            try {
                Tsundere.init();
            } catch(Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia is wondering where all the dayblooms went.");
                e.printStackTrace();
                Compat.botan = false;
            }
        }

        if(Loader.isModLoaded("tails")){
            try
            {
                InfiniteFoxes.floof();
            }
            catch(Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia was not fluffy enough!");
                e.printStackTrace();
            }
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
        if(Compat.thaumic) {
            try {
                Lucrum.postInit();
            } catch(Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia accumulated too much Warp!");
                e.printStackTrace();
                Compat.thaumic = false;
            }
        }
    }

    public void initRecipes(){
        if(Compat.thaumic) {
            try {
                Lucrum.initRecipes();
            } catch(Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia accumulated too much Warp!");
                e.printStackTrace();
                Compat.thaumic = false;
            }
        }

        if(Compat.botan) {
            try {
                Tsundere.initRecipes();
            } catch(Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia is wondering where all the dayblooms went.");
                e.printStackTrace();
                Compat.botan = false;
            }
        }


        if(Compat.blood) {
            try {
                Bloody.initRecipes();
            } catch (Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia decided to use a Fallen Kanade instead.");
                e.printStackTrace();
                Compat.blood = false;
            }
        }
        if(Compat.te){
            try {
                ItemStack cell = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("thermalexpansion", "cell")));
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Creative", (byte) 1);
                cell.setTagCompound(tag);
                ExtremeCraftingManager.addRecipe("creative_te_cell", cell,
                        "IIIIRIIII",
                        "IEEEREEEI",
                        "IERRRRREI",
                        "IERRRRREI",
                        "RRRRXRRRR",
                        "IERRRRREI",
                        "IERRRRREI",
                        "IEEEREEEI",
                        "IIIIRIIII",
                        'X', new ItemStack(ModBlocks.resource, 1, 1),
                        'E', "blockEnderium",
                        'I', "ingotInfinity",
                        'R', new ItemStack(ModItems.singularity, 1, 3));
            }
            catch (Throwable e){
                Lumberjack.log(Level.INFO, e, "Avaritia forgot one of Thermal Expansion's 500 prerequisites.");
                te = false;
            }
        }

        if(ae2){
            try {
                Item resource = getItem("appliedenergistics2", "material");

                Block creative = getBlock("appliedenergistics2", "creative_energy_cell");
                Block dense = getBlock("appliedenergistics2", "dense_energy_cell");

                ExtremeCraftingManager.addRecipe("creative_ae2_cell", new ItemStack(creative, 1, 0),
                        "IIIIDIIII",
                        "IEEEDEEEI",
                        "IEEEDEEEI",
                        "IEEEDEEEI",
                        "DDDDDDDDD",
                        "IEEEDEEEI",
                        "IEEEDEEEI",
                        "IEEEDEEEI",
                        "IIIIDIIII",
                        'D', new ItemStack(resource, 1, 24),
                        'E', new ItemStack(dense, 1, 0),
                        'I', "ingotInfinity");
            }
            catch (Throwable e){
                Lumberjack.log(Level.INFO, "Avaritia couldn't figure out how channels work.");
                e.printStackTrace();
                ae2 = false;
            }
        }
    }

    public void addModelRegister(IModelRegister register) {

    }

    public boolean isClient() {
        return false;
    }

    public boolean isServer() {
        return true;
    }

    public World getClientWorld() {
        return null;
    }
}
