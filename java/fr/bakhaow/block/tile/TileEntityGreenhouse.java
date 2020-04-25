package fr.bakhaow.block.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGreenhouse extends TileEntity {
    
	private int updateCounter = 0;
	public int wheat, potato, carrot, netherwart, xs, zs;
	public boolean autoReplant;

    @Override
    public void updateEntity() {
    	if(!worldObj.isRemote && autoReplant) {
        	if(updateCounter == 20 * 3) { // 3 * 1 Sec
        		ticked();
        		updateCounter = 0;
        	} else {
        		++updateCounter;
        	}
    	}
    }
    
    public void ticked() {
    	int x = this.xCoord + 1;
		int ys = (wheat + carrot + potato + netherwart) * 3;
		for(int i = 0; i < xs; i++) {
			for(int j = 0; j < zs; j++) {
				for(int k = 0; k < ys; k++) {
					Block b = this.getWorldObj().getBlock(x + i, this.yCoord + k, this.zCoord + j);
					if(b.equals(Blocks.wheat) && this.getWorldObj().getBlockMetadata(x + i, this.yCoord + k, this.zCoord + j) == 7) {
						this.worldObj.setBlock(x + i, this.yCoord + k, this.zCoord + j, Blocks.wheat);
					} else if(b.equals(Blocks.carrots) && this.getWorldObj().getBlockMetadata(x + i, this.yCoord + k, this.zCoord + j) == 7) {
						this.worldObj.setBlock(x + i, this.yCoord + k, this.zCoord + j, Blocks.carrots);
					} else if(b.equals(Blocks.potatoes) && this.getWorldObj().getBlockMetadata(x + i, this.yCoord + k, this.zCoord + j) == 7) {
						this.worldObj.setBlock(x + i, this.yCoord + k, this.zCoord + j, Blocks.potatoes);
					} else if(b.equals(Blocks.nether_wart) && this.getWorldObj().getBlockMetadata(x + i, this.yCoord + k, this.zCoord + j) == 7) {
						this.worldObj.setBlock(x + i, this.yCoord + k, this.zCoord + j, Blocks.nether_wart);
					}
				}
			}
		}
    }
    
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.wheat = data.getShort("wheat");
        this.potato = data.getShort("potato");
        this.carrot = data.getShort("carrot");
        this.netherwart = data.getShort("netherwart");
        this.xs = data.getShort("xs");
        this.zs = data.getShort("zs");
        this.autoReplant = data.getBoolean("autoReplant");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setShort("wheat", (short)this.wheat);
        data.setShort("potato", (short)this.potato);
        data.setShort("carrot", (short)this.carrot);
        data.setShort("netherwart", (short)this.netherwart);
        data.setShort("xs", (short)this.xs);
        data.setShort("zs", (short)this.zs);
        data.setBoolean("autoReplant", this.autoReplant);
    }

}