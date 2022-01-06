package me.cyadd.axiom.client.module.combat;

import org.lwjgl.input.Keyboard;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

public class AxiomModuleAntiVelocity extends AxiomModule {

	public AxiomModuleAntiVelocity() {
		super("AntiVelocity", 0xffb200ff, EnumModuleType.COMBAT, Keyboard.KEY_O);
		this.setDescription("Stops all velocity effects to the player.");
	}
	
}
