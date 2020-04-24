package fr.bakhaow.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import fr.bakhaow.block.tile.ContainerAntenne;
import fr.bakhaow.block.tile.ContainerBurner;
import fr.bakhaow.block.tile.ContainerGreenhouse;
import fr.bakhaow.block.tile.TileEntityAntenne;
import fr.bakhaow.block.tile.TileEntityBurner;
import fr.bakhaow.block.tile.TileEntityGreenhouse;
import fr.bakhaow.init.Guis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    if(ID == Guis.GUI_ANTENNE)
	        return new GuiAntenne(player.inventory, (TileEntityAntenne) world.getTileEntity(x, y, z));
	    if(ID == Guis.GUI_BURNER)
	        return new GuiBurner(player.inventory, (TileEntityBurner) world.getTileEntity(x, y, z));
	    if(ID == Guis.GUI_GREENHOUSE)
	        return new GuiGreenhouse(player.inventory, (TileEntityGreenhouse) world.getTileEntity(x, y, z));
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    if(ID == Guis.GUI_ANTENNE)
	        return new ContainerAntenne(player.inventory, (TileEntityAntenne) world.getTileEntity(x, y, z));
	    if(ID == Guis.GUI_BURNER)
	        return new ContainerBurner(player.inventory, (TileEntityBurner) world.getTileEntity(x, y, z));
	    if(ID == Guis.GUI_GREENHOUSE)
	        return new ContainerGreenhouse(player.inventory, (TileEntityGreenhouse) world.getTileEntity(x, y, z));
		return null;
	}
}
