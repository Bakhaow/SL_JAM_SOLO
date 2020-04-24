package fr.bakhaow.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static Block antenne, burner, greenhouse;
	
	public static void register() {
		antenne = new fr.bakhaow.block.BlockAntenne(Material.iron);
		burner = new fr.bakhaow.block.BlockBurner(Material.iron);
		greenhouse = new fr.bakhaow.block.BlockGreenhouse(Material.wood);
		
    	GameRegistry.registerBlock(antenne, "antenne");
    	GameRegistry.registerBlock(burner, "burner");
    	GameRegistry.registerBlock(greenhouse, "greenhouse");
	}

}
