package fr.bakhaow.init;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.bakhaow.Main;

public class Tiles {

	public static void register() {
		GameRegistry.registerTileEntity(fr.bakhaow.block.tile.TileEntityAntenne.class, Main.MOD_ID + ":Antenne");
		GameRegistry.registerTileEntity(fr.bakhaow.block.tile.TileEntityBurner.class, Main.MOD_ID + ":Burner");
		GameRegistry.registerTileEntity(fr.bakhaow.block.tile.TileEntityGreenhouse.class, Main.MOD_ID + ":Greenhouse");
	}
}
