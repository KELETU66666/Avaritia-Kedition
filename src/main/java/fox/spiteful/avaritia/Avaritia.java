package fox.spiteful.avaritia;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import fox.spiteful.avaritia.blocks.LudicrousBlocks;
import fox.spiteful.avaritia.crafting.Grinder;
import fox.spiteful.avaritia.gui.GooeyHandler;
import fox.spiteful.avaritia.items.LudicrousItems;

@Mod(modid = "Avaritia", name = "Avaritia", dependencies = "after:Thaumcraft;after:AWWayofTime;after:Botania")
public class Avaritia {
    @Instance
    public static Avaritia instance;

    @EventHandler
    public void earlyGame(FMLPreInitializationEvent event){
        instance = this;
        LudicrousItems.grind();
        LudicrousBlocks.voxelize();
    }

    @EventHandler
    public void midGame(FMLInitializationEvent event){
        Grinder.artsAndCrafts();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GooeyHandler());
    }

    @EventHandler
    public void endGame(FMLPostInitializationEvent event){
    }
}