package fr.bakhaow.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.bakhaow.Main;
import fr.bakhaow.PacketGreenhouse;
import fr.bakhaow.block.tile.ContainerGreenhouse;
import fr.bakhaow.block.tile.TileEntityGreenhouse;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

@SideOnly(Side.CLIENT)
public class GuiGreenhouse extends GuiContainer {
	
    private TileEntityGreenhouse tile;
    private GuiTextField s, xs, zs;

    public GuiGreenhouse(InventoryPlayer inv, TileEntityGreenhouse t) {
        super(new ContainerGreenhouse(inv, t));
        this.tile = t;
        this.ySize = 133;
    }
    
	@Override
	public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50, this.height / 4 * 2 - 20, 100, 20, "Construire"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50, this.height / 4 * 3 - 20, 100, 20, "Retour au jeu"));
        this.s = new GuiTextField(this.fontRendererObj, this.width / 4, this.height / 3, 50, 20);
        this.xs = new GuiTextField(this.fontRendererObj, this.width / 4 * 2, this.height / 3, 50, 20);
        this.zs = new GuiTextField(this.fontRendererObj, this.width / 4 * 3, this.height / 3, 50, 20);
        this.s.setFocused(true);
        this.s.setText("1");
        this.xs.setFocused(false);
        this.xs.setText("2");
        this.zs.setFocused(false);
        this.zs.setText("3");
    }
    
    @Override
    protected void actionPerformed(GuiButton btn) {
    	if(btn.id == 0) {
			this.xs.setFocused(false);
			this.zs.setFocused(false);
			this.s.setFocused(false);
			int xsInt = Integer.parseInt(this.xs.getText());
			int zsInt = Integer.parseInt(this.zs.getText());
			int sInt = Integer.parseInt(this.s.getText());
			int price = (xsInt + zsInt * sInt) * 30;
			Main.networkWrapper.sendToServer(new PacketGreenhouse(tile.xCoord, tile.yCoord, tile.zCoord, xsInt, zsInt, sInt, price));
    		this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
    	} else if(btn.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
    	}
    }
    
    protected void keyTyped(char c, int i)
    {
    	if(!Character.isDigit(c)) {
    		c = Character.MIN_VALUE;
    	}
        if (this.s.isFocused()) {
            this.s.textboxKeyTyped(c, i);
        } else if (this.xs.isFocused()) {
            this.xs.textboxKeyTyped(c, i);
        } else if (this.zs.isFocused()) {
            this.zs.textboxKeyTyped(c, i);
        }
    }
    
    protected void mouseClicked(int x, int y, int btn) {
        super.mouseClicked(x, y, btn);
        this.s.mouseClicked(x, y, btn);
        this.xs.mouseClicked(x, y, btn);
        this.zs.mouseClicked(x, y, btn);
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = EnumChatFormatting.DARK_GRAY + "Greenhouse";
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.s.drawTextBox();
        this.xs.drawTextBox();
        this.zs.drawTextBox();
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
    	this.drawDefaultBackground();
    }
}