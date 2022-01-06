package me.cyadd.axiom.client.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;

import org.lwjgl.opengl.GL11;

/**
 * A class to create and draw true type fonts onto the Minecraft game engine.
 * 
 * @author TheObliterator
 */
public class CustomFont {

	private final int texID;
	private final int xPos[];
	private final int yPos[];
	private final int startChar;
	private final FontMetrics metrics;

	public String fontName;
	public int fSize;

	public CustomFont(final Minecraft minecraft, final Object obj, final int i) {
		this(minecraft, obj, i, 31, 127);
	}

	public CustomFont(final Minecraft minecraft, final Object obj, final int i, final int j, final int k) {
		this.startChar = j;
		this.xPos = new int[k - j];
		this.yPos = new int[k - j];
		final BufferedImage bufferedimage = new BufferedImage(256, 256, 2);
		final Graphics g = bufferedimage.getGraphics();
		this.fSize = i;
		try {
			final Graphics2D g1 = (Graphics2D) g;
			g1.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			if (obj instanceof String) {
				final String s = (String) obj;
				this.fontName = s;
				if (s.contains("/")) {
					g.setFont(Font.createFont(0, new File(s)).deriveFont(i));
				} else {
					g.setFont(new Font(s, 0, i));
				}
			} else if (obj instanceof InputStream) {
				g.setFont(Font.createFont(0, (InputStream) obj).deriveFont(i));
			} else if (obj instanceof File) {
				g.setFont(Font.createFont(0, (File) obj).deriveFont(i));
			}
		} catch (final Exception exception) {
			exception.printStackTrace();
		}

		g.setColor(new Color(255, 255, 255, 0));
		g.fillRect(0, 0, 256, 256);
		g.setColor(Color.white);
		this.metrics = g.getFontMetrics();
		int l = 2;
		int i1 = 2;

		for (int j1 = j; j1 < k; j1++) {
			g.drawString((new StringBuilder()).append("").append((char) j1).toString(), l, i1 + g.getFontMetrics().getAscent());
			this.xPos[j1 - j] = l;
			this.yPos[j1 - j] = i1 - this.metrics.getMaxDescent();
			l += this.metrics.stringWidth((new StringBuilder()).append("").append((char) j1).toString()) + 2;

			if (l >= 250 - this.metrics.getMaxAdvance()) {
				l = 2;
				i1 += this.metrics.getMaxAscent() + this.metrics.getMaxDescent() + i / 2;
			}
		}

		this.texID = minecraft.renderEngine.allocateAndSetupTexture(bufferedimage);
	}

	public void drawStringS(final Gui gui, final String s, final int i, final int j) {
		this.drawString(gui, s, i + 2, j + 2, true);
		this.drawString(gui, s, i, j, false);
	}

	public void drawGoodStringWithOutScalingAndShit(final Gui g, final String s, int i, int j, final int colour) {
		final float red = (colour >> 16 & 0xff) / 255F;
		final float green = (colour >> 8 & 0xff) / 255F;
		final float blue = (colour & 0xff) / 255F;
		new Color(red, green, blue, 1F);
		i *= 2;
		j *= 2;
		j -= 5;
		GL11.glPushMatrix();
		GL11.glEnable(3553);
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glColor4f(red, green, blue, 255);
		this.drawString(g, s, i, j, false);
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glPopMatrix();
		GL11.glColor4f(1F, 1F, 1F, 255F);
	}

	public void drawGoodString(final Gui g, final String s, double i, double j, int colour, final boolean flag) {
		if (flag) {
			colour = (colour & 16579836) >> 2 | colour & -16777216;
		}
		final float red = (colour >> 16 & 0xff) / 255F;
		final float green = (colour >> 8 & 0xff) / 255F;
		final float blue = (colour & 0xff) / 255F;
		i *= 2;
		j *= 2;
		GL11.glPushMatrix();
		GL11.glEnable(3553);
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glColor4f(red, green, blue, 255);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.drawString(g, s, i, j, false);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glPopMatrix();
	}

	public void drawGoodStringS(final Gui g, final String s, int i, int j, final int colour) {
		i *= 2;
		j *= 2;
		j -= 5;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.drawString(g, s, i + 2, j + 2, true);
		GL11.glScalef(2F, 2F, 2F);
		final float red = (colour >> 16 & 0xff) / 255F;
		final float green = (colour >> 8 & 0xff) / 255F;
		final float blue = (colour & 0xff) / 255F;
		new Color(red, green, blue, 1F);
		GL11.glPushMatrix();
		GL11.glEnable(3553);
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glColor4f(red, green, blue, 255);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.drawString(g, s, i, j, false);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glPopMatrix();
		GL11.glColor4f(1F, 1F, 1F, 255F);
	}

	public void drawGoodString(final Gui g, final String s, int i, int j, final Color c) {
		i *= 2;
		j *= 2;
		j -= 5;
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glPushMatrix();
		GL11.glEnable(3553);
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glColor4f(c.getRed() / 255, c.getGreen() / 255, c.getBlue() / 255, 1);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.drawString(g, s, i, j, false);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glColor4f(1F, 1F, 1F, 255F);
		GL11.glPopMatrix();
	}

	public void drawGoodStringS(final Gui g, final String s, int i, int j, final Color c) {
		i *= 2;
		j *= 2;
		j -= 5;
		GL11.glEnable(GL11.GL_COLOR_CLEAR_VALUE);
		GL11.glColor4f(c.getRed(), c.getGreen(), c.getBlue(), 255);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.drawStringS(g, s, i, j);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glDisable(GL11.GL_COLOR_CLEAR_VALUE);
		GL11.glColor4f(1F, 1F, 1F, 255F);
	}

	public void drawString(final Gui gui, final String s, double i, double j, final boolean flag) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texID);

		if (flag) {
			GL11.glColor4f(0.2F, 0.2F, 0.2F, 255F);
		}

		final double k = i;

		final double realY = j;

		try {
			for (int l = 0; l < s.length(); l++) {
				j = realY;
				char c1 = s.charAt(l);
				if (c1 == '|') {
					j -= 1;
				} else if (c1 == '[') {
					j -= 1;
				} else if (c1 == ']') {
					j -= 1;
				} else {
					j = realY;
				}
				if (c1 == '\n') {
					j += this.metrics.getAscent() + 4;
					i = k;
					l++;
					continue;
				}

				if (c1 == '\341' || c1 == '\340' || c1 == '\342' || c1 == '\344' || c1 == '\301' || c1 == '\300' || c1 == '\302' || c1 == '\304') {
					c1 = 'a';
				}

				if (c1 == '\351' || c1 == '\350' || c1 == '\352' || c1 == '\353' || c1 == '\311' || c1 == '\310' || c1 == '\312' || c1 == '\313') {
					c1 = 'e';
				}

				if (c1 == '\355' || c1 == '\354' || c1 == '\356' || c1 == '\357' || c1 == '\315' || c1 == '\314' || c1 == '\316' || c1 == '\317') {
					c1 = 'i';
				}

				if (c1 == '\363' || c1 == '\362' || c1 == '\364' || c1 == '\366' || c1 == '\323' || c1 == '\322' || c1 == '\324' || c1 == '\326') {
					c1 = 'o';
				}

				if (c1 == '\372' || c1 == '\371' || c1 == '\373' || c1 == '\374' || c1 == '\332' || c1 == '\331' || c1 == '\333' || c1 == '\334') {
					c1 = 'u';
				}

				if (c1 == '\247') {
					l++;
					final char c = s.charAt(l);

					if (c == 'a' || c == 'A' || c == 'g') {
						if (!flag) {
							GL11.glColor4f(0.25F, 1.0F, 0.25F, 1.0F);
						} else {
							GL11.glColor4f(0.05F, 0.2F, 0.05F, 1.0F);
						}
					}

					if (c == 'b' || c == 'B') {
						if (!flag) {
							GL11.glColor4f(0.25F, 1.0F, 1.0F, 1.0F);
						} else {
							GL11.glColor4f(0.05F, 0.2F, 0.2F, 1.0F);
						}
					}

					if (c == 'c' || c == 'C') {
						if (!flag) {
							GL11.glColor4f(1.0F, 0.25F, 0.25F, 1.0F);
						} else {
							GL11.glColor4f(0.2F, 0.05F, 0.05F, 1.0F);
						}
					}

					if (c == 'd' || c == 'D') {
						if (!flag) {
							GL11.glColor4f(1.0F, 0.25F, 1.0F, 1.0F);
						} else {
							GL11.glColor4f(0.2F, 0.05F, 0.2F, 1.0F);
						}
					}

					if (c == 'e' || c == 'E') {
						if (!flag) {
							GL11.glColor4f(1.0F, 1.0F, 0.25F, 1.0F);
						} else {
							GL11.glColor4f(0.2F, 0.2F, 0.05F, 1.0F);
						}
					}

					if (c == 'f' || c == 'F') {
						if (!flag) {
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						} else {
							GL11.glColor4f(0.2F, 0.2F, 0.2F, 1.0F);
						}
					}

					if (c == 'q') {
						GL11.glColor4f(255F, 0F, 0F, 255F);
					}

					if (c == '0') {
						GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
					}

					if (c == '1') {
						if (!flag) {
							GL11.glColor4f(0.0F, 0.0F, 0.75F, 1.0F);
						} else {
							GL11.glColor4f(0.0F, 0.0F, 0.1F, 1.0F);
						}
					}

					if (c == '2') {
						if (!flag) {
							GL11.glColor4f(0.0F, 0.75F, 0.0F, 1.0F);
						} else {
							GL11.glColor3f(0.0F, 0.1F, 0.0F);
						}
					}

					if (c == '3') {
						if (!flag) {
							GL11.glColor4f(0.0F, 0.75F, 0.75F, 1.0F);
						} else {
							GL11.glColor4f(0.0F, 0.1F, 0.1F, 1.0F);
						}
					}

					if (c == '4') {
						if (!flag) {
							GL11.glColor4f(0.75F, 0.0F, 0.0F, 1.0F);
						} else {
							GL11.glColor4f(0.1F, 0.0F, 0.0F, 1.0F);
						}
					}

					if (c == '5') {
						if (!flag) {
							GL11.glColor4f(0.75F, 0.0F, 0.75F, 1.0F);
						} else {
							GL11.glColor4f(0.1F, 0.0F, 0.1F, 1.0F);
						}
					}

					if (c == '6') {
						if (!flag) {
							GL11.glColor4f(1.0F, 0.75F, 0.0F, 1.0F);
						} else {
							GL11.glColor4f(0.1F, 0.1F, 0.0F, 1.0F);
						}
					}

					if (c == '7') {
						if (!flag) {
							GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
						} else {
							GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
						}
					}

					if (c == '8') {
						if (!flag) {
							GL11.glColor4f(0.25F, 0.25F, 0.25F, 1.0F);
						} else {
							GL11.glColor4f(0.05F, 0.05F, 0.05F, 1.0F);
						}
					}

					if (c != '9') {
						continue;
					}

					if (!flag) {
						GL11.glColor4f(0.25F, 0.25F, 1.0F, 1.0F);
					} else {
						GL11.glColor4f(0.05F, 0.05F, 0.2F, 1.0F);
					}

					if (c == 'x') {
						if (!flag) {
							GL11.glColor4f(0.2F, 0.2F, 0.50F, 1.0F);
						} else {
							GL11.glColor4f(0.05F, 0.05F, 0.05F, 1.0F);
						}
					}

					continue;
				}

				if (c1 <= '~') {
					this.drawChar(gui, c1, i, j);
					i = (int) (i + this.metrics.getStringBounds((new StringBuilder()).append("").append(c1).toString(), null).getWidth());
				}
			}
		} catch (final Exception e) {
		}
	}

	public FontMetrics getMetrics() {
		return this.metrics;
	}

	public int getStringWidth(final String s) {
		return (int) this.getBounds(s).getWidth();
	}

	public int getStringHeight(final String s) {
		return (int) this.getBounds(s).getHeight();
	}

	private Rectangle getBounds(final String s) {
		int i = 0;
		int j = 0;
		int k = 0;

		for (int l = 0; l < s.length(); l++) {
			final char c = s.charAt(l);

			if (c == '\\') {
				final char c1 = s.charAt(l + 1);

				if (c1 == 'n') {
					j += this.metrics.getAscent() + 2;

					if (k > i) {
						i = k;
					}

					k = 0;
				}

				l++;
			} else {
				k += this.metrics.stringWidth((new StringBuilder()).append("").append(c).toString());
			}
		}

		if (k > i) {
			i = k;
		}

		j += this.metrics.getAscent();
		return new Rectangle(0, 0, i, j);
	}

	private void drawChar(final Gui gui, final char c, final double i, final double j) {
		final Rectangle2D rectangle2d = this.metrics.getStringBounds((new StringBuilder()).append("").append(c).toString(), null);
		gui.drawTexturedModalRect(i, j - 2, this.xPos[(byte) c - this.startChar], this.yPos[(byte) c - this.startChar], rectangle2d.getWidth(), rectangle2d.getHeight() + this.metrics.getMaxDescent() + 2);
	}
}