package me.cyadd.axiom.client.module.client;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.gui.screens.AxiomChat;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.GuiNewChat;

public class AxiomModuleChat extends AxiomModule {

	public AxiomModuleChat() {
		super("Chat", EnumModuleType.CLIENT);
		this.setDescription("Displays a nice TTF Chat.");
	}

	@Override
	protected void onEnable() {
		this.mc.ingameGUI.persistantChatGUI = new AxiomChat(this.mc);
	}

	@Override
	protected void onDisable() {
		this.mc.ingameGUI.persistantChatGUI = new GuiNewChat(this.mc);
	}

}
