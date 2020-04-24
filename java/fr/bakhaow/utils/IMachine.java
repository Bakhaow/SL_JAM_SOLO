package fr.bakhaow.utils;

import fr.bakhaow.block.tile.TileEntityAntenne;
import fr.bakhaow.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface IMachine {
	/*default boolean hasPower(World w, int x, int y, int z) {
		for(int i = -16; i < 16; i++) {
			for(int j = -16; j < 16; j++) {
				for(int k = 0; k < 255; k++) {
					Block b = w.getBlock(x + i, y + k, z + j);
					if(b.equals(ModBlocks.antenne)) {
						TileEntityAntenne t = (TileEntityAntenne) w.getTileEntity(x + i, y + k, z + j);
						if(!(i > t.radius|| j > t.radius)) {
							System.out.println("powered by " + t);
							return t.energy > 0;
						}
					}
				}
			}
		}
		return false;
	}
	
	default boolean canAfford(World w, int x, int y, int z, int p) {
		for(int i = -16; i < 16; i++) {
			for(int j = -16; j < 16; j++) {
				for(int k = 0; k < 255; k++) {
					Block b = w.getBlock(x + i, y + k, z + j);
					if(b.equals(ModBlocks.antenne)) {
						TileEntityAntenne t = (TileEntityAntenne) w.getTileEntity(x + i, y + k, z + j);
						if(!(i > t.radius|| j > t.radius)) {
							if(t.energy >= p) {
								return true;
							}
						}
					}
				}
			}
		}
		System.out.println("cant afford");
		return false;
	}
	
	default void usePower(World w, int x, int y, int z, int p) {
		for(int i = -16; i < 16; i++) {
			for(int j = -16; j < 16; j++) {
				for(int k = 0; k < 255; k++) {
					Block b = w.getBlock(x + i, y + k, z + j);
					if(b.equals(ModBlocks.antenne)) {
						TileEntityAntenne t = (TileEntityAntenne) w.getTileEntity(x + i, y + k, z + j);
						if(!(i > t.radius|| j > t.radius)) {
							t.energy -= p;
						}
					}
				}
			}
		}
	}*/
}
