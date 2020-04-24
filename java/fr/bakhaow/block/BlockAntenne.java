package fr.bakhaow.block;

import java.util.Random;

import fr.bakhaow.Main;
import fr.bakhaow.block.tile.TileEntityAntenne;
import fr.bakhaow.init.Guis;
import fr.bakhaow.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockAntenne extends BlockContainer{
	
	public boolean state = false;
	public int x;
	public int y;
	public int z;

	public BlockAntenne(Material m) {
		super(m);
		this.setCreativeTab(Main.tabIndustry);
		this.setHardness(2.0F);
		this.setBlockName("antenne");
		this.setBlockTextureName("antenne");
		this.setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 3);
	}
	
	public Item getItemDropped(int i, Random r, int j) {
		return Item.getItemFromBlock(ModBlocks.antenne);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEntityAntenne(x, y, z);
	}
	
    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int par6, float par7, float par8, float par9) {
		if(!(world.isRemote)) {
			p.openGui(Main.instance, Guis.GUI_ANTENNE, world, x, y, z);
			return true;
		}
		return false;
	}
    
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        TileEntity tileentity = world.getTileEntity(x, y, z);
 
                if (tileentity instanceof IInventory)
                {
                    IInventory inv = (IInventory)tileentity;
                    for (int i1 = 0; i1 < inv.getSizeInventory(); ++i1)
                    {
                        ItemStack itemstack = inv.getStackInSlot(i1);
 
                        if (itemstack != null)
                        {
                            float f = world.rand.nextFloat() * 0.8F + 0.1F;
                            float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                            EntityItem entityitem;
 
                            for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                            {
                                int j1 = world.rand.nextInt(21) + 10;
 
                                if (j1 > itemstack.stackSize)
                                {
                                    j1 = itemstack.stackSize;
                                }
 
                                itemstack.stackSize -= j1;
                                entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                                float f3 = 0.05F;
                                entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                                entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                                entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
 
                                if (itemstack.hasTagCompound())
                                {
                                    entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                                }
                            }
                        }
                    }
 
                world.func_147453_f(x, y, z, block);
            }
 
        super.breakBlock(world, x, y, z, block, metadata);
    }
    
    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase e, ItemStack s) {
    	if(!w.isRemote) {
    		if(e instanceof EntityPlayer) {
    			EntityPlayer p = (EntityPlayer) e;
    			p.addChatMessage(new ChatComponentText("Antenne mise en place"));
    		}
    	}
		this.x = x;
		this.y = y;
		this.z = z;
    }


}
