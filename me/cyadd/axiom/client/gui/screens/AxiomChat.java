package me.cyadd.axiom.client.gui.screens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.gui.components.GuiUtilities;
import me.cyadd.axiom.client.utilities.CustomFont;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ChatClickData;
import net.minecraft.src.ChatLine;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiNewChat;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.StringUtils;

import org.lwjgl.opengl.GL11;


/**
 * A custom chat class.
 * 
 * @author Cyadd + Wulf
 * @since 03/06/2013
 */
public class AxiomChat extends GuiNewChat {

	private int startY = 0;

	private CustomFont chatText;

	/** The Minecraft instance. */
	private final Minecraft mc;

	/** A list of messages previously sent through the chat GUI */
	private final List sentMessages = new ArrayList();

	/** Chat lines to be displayed in the chat box */
	public final static List chatLines = new ArrayList();
	private int field_73768_d = 0;
	private boolean field_73769_e = false;

	public AxiomChat(final Minecraft par1Minecraft) {
		super(par1Minecraft);
		this.mc = par1Minecraft;

		if (this.chatText == null) {
			this.chatText = new CustomFont(this.mc, "Verdana Bold", 17);
		}
	}

	@Override
	public void drawChat(final int par1) {
		if (this.mc.gameSettings.chatVisibility != 2) {
			final ScaledResolution var51 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			var51.getScaledWidth();
			var51.getScaledHeight();
			byte var2 = 10;
			boolean var3 = false;
			int var4 = 0;
			final int var5 = AxiomChat.chatLines.size();
			final float var6 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

			if (var5 > 0) {
				if (this.getChatOpen()) {
					var2 = 20;
					var3 = true;
				}

				int var7;
				int var9;
				int var12 = 0;

				for (var7 = 0; var7 + this.field_73768_d < AxiomChat.chatLines.size() && var7 < var2; ++var7) {
					final ChatLine var8 = (ChatLine) AxiomChat.chatLines.get(var7 + this.field_73768_d);

					if (var8 != null) {
						var9 = par1 - var8.getUpdatedCounter();

						if (var9 < 200 || var3) {
							double var10 = var9 / 200.0D;
							var10 = 1.0D - var10;
							var10 *= 10.0D;

							if (var10 < 0.0D) {
								var10 = 0.0D;
							}

							if (var10 > 1.0D) {
								var10 = 1.0D;
							}

							var10 *= var10;
							var12 = (int) (255.0D);

							if (var3) {
								var12 = 255;
							}

							var12 = (int) (var12 * var6);
							++var4;

							if (var12 > 3) {
								String var15 = var8.getChatLineString();
								final int var14 = -var7 * 9;
								this.startY = var14 - 1;

								if (!this.mc.gameSettings.chatColours) {
									var15 = StringUtils.stripControlCodes(var15);
								}
							}
						}
					}
				}

				if (var12 > 0) {
					if (Axiom.getAxiom().getModuleManager().getModuleByName("Chat").isEnabled()) {
						GuiUtilities.drawBorderedRect(3, this.startY - 17, 343, -4, 0xaa000000, 0xff000000, true);

						if (this.getChatOpen()) {
							GuiUtilities.drawBorderedRect(3, this.startY - 30, 343, this.startY - 18, 0xaa000000, 0xff000000, true);
							GuiUtilities.drawTTFStringWithShadow(this.chatText, "Chat", 7, this.startY - 31, 0xFFFFFF);
						}
					}
				}

				for (var7 = 0; var7 + this.field_73768_d < AxiomChat.chatLines.size() && var7 < var2; ++var7) {
					final ChatLine var8 = (ChatLine) AxiomChat.chatLines.get(var7 + this.field_73768_d);

					if (var8 != null) {
						var9 = par1 - var8.getUpdatedCounter();

						if (var9 < 200 || var3) {
							double var10 = var9 / 200.0D;
							var10 = 1.0D - var10;
							var10 *= 10.0D;

							if (var10 < 0.0D) {
								var10 = 0.0D;
							}

							if (var10 > 1.0D) {
								var10 = 1.0D;
							}

							var10 *= var10;
							var12 = (int) (255.0D);

							if (var3) {
								var12 = 255;
							}

							var12 = (int) (var12 * var6);
							++var4;

							if (var12 > 3) {
								String var15 = var8.getChatLineString();
								final byte var13 = 3;
								final int var14 = -var7 * 9;
								this.startY = var14 - 1;
								if (!Axiom.getAxiom().getModuleManager().getModuleByName("Chat").isEnabled()) {
									Gui.drawRect(var13, var14 - 1, var13 + 320 + 4, var14 + 8, var12 / 2 << 24);
								}
								GL11.glEnable(GL11.GL_BLEND);

								if (!this.mc.gameSettings.chatColours) {
									var15 = StringUtils.stripControlCodes(var15);
								}

								var15 = Axiom.getAxiom().getHookManager().onChatLineRender(var15);
								if (Axiom.getAxiom().getModuleManager().getModuleByName("Chat").isEnabled()) {
									GuiUtilities.drawTTFStringWithShadow(this.chatText, var15, var13 + 4, var14 - 17.8f, 16777215 + (var12 << 24));
								} else {
									this.mc.fontRenderer.drawStringWithShadow(var15, var13, var14, 0xffffff + (var12 << 24));
								}
							}
						}
					}
				}
				if (var3) {
					var7 = this.mc.fontRenderer.FONT_HEIGHT;
					GL11.glTranslatef(0.0F, var7, 0.0F);
					final int var16 = var5 * var7 + var5;
					var9 = var4 * var7 + var4;
					final int var17 = this.field_73768_d * var9 / var5;
					final int var11 = var9 * var9 / var16;

					if (var16 != var9) {
						var12 = var17 > 0 ? 170 : 96;
						final int var18 = this.field_73769_e ? 13382451 : 3355562;
						Gui.drawRect(0, -var17, 2, -var17 - var11, var18 + (var12 << 24));
						Gui.drawRect(2, -var17, 1, -var17 - var11, 13421772 + (var12 << 24));
					}
				}
			}
		}
	}

	public void func_73761_a() {
		AxiomChat.chatLines.clear();
		this.sentMessages.clear();
	}

	/**
	 * takes a String and prints it to chat
	 */
	@Override
	public void printChatMessage(final String par1Str) {
		this.printChatMessageWithOptionalDeletion(par1Str, 0);
	}

	/**
	 * prints the String to Chat. If the ID is not 0, deletes an existing Chat
	 * Line of that ID from the GUI
	 */
	@Override
	public void printChatMessageWithOptionalDeletion(final String par1Str, final int par2) {
		final boolean var3 = this.getChatOpen();
		boolean var4 = true;

		if (par2 != 0) {
			this.deleteChatLine(par2);
		}

		final Iterator var5 = this.mc.fontRenderer.listFormattedStringToWidth(par1Str, 320).iterator();

		while (var5.hasNext()) {
			String var6 = (String) var5.next();

			if (var3 && this.field_73768_d > 0) {
				this.field_73769_e = true;
				this.scroll(1);
			}

			if (!var4) {
				var6 = " " + var6;
			}

			var4 = false;
			AxiomChat.chatLines.add(0, new ChatLine(this.mc.ingameGUI.getUpdateCounter(), var6, par2));
		}

		while (AxiomChat.chatLines.size() > 100) {
			AxiomChat.chatLines.remove(AxiomChat.chatLines.size() - 1);
		}
	}

	/**
	 * Gets the list of messages previously sent through the chat GUI
	 */
	@Override
	public List getSentMessages() {
		return this.sentMessages;
	}

	/**
	 * Adds this string to the list of sent messages, for recall using the
	 * up/down arrow keys
	 */
	@Override
	public void addToSentMessages(final String par1Str) {
		if (this.sentMessages.isEmpty() || !((String) this.sentMessages.get(this.sentMessages.size() - 1)).equals(par1Str)) {
			this.sentMessages.add(par1Str);
		}
	}

	/**
	 * Resets the chat scroll (executed when the GUI is closed)
	 */
	@Override
	public void resetScroll() {
		this.field_73768_d = 0;
		this.field_73769_e = false;
	}

	/**
	 * Scrolls the chat by the given number of lines.
	 */
	@Override
	public void scroll(final int par1) {
		this.field_73768_d += par1;
		final int var2 = AxiomChat.chatLines.size();

		if (this.field_73768_d > var2 - 20) {
			this.field_73768_d = var2 - 20;
		}

		if (this.field_73768_d <= 0) {
			this.field_73768_d = 0;
			this.field_73769_e = false;
		}
	}

	@Override
	public ChatClickData func_73766_a(final int par1, final int par2) {
		if (!this.getChatOpen()) {
			return null;
		} else {
			final ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			final int var4 = var3.getScaleFactor();
			final int var5 = par1 / var4 - 3;
			final int var6 = par2 / var4 - 40;

			if (var5 >= 0 && var6 >= 0) {
				final int var7 = Math.min(20, AxiomChat.chatLines.size());

				if (var5 <= 320 && var6 < this.mc.fontRenderer.FONT_HEIGHT * var7 + var7) {
					final int var8 = var6 / (this.mc.fontRenderer.FONT_HEIGHT + 1) + this.field_73768_d;
					return new ChatClickData(this.mc.fontRenderer, (ChatLine) AxiomChat.chatLines.get(var8), var5, var6 - (var8 - this.field_73768_d) * this.mc.fontRenderer.FONT_HEIGHT + var8);
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	/**
	 * Adds a message to the chat after translating to the client's locale.
	 */
	@Override
	public void addTranslatedMessage(final String par1Str, final Object... par2ArrayOfObj) {
		this.printChatMessage(StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj));
	}

	/**
	 * @return {@code true} if the chat GUI is open
	 */
	@Override
	public boolean getChatOpen() {
		return this.mc.currentScreen instanceof GuiChat;
	}

	/**
	 * finds and deletes a Chat line by ID
	 */
	@Override
	public void deleteChatLine(final int par1) {
		final Iterator var2 = AxiomChat.chatLines.iterator();
		ChatLine var3;

		do {
			if (!var2.hasNext()) {
				return;
			}

			var3 = (ChatLine) var2.next();
		} while (var3.getChatLineID() != par1);

		var2.remove();
	}
}