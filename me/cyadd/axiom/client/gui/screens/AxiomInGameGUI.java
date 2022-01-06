package me.cyadd.axiom.client.gui.screens;

import java.text.DecimalFormat;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.AxiomWrapper;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.ScaledResolution;

/**
 * This is a class which holds everything that should be rendered on screen.
 * 
 * @author Cyadd + Wulf
 * @since 31/05/2013
 */
public class AxiomInGameGUI extends GuiIngame {

	public AxiomInGameGUI(final Minecraft par1Minecraft) {
		super(par1Minecraft);
	}

	/**
	 * Render the in-game overlay with quick icon bar. Also contains the hook
	 * for rendering things in-game.
	 */
	@Override
	public void renderGameOverlay(final float par1, final boolean par2, final int par3, final int par4) {
		super.renderGameOverlay(par1, par2, par3, par4);
		final FontRenderer font = AxiomWrapper.getMinecraftObj().fontRenderer;
		final Minecraft mc = AxiomWrapper.getMinecraftObj();
		int yPosition = 2;
		if (!mc.gameSettings.showDebugInfo) {
			font.drawStringWithShadow("Axiom Beta " + AxiomInput.getClientVersion() + " (rel-" + AxiomInput.getMCVersion() + ")", 2, 2, 0xffffff);
			Axiom.getAxiom().getHookManager().renderOverlay();
			this.renderCoordinates();
			for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
				if (m.isEnabled() && m.isOnArray()) {
					final String name = m.getName();
					final int colour = m.getColour();
					final int xPosition = AxiomWrapper.getResolution().getScaledWidth() - (font.getStringWidth(name) + 2);
					font.drawStringWithShadow(name, xPosition, yPosition, colour);
					yPosition += 10;
				}
			}
		}
	}

	/** Displays the players current co-ordinates with a formatted decimal **/
	public void renderCoordinates() {
		final EntityClientPlayerMP var0 = AxiomWrapper.getMinecraftObj().thePlayer;
		final FontRenderer var1 = AxiomWrapper.getMinecraftObj().fontRenderer;
		final ScaledResolution var2 = AxiomWrapper.getResolution();
		final DecimalFormat var3 = new DecimalFormat("##.#");
		var3.setDecimalSeparatorAlwaysShown(true);
		var3.setMinimumFractionDigits(1);
		var3.setMaximumFractionDigits(1);
		final String var4 = var3.format(var0.posX);
		final String var5 = var3.format(var0.posY);
		final String var6 = var3.format(var0.posZ);
		final String var7 = "X: " + var4;
		final String var8 = "Y: " + var5;
		final String var9 = "Z: " + var6;
		var1.drawStringWithShadow(var7, var2.getScaledWidth() - var1.getStringWidth(var7) - 2, var2.getScaledHeight() - 30, 16777215);
		var1.drawStringWithShadow(var8, var2.getScaledWidth() - var1.getStringWidth(var8) - 2, var2.getScaledHeight() - 20, 16777215);
		var1.drawStringWithShadow(var9, var2.getScaledWidth() - var1.getStringWidth(var9) - 2, var2.getScaledHeight() - 10, 16777215);
	}

}
