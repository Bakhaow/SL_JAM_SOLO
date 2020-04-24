package fr.bakhaow.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.bakhaow.block.tile.ContainerAntenne;
import fr.bakhaow.block.tile.TileEntityAntenne;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiAntenne extends GuiContainer {
    private static final ResourceLocation guiTexture = new ResourceLocation("textures/gui/container/hopper.png");
    public TileEntityAntenne tile;

    public GuiAntenne(InventoryPlayer inv, TileEntityAntenne t) {
        super(new ContainerAntenne(inv, t));
        this.tile = t;
        this.ySize = 133;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = EnumChatFormatting.DARK_GRAY + "Antenne";
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString("Inventaire - " + tile.radius + " | " + tile.energy, 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}