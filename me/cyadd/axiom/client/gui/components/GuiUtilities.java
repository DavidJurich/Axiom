package me.cyadd.axiom.client.gui.components;

import me.cyadd.axiom.client.AxiomWrapper;
import me.cyadd.axiom.client.utilities.CustomFont;
import net.minecraft.src.StringUtils;

import org.lwjgl.opengl.GL11;


/**
 * This is the graphic rendering class. There are a lot of useful methods in
 * here for drawing things.
 * 
 * @author Cyadd + Wulf
 * @since 03/06/2013
 */
public class GuiUtilities {

	public static void enableDefaults() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
	}

	public static void drawRect(final float x, final float y, final float x2, final float y2, final int col1) {
		final float f = (col1 >> 24 & 0xFF) / 255F;
		final float f1 = (col1 >> 16 & 0xFF) / 255F;
		final float f2 = (col1 >> 8 & 0xFF) / 255F;
		final float f3 = (col1 & 0xFF) / 255F;

		GuiUtilities.enableDefaults();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GuiUtilities.disableDefaults();
	}

	public static void drawHorizontalLine(float x, float y, final float x2, final int hex) {
		if (y < x) {
			final float var5 = x;
			x = y;
			y = var5;
		}

		GuiUtilities.drawRect(x, x2, y + 1, x2 + 1, hex);
	}

	public static void drawVerticalLine(final float x, float y, float y2, final int hex) {
		if (y2 < y) {
			final float var5 = y;
			y = y2;
			y2 = var5;
		}

		GuiUtilities.drawRect(x, y + 1, x + 1, y2, hex);
	}

	public static void drawBaseRect(final int i, final int j, final int k, final int l, final int i1, final int j1, final boolean flag) {
		if (flag) {
			GuiUtilities.drawRect(i + 1, j, k, l, i1);
			GuiUtilities.drawHorizontalLine(i + 1, k - 1, j, j1);
			GuiUtilities.drawVerticalLine(i + 1, j, l, j1);
			GuiUtilities.drawVerticalLine(k - 1, j, l, j1);
			GuiUtilities.drawHorizontalLine(i + 1, k - 1, l, j1);
		} else {
			GuiUtilities.drawRect(i + 2, j + 2, k - 1, l - 1, i1);
			GuiUtilities.drawHorizontalLine(i + 2, k - 2, j + 1, j1);
			GuiUtilities.drawVerticalLine(i + 1, j + 1, l - 1, j1);
			GuiUtilities.drawVerticalLine(k - 1, j + 1, l - 1, j1);
			GuiUtilities.drawHorizontalLine(i + 2, k - 2, l - 1, j1);
		}
	}

	public static void drawBorderedRect(final int i, final int j, final int k, final int l, final int i1, final int j1, final boolean flag) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GuiUtilities.drawBaseRect(i * 2, j * 2, k * 2, l * 2, i1, j1, flag);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public static void drawTTFString(final CustomFont f, final String text, final double x, final double y, final int color) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		f.drawGoodString(AxiomWrapper.getMinecraftObj().ingameGUI, text, x, y, color, false);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawTTFStringWithShadow(final CustomFont f, final String text, final double x, final double y, final int color) {
		GuiUtilities.drawTTFString(f, StringUtils.stripControlCodes(text), x + 0.6, y + 0.6, 0x000000);
		GuiUtilities.drawTTFString(f, text, x, y, color);
	}

	public static void disableDefaults() {
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

}
