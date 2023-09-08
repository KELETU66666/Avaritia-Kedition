package morph.avaritia.proxy;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.ItemNBTUtils;
import morph.avaritia.api.registration.IModelRegister;
import morph.avaritia.client.AvaritiaClientEventHandler;
import morph.avaritia.client.render.entity.RenderGapingVoid;
import morph.avaritia.client.render.entity.RenderHeavenArrow;
import morph.avaritia.client.render.entity.WrappedEntityItemRenderer;
import morph.avaritia.client.render.shader.ShaderHelper;
import morph.avaritia.compat.CompatClient;
import morph.avaritia.entity.EntityEndestPearl;
import morph.avaritia.entity.EntityGapingVoid;
import morph.avaritia.entity.EntityHeavenArrow;
import morph.avaritia.entity.EntityHeavenSubArrow;
import morph.avaritia.init.AvaritiaTextures;
import morph.avaritia.init.LudicrousItems;
import morph.avaritia.item.ItemMatterCluster;
import morph.avaritia.network.ClientPacketHandler;
import morph.avaritia.network.NetworkDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.HashSet;
import java.util.Set;

public class ProxyClient extends Proxy {

    private Set<IModelRegister> modelRegisters = new HashSet<>();

    //@formatter:off
    public static final int[] SINGULARITY_COLOURS_FOREGROUND = new int[] {
            0xBFBFBF, 0xE8EF23, 0x5a82e2, 0xDF0000, 0xeeebe6, 0xE47200,
            0xA5C7DE, 0x444072, 0xF9F9F9, 0xDEE187, 0x8890AD
    };

    public static final int[] SINGULARITY_COLOURS_BACKGROUND = new int[] {
            0x7F7F7F, 0xdba213, 0x224baf, 0x900000, 0x94867d, 0x89511A,
            0x9BA9B2, 0x3E3D4E, 0xD5D5D5, 0xC4C698, 0x666B7F
    };

    public static final int[][] SINGULARITY_COLOURS = new int[][] {
            SINGULARITY_COLOURS_FOREGROUND,
            SINGULARITY_COLOURS_BACKGROUND
    };
    //@formatter:on

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        super.preInit(event);
        TextureUtils.addIconRegister(new AvaritiaTextures());
        MinecraftForge.EVENT_BUS.register(new AvaritiaClientEventHandler());

        for (IModelRegister register : modelRegisters) {
            register.registerModels();
        }
        ShaderHelper.initShaders();
        ResourceLocation tools = new ResourceLocation("avaritia:tools");
        ResourceLocation resource = new ResourceLocation("avaritia:resource");

        {
            ModelResourceLocation pickaxe = new ModelResourceLocation(tools, "infinity_pickaxe=pickaxe");
            ModelResourceLocation hammer = new ModelResourceLocation(tools, "infinity_pickaxe=hammer");
            ModelLoader.registerItemVariants(LudicrousItems.infinity_pickaxe, pickaxe, hammer);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.infinity_pickaxe, stack -> {
                if (stack.hasTagCompound()) {
                    if (ItemNBTUtils.getBoolean(stack, "hammer")) {
                        return hammer;
                    }
                }
                return pickaxe;
            });
        }

        {
            ModelResourceLocation shovel = new ModelResourceLocation(tools, "infinity_shovel=shovel");
            ModelResourceLocation destroyer = new ModelResourceLocation(tools, "infinity_shovel=destroyer");
            ModelLoader.registerItemVariants(LudicrousItems.infinity_shovel, shovel, destroyer);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.infinity_shovel, stack -> {
                if (stack.hasTagCompound()) {
                    if (ItemNBTUtils.getBoolean(stack, "destroyer")) {
                        return destroyer;
                    }
                }
                return shovel;
            });
        }

        {
            ModelResourceLocation axe = new ModelResourceLocation(tools, "type=infinity_axe");
            ModelLoader.registerItemVariants(LudicrousItems.infinity_axe, axe);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.infinity_axe, (ItemStack stack) -> axe);
        }

        {
            ModelResourceLocation hoe = new ModelResourceLocation(tools, "type=infinity_hoe");
            ModelLoader.registerItemVariants(LudicrousItems.infinity_axe, hoe);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.infinity_hoe, (ItemStack stack) -> hoe);
        }

        {
            ModelResourceLocation helmet = new ModelResourceLocation(tools, "armor=helmet");
            ModelLoader.registerItemVariants(LudicrousItems.infinity_helmet, helmet);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.infinity_helmet, (ItemStack stack) -> helmet);
        }

        {
            ModelResourceLocation chestplate = new ModelResourceLocation(tools, "armor=chestplate");
            ModelLoader.registerItemVariants(LudicrousItems.infinity_chestplate, chestplate);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.infinity_chestplate, (ItemStack stack) -> chestplate);
        }

        {
            ModelResourceLocation legs = new ModelResourceLocation(tools, "armor=legs");
            ModelLoader.registerItemVariants(LudicrousItems.infinity_pants, legs);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.infinity_pants, (ItemStack stack) -> legs);
        }

        {
            ModelResourceLocation boots = new ModelResourceLocation(tools, "armor=boots");
            ModelLoader.registerItemVariants(LudicrousItems.infinity_boots, boots);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.infinity_boots, (ItemStack stack) -> boots);
        }

        {
            ModelResourceLocation sword = new ModelResourceLocation(tools, "type=skull_sword");
            ModelLoader.registerItemVariants(LudicrousItems.skull_sword, sword);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.skull_sword, (ItemStack stack) -> sword);
        }

        {
            ModelResourceLocation stew = new ModelResourceLocation(resource, "type=ultimate_stew");
            ModelLoader.registerItemVariants(LudicrousItems.ultimate_stew, stew);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.ultimate_stew, (ItemStack stack) -> stew);
        }

        {
            ModelResourceLocation meatballs = new ModelResourceLocation(resource, "type=cosmic_meatballs");
            ModelLoader.registerItemVariants(LudicrousItems.cosmic_meatballs, meatballs);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.cosmic_meatballs, (ItemStack stack) -> meatballs);
        }

        {
            ModelResourceLocation empty = new ModelResourceLocation(resource, "matter_cluster=empty");
            ModelResourceLocation full = new ModelResourceLocation(resource, "matter_cluster=full");
            ModelLoader.registerItemVariants(LudicrousItems.matter_cluster, empty, full);
            ModelLoader.setCustomMeshDefinition(LudicrousItems.matter_cluster, (ItemStack stack) -> {
                if (ItemMatterCluster.getClusterSize(stack) == ItemMatterCluster.CAPACITY) {
                    return full;
                }
                return empty;
            });
        }


        CompatClient.earlyComprettify();
        registerRenderers();
        PacketCustom.assignHandler(NetworkDispatcher.NET_CHANNEL, new ClientPacketHandler());
    }

    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
        CompatClient.comprettify();
    }

    @SuppressWarnings ("unchecked")
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        CompatClient.lateComprettify();
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        itemColors.registerItemColorHandler((stack, tintIndex) -> SINGULARITY_COLOURS[tintIndex ^ 1][stack.getItemDamage()], LudicrousItems.singularity);

        RenderManager manager = Minecraft.getMinecraft().getRenderManager();

        Render<EntityItem> render = (Render<EntityItem>) manager.entityRenderMap.get(EntityItem.class);
        if (render == null) {
            throw new RuntimeException("EntityItem does not have a Render bound... This is likely a bug..");
        }
        manager.entityRenderMap.put(EntityItem.class, new WrappedEntityItemRenderer(manager, render));
    }

    @Override
    public void addModelRegister(IModelRegister register) {
        modelRegisters.add(register);
    }

    private void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityEndestPearl.class, manager -> new RenderSnowball<>(manager, LudicrousItems.endest_pearl, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityGapingVoid.class, RenderGapingVoid::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHeavenArrow.class, RenderHeavenArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHeavenSubArrow.class, RenderHeavenArrow::new);
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().world;
    }
}
