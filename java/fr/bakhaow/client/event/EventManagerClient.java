package fr.bakhaow.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.bakhaow.client.gui.CustomMainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;

public class EventManagerClient {
	
    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent e) {
    	if(e.gui != null && e.gui.getClass().equals(GuiMainMenu.class)) {
    		e.gui = new CustomMainMenu();
    	}
    }
}
