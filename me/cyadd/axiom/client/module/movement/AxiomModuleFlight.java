package me.cyadd.axiom.client.module.movement;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

import org.lwjgl.input.Keyboard;


public class AxiomModuleFlight extends AxiomModule {

	public AxiomModuleFlight() {
		super("Flight", 0x01DFD7, EnumModuleType.MOVEMENT, Keyboard.KEY_R);
		this.setDescription("Allows the player to fly.");
	}

	@Override
	protected void onEnable() {
	}

	@Override
	public void onRunnableTick() {
		this.mc.thePlayer.capabilities.isFlying = true;
	}

	@Override
	protected void onDisable() {
		this.mc.thePlayer.capabilities.isFlying = false;
	}

}
