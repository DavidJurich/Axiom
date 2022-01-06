package me.cyadd.axiom.client.module.player;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.Packet10Flying;

import org.lwjgl.input.Keyboard;


public class AxiomModuleNoFall extends AxiomModule {

	public AxiomModuleNoFall() {
		super("NoFall", 0xDBA901, EnumModuleType.PLAYER, Keyboard.KEY_P);
		this.setDescription("Disables fall damage to the player.");
	}

	@Override
	protected void onEnable() {
	}

	@Override
	public void onRunnableTick() {
		if (!this.mc.thePlayer.onGround) {
			this.mc.getNetHandler().addToSendQueue(new Packet10Flying(true));
		}
	}

	@Override
	protected void onDisable() {
	}

}
