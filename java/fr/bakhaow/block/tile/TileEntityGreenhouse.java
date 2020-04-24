package fr.bakhaow.block.tile;

import fr.bakhaow.utils.IMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGreenhouse extends TileEntity implements IInventory, IMachine{
    
	private ItemStack[] inventory;
	private int updateCounter = 0;
	private int stages;
	private int wheat;
	private int potato;
	private int	carrot;
	private int netherwart;
	
    public TileEntityGreenhouse(int x, int y, int z){
    	this.inventory = new ItemStack[4];
    }
    
    @Override
    public void updateEntity() {
    	if(!worldObj.isRemote) {
        	if(updateCounter == 20) { // 1 SEC
        		updateCounter = 0;
        		ticked();
        	} else {
        		++updateCounter;
        	}
    	}
    }
    
    public void ticked() {
    	
    }
    
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        NBTTagList nbttaglist = data.getTagList("ItemStacks", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j >= 0 && j < this.inventory.length) {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
 
        this.stages = data.getShort("stages");
        this.wheat = data.getShort("wheat");
        this.potato = data.getShort("potato");
        this.carrot = data.getShort("carrot");
        this.netherwart = data.getShort("netherwart");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
 
        data.setTag("ItemStacks", nbttaglist);
        data.setShort("stages",(short)this.stages);
        data.setShort("wheat", (short)this.wheat);
        data.setShort("potato", (short)this.potato);
        data.setShort("carrot", (short)this.carrot);
        data.setShort("netherwart", (short)this.netherwart);
    }

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}
    @Override
    public ItemStack decrStackSize(int i, int count) {
            if (this.inventory[i] != null) {
                ItemStack itemstack;
                if (this.inventory[i].stackSize <= count) {
                    itemstack = this.inventory[i];
                    this.inventory[i] = null;
                    this.markDirty();
                    return itemstack;
                } else {
                    itemstack = this.inventory[i].splitStack(count);
                    if (this.inventory[i].stackSize == 0) {
                        this.inventory[i] = null;
                    }
                    this.markDirty();
                    return itemstack;
                }
            } else {
                return null;
            }
    }
 
    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.inventory[i] != null) {
            ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }
 
    @Override
    public void setInventorySlotContents(int i, ItemStack s) {
        this.inventory[i] = s;
        if (s != null && s.stackSize > this.getInventoryStackLimit()) {
            s.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
 
    @Override
    public String getInventoryName() {
        return "tile.antenne";
    }
 
    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
 
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
 
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }
 
    @Override
    public void openInventory() {
 
    }
 
    @Override
    public void closeInventory() {
 
    }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack s) {
		return false;
	}
}