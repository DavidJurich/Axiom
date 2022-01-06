package me.cyadd.axiom.client.module.movement;

import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

import org.lwjgl.input.Keyboard;

/** @see {@link net.minecraft.src.EntityPlayerSP#moveEntity()} **/
public class AxiomModuleSpeed extends AxiomModule {

	private static float speed = 1.24f;
	private float x = 1.05F;
	private float y = 1.05F;

	public AxiomModuleSpeed() {
		super("Speed", 0xffcc33, EnumModuleType.MOVEMENT, Keyboard.KEY_V);
		this.setDescription("Run faster than usual.");
		this.setCustomSyntax(".speed <float>");
	}

	@Override
	public void onEnable() {
		if (AxiomInput.canSprint()) {
			this.mc.thePlayer.setSprinting(true);
			this.x = this.mc.thePlayer.landMovementFactor;
			this.y = this.mc.thePlayer.jumpMovementFactor;
		}
	}

	@Override
	public void onRunnableTick() {
		if (AxiomInput.canSprint()) {
			this.mc.thePlayer.setSprinting(true);
			this.mc.thePlayer.landMovementFactor *= 1.05;
			this.mc.thePlayer.jumpMovementFactor *= 1.05;
		}
	}

	@Override
	public void onDisable() {
		this.mc.thePlayer.landMovementFactor = this.x;
		this.mc.thePlayer.jumpMovementFactor = this.y;
		this.mc.thePlayer.setSprinting(false);
	}

	@Override
	public void runCommand(final String s) {
		if (s.startsWith(".speed")) {
			final String args[] = s.split(" ");
			try {
				final Float f = Float.parseFloat(args[1]);
				this.setSpeed(f);
				AxiomInput.addChat("Speed hack speed set to <" + f + ">.");
			} catch (final Exception e) {
				AxiomInput.addChat("Invaid Syntax: " + this.getCustomSyntax());
			}
		}
	}

	/**
	 * @return the speed
	 */
	public static float getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(final float speed) {
		this.speed = speed;
	}

}
