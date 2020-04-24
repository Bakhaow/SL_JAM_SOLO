package fr.bakhaow.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGreenhouse extends Container {
	 
	private TileEntityGreenhouse tile;
	
    public ContainerGreenhouse(IInventory inv, TileEntityGreenhouse t) {
        this.tile = t;
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer p) {
        return this.tile.isUseableByPlayer(p);
	}
}