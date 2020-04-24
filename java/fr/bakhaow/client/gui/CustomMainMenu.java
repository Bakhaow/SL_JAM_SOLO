package fr.bakhaow.client.gui;

import org.lwjgl.opengl.GL11;

import fr.bakhaow.Main;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class CustomMainMenu extends GuiScreen implements GuiYesNoCallback {

    private static final ResourceLocation Background = new ResourceLocation(Main.MOD_ID + ":textures/gui/bg.jpg");
    
    public CustomMainMenu(){}

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

	@SuppressWarnings("unchecked")
	@Override
    public void initGui() {
        int var3 = this.height / 3 ;
        
        this.buttonList.add(new GuiButton(0, this.width / 7 - 5, var3 + 40 , 100, 20, "Solo"));
        this.buttonList.add(new GuiButton(1, this.width / 7 - 5, var3 + 70 , 100, 20, "Options"));
        this.buttonList.add(new GuiButton(2, this.width / 7 - 5, var3 + 100 , 100, 20, "Quitter"));
    }

    @Override
    protected void actionPerformed(GuiButton b) {
        if(b.id == 0) {
        	this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }else if(b.id == 1) {
        	this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        } else {
        	this.mc.shutdown();
        }
    }

    @Override
    public void drawScreen(int x, int y, float t) {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        mc.getTextureManager().bindTexture(Background);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTextureWithOptionalSize(-1, -1, x, y, width + 5, height + 5, width + 5, height + 5);
        String s = "Bakhaow & Alwyn SL_JAM";
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + s, this.width - this.fontRendererObj.getStringWidth(s) - 2, this.height - 10, -1);
        super.drawScreen(x, y, t);
    }

    public void drawTextureWithOptionalSize(int x, int y, int u, int v, int w, int h, int a, int b) {
        float sX = (float)1/a;
        float sY = (float)1/b;
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV((double)(x + 0), (double)(y + h), (double)this.zLevel, (double)((float)(u + 0) * sX), (double)((float)(v + h) * sY));
        t.addVertexWithUV((double)(x + w), (double)(y + h), (double)this.zLevel, (double)((float)(u + w) * sX), (double)((float)(v + h) * sY));
        t.addVertexWithUV((double)(x + w), (double)(y + 0), (double)this.zLevel, (double)((float)(u + w) * sX), (double)((float)(v + 0) * sY));
        t.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(u + 0) * sX), (double)((float)(v + 0) * sY));
        t.draw();
    }
}