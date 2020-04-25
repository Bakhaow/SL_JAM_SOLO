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
import scala.Int;

@SideOnly(Side.CLIENT)
public class GuiGreenhouse extends GuiContainer {
	
    private TileEntityGreenhouse tile;
    private GuiTextField xs, zs, s, sc, sp, sn;

    public GuiGreenhouse(InventoryPlayer inv, TileEntityGreenhouse t) {
        super(new ContainerGreenhouse(inv, t));
        this.tile = t;
        this.ySize = 133;
    }
    
	@Override
	public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50, this.height / 2 - 10, 100, 20, "Construire"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50, this.height / 2 + 10, 100, 20, "Retour au jeu"));
        this.xs = new GuiTextField(this.fontRendererObj, this.width / 4 - this.width / 8, this.height / 3, this.width / 4, 20);
        this.zs = new GuiTextField(this.fontRendererObj, this.width / 4 * 3 - this.width / 8, this.height / 3, this.width / 4, 20);
        this.s = new GuiTextField(this.fontRendererObj, 10, this.height / 3 * 2 + 15, this.width * 8 / 40, 20);
        this.sc = new GuiTextField(this.fontRendererObj, this.width / 4 + 10, this.height / 3 * 2 + 15, this.width * 8 / 40, 20);
        this.sp = new GuiTextField(this.fontRendererObj, this.width / 2 + 10, this.height / 3 * 2 + 15, this.width * 8 / 40, 20);
        this.sn = new GuiTextField(this.fontRendererObj, this.width / 4 * 3 + 10, this.height / 3 * 2 + 15, this.width * 8 / 40, 20);
        this.xs.setFocused(false);
        this.xs.setText("2");
        this.zs.setFocused(false);
        this.zs.setText("2");
        this.s.setFocused(true);
        this.s.setText("2");
        this.sc.setFocused(false);
        this.sc.setText("2");
        this.sp.setFocused(false);
        this.sp.setText("2");
        this.sn.setFocused(false);
        this.sn.setText("2");
    }
    
    @Override
    protected void actionPerformed(GuiButton btn) {
    	if(btn.id == 0) {
			this.xs.setFocused(false);
			this.zs.setFocused(false);
			this.s.setFocused(false);
			this.sc.setFocused(false);
			this.sp.setFocused(false);
			this.sn.setFocused(false);
			int xsInt = Integer.parseInt(this.xs.getText());
			int zsInt = Integer.parseInt(this.zs.getText());
			int sInt = Integer.parseInt(this.s.getText());
			int scInt = Integer.parseInt(this.sc.getText());
			int spInt = Integer.parseInt(this.sp.getText());
			int snInt = Integer.parseInt(this.sn.getText());
			int price = (xsInt + zsInt * (sInt + spInt + snInt)) * 30;
			Main.networkWrapper.sendToServer(new PacketGreenhouse(tile.xCoord, tile.yCoord, tile.zCoord, xsInt, zsInt, price, sInt, scInt, spInt, snInt));
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
    	if (this.xs.isFocused()) {
            this.xs.textboxKeyTyped(c, i);
        } else if (this.zs.isFocused()) {
            this.zs.textboxKeyTyped(c, i);
        } else if (this.s.isFocused()) {
            this.s.textboxKeyTyped(c, i);
        } else if (this.sc.isFocused()) {
            this.sc.textboxKeyTyped(c, i);
        } else if (this.sp.isFocused()) {
            this.sp.textboxKeyTyped(c, i);
        } else if (this.sn.isFocused()) {
            this.sn.textboxKeyTyped(c, i);
        }
    }
    
    protected void mouseClicked(int x, int y, int btn) {
        super.mouseClicked(x, y, btn);
        this.xs.mouseClicked(x, y, btn);
        this.zs.mouseClicked(x, y, btn);
        this.s.mouseClicked(x, y, btn);
        this.sc.mouseClicked(x, y, btn);
        this.sp.mouseClicked(x, y, btn);
        this.sn.mouseClicked(x, y, btn);
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.xs.drawTextBox();
        this.zs.drawTextBox();
        this.s.drawTextBox();
        this.sc.drawTextBox();
        this.sp.drawTextBox();
        this.sn.drawTextBox();
        this.fontRendererObj.drawString(EnumChatFormatting.GOLD + "Greenhouse", this.width / 2 - this.fontRendererObj.getStringWidth("Greenhouse") / 2 , 15, Int.MinValue());
        this.fontRendererObj.drawString(EnumChatFormatting.DARK_GREEN + "Width:", this.width / 4 - this.width / 8, this.height / 3 - 10, Int.MaxValue());
        this.fontRendererObj.drawString(EnumChatFormatting.DARK_GREEN + "Length:", this.width / 4 * 3 - this.width / 8, this.height / 3 - 10, Int.MaxValue());
        this.fontRendererObj.drawString(EnumChatFormatting.BLUE + "Wheat:", 10, this.height / 3 * 2, Int.MinValue());
        this.fontRendererObj.drawString(EnumChatFormatting.BLUE + "Carrot:", this.width / 4 + 10, this.height / 3 * 2, Int.MinValue());
        this.fontRendererObj.drawString(EnumChatFormatting.BLUE + "Potato:", this.width / 2 + 10, this.height / 3 * 2, Int.MinValue());
        this.fontRendererObj.drawString(EnumChatFormatting.BLUE + "Netherwart:", this.width / 4 * 3 + 10, this.height / 3 * 2, Int.MinValue());
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
    	this.drawDefaultBackground();
    }
}