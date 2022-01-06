package me.cyadd.axiom.client.module.movement;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

import org.lwjgl.input.Keyboard;

public class AxiomModuleSneak extends AxiomModule {

	public AxiomModuleSneak() {
		super("Sneak", 0xA9F5D0, EnumModuleType.MOVEMENT, Keyboard.KEY_Z);
		this.setDescription("Sets the player sneaking.");
	}

	@Override
	protected void onEnable() {
		this.mc.gameSettings.keyBindSneak.pressed = true;
	}

	@Override
	public void onRunnableTick() {
		this.mc.gameSettings.keyBindSneak.pressed = true;
	}

	@Override
	protected void onDisable() {
		this.mc.gameSettings.keyBindSneak.pressed = false;
	}

}
