package me.cyadd.axiom.client.module.movement;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

import org.lwjgl.input.Keyboard;

public class AxiomModuleSprint extends AxiomModule {

	public AxiomModuleSprint() {
		super("Sprint", 0xffcc33, EnumModuleType.MOVEMENT, Keyboard.KEY_NONE);
		this.setDescription("Sets the player to sprint if moving forward.");
	}

	@Override
	protected void onEnable() {
		if (AxiomInput.canSprint()) {
			this.mc.thePlayer.setSprinting(true);
		}
	}

	@Override
	public void onRunnableTick() {
		if (AxiomInput.canSprint()) {
			this.mc.thePlayer.setSprinting(true);
		}
	}

	@Override
	protected void onDisable() {
		this.mc.thePlayer.setSprinting(false);
	}

}
