package me.cyadd.axiom.client.module.player;

import org.lwjgl.input.Keyboard;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

public class AxiomModuleFastPlace extends AxiomModule {

	public AxiomModuleFastPlace() {
	    super("Fastplace", 0xff6677, EnumModuleType.PLAYER, Keyboard.KEY_NONE);
	    this.setDescription("Place blocks faster.");
    }
	
	@Override
	public void onRunnableTick() {
		mc.rightClickDelayTimer = 0;
	}

}
