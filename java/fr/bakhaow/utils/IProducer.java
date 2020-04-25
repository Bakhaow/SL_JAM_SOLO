package fr.bakhaow.utils;

import fr.bakhaow.block.tile.TileEntityAntenne;
import fr.bakhaow.block.tile.TileEntityBurner;
import fr.bakhaow.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IProducer {
	default boolean hasStorage(World w, int x, int y, int z) {
		for(int i = -16; i < 16; i++) {
			for(int j = -16; j < 16; j++) {
				for(int k = 0; k < 255; k++) {
					Block b = w.getBlock(x + i, y + k, z + j);
					if(b.equals(ModBlocks.antenne)) {
						TileEntityAntenne t = (TileEntityAntenne) w.getTileEntity(x + i, y + k, z + j);
						if(!(i > t.radius|| j > t.radius)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	default double getDebit(World w, int x, int y, int z) {
		for(int i = -16; i < 16; i++) {
			for(int j = -16; j < 16; j++) {
				for(int k = 0; k < 255; k++) {
					Block b = w.getBlock(x + i, y + k, z + j);
					if(b.equals(ModBlocks.antenne)) {
						TileEntityAntenne t = (TileEntityAntenne) w.getTileEntity(x + i, y + k, z + j);
						if(!(i > t.radius|| j > t.radius)) {
							if(i + j >= t.radius) {
								return 0.5;
							} else if(i + j <= 1) {
								return 1;
							} else {
								return ((i + j) / t.radius / 2) + 0.5;
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	default void sendEnergy(World w, int x, int y, int z, int e, double d) {
		for(int i = -16; i < 16; i++) {
			for(int j = -16; j < 16; j++) {
				for(int k = 0; k < 255; k++) {
					Block b = w.getBlock(x + i, y + k, z + j);
					if(b.equals(ModBlocks.antenne)) {
						TileEntityAntenne t = (TileEntityAntenne) w.getTileEntity(x + i, y + k, z + j);
						if(!(i > t.radius|| j > t.radius)) {
							TileEntity producer = w.getTileEntity(x, y, z);
							if(producer instanceof TileEntityBurner) {
								TileEntityBurner p = (TileEntityBurner) w.getTileEntity(x, y, z);
								p.reduceEnergy(e);
								t.energy += e * d;
							}
						}
					}
				}
			}
		}
	}
}
