package fr.bakhaow;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.bakhaow.client.event.EventManagerClient;
import fr.bakhaow.client.gui.GuiHandler;
import fr.bakhaow.init.ModBlocks;
import fr.bakhaow.init.Tiles;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Main.MOD_ID,name = Main.MOD_NAME,version = Main.MOD_VERSION)
public class Main {
    public static final String MOD_ID = "vindustry";
    public static final String MOD_NAME = "Bakhaow & Alwyn Industry's Mod";
    public static final String MOD_VERSION = "0.1";
    
    @Mod.Instance(MOD_ID)
    public static Main instance;
    public static SimpleNetworkWrapper networkWrapper;

    public static CreativeTabs tabIndustry = new CreativeTabs("tabIndustry") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
        	return Items.arrow;
        }
    };
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(this.MOD_ID);
    	networkWrapper.registerMessage(PacketGreenhouse.PacketGreenhouseHandler.class, PacketGreenhouse.class, 0, Side.SERVER);
    	ModBlocks.register();
    	Tiles.register();
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        if(e.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new EventManagerClient());
            FMLCommonHandler.instance().bus().register(new EventManagerClient());
        }
    }
}