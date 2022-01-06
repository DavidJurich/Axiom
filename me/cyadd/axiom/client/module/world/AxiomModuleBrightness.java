package me.cyadd.axiom.client.module.world;

import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

import org.lwjgl.input.Keyboard;

public class AxiomModuleBrightness extends AxiomModule {

	private int brightness = 100;

	public AxiomModuleBrightness() {
		super("Bright", 0xFAFAFA, EnumModuleType.WORLD, Keyboard.KEY_B);
		this.setDescription("Brighten up the world.");
		this.setCustomSyntax(".brightness <integer>");
	}

	@Override
	public void onRunnableTick() {
		if (this.mc.gameSettings.gammaSetting <= this.getBrightness()) {
			this.mc.gameSettings.gammaSetting += 1.5;
		}
	}

	@Override
	public void onDisable() {
		this.mc.gameSettings.gammaSetting = 0;
	}

	@Override
	public void runCommand(final String s) {
		if (s.startsWith(".brightness")) {
			final String args[] = s.split(" ");
			try {
				final int i = Integer.parseInt(args[1]);
				this.setBrightness(i);
				AxiomInput.addChat("Brightness set to <" + i + ">.");
			} catch (final Exception e) {
				AxiomInput.addChat("Invaid Syntax: " + this.getCustomSyntax());
			}
		}
	}

	/**
	 * @return the brightness
	 */
	public int getBrightness() {
		return this.brightness;
	}

	/**
	 * @param brightness
	 *            the brightness to set
	 */
	public void setBrightness(final int brightness) {
		this.brightness = brightness;
	}

}
