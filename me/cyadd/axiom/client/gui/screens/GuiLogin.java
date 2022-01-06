package me.cyadd.axiom.client.gui.screens;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import me.cyadd.axiom.client.gui.components.PasswordField;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;

import org.lwjgl.input.Keyboard;

public class GuiLogin extends GuiScreen {

	private GuiTextField fieldNick;
	private PasswordField fieldPass;

	private final GuiScreen parentScreen;

	public GuiLogin(final GuiScreen guiScreen) {
		this.parentScreen = guiScreen;
	}

	@Override
	protected void actionPerformed(final GuiButton var0) {
		if (!var0.enabled) {
			return;
		}
		if (var0.id == 2) {

		} else {
			if (var0.id == 1) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else if (var0.id == 0) {
				if (this.fieldPass.getText().length() > 0) {
					try {
						this.login(this.fieldNick.getText(), this.fieldPass.getText());
					} catch (final UnsupportedEncodingException e) {
					}
					this.fieldPass.setText("");
				} else {
					String s = this.fieldNick.getText().trim();
					s = s.replaceAll("&", "\247");
					this.mc.session.username = s;
					this.mc.session.sessionId = "-";
					// state = "Logged in as offline name \"" + s + "\".";
				}
			}
		}
	}

	@Override
	public void drawScreen(final int var0, final int var1, final float var2) {
		final StringTranslate var3 = StringTranslate.getInstance();
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, var3.translateKey("Account Login"), this.width / 2, (this.height / 4 - 60) + 40, 0xffffff);
		this.fontRenderer.drawString("Username", this.width / 2 - 100, (this.height / 4 - 10) + 10, 0xffffff);
		this.fieldNick.drawTextBox();
		this.fontRenderer.drawString("Password", this.width / 2 - 100, (this.height / 4 - 10) + 52, 0xffffff);
		this.fieldPass.drawTextBox();

		if (this.mc.session.sessionId != "-") {
		}
		super.drawScreen(var0, var1, var2);
	}

	@Override
	public void initGui() {
		StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Login"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
		final String var1 = this.mc.session.username;
		((GuiButton) this.buttonList.get(0)).enabled = var1.length() > 0;
		this.fieldNick = new GuiTextField(this.fontRenderer, this.width / 2 - 100, (this.height / 4 - 10) + 20, 200, 20);
		this.fieldPass = new PasswordField(this.fontRenderer, this.width / 2 - 100, (this.height / 4 - 10) + 50 + 12, 200, 20);

		this.fieldNick.setMaxStringLength(228);
		this.fieldNick.setText(this.mc.session.username);

		this.fieldPass.setMaxStringLength(228);
	}

	@Override
	protected void keyTyped(final char var0, final int var1) {
		this.fieldNick.textboxKeyTyped(var0, var1);
		if (var0 == '\r') {
			this.actionPerformed((GuiButton) this.buttonList.get(0));
		}
		((GuiButton) this.buttonList.get(0)).enabled = this.fieldNick.getText().length() > 0;
		this.fieldPass.textboxKeyTyped(var0, var1);
	}

	public void login(final String var0, final String var1) throws UnsupportedEncodingException {
		String var2;
		var2 = GuiLogin.excutePost("https://login.minecraft.net/", (new StringBuilder("user=")).append(URLEncoder.encode(this.fieldNick.getText(), "UTF-8")).append("&password=").append(URLEncoder.encode(this.fieldPass.getText(), "UTF-8")).append("&version=").append(13).toString());
		if (var2 == null) {
			return;
		}
		if (!var2.contains(":")) {
			return;
		}
		try {
			final String[] var3 = var2.split(":");
			this.mc.session.username = var3[2].trim();
			this.mc.session.sessionId = var3[3].trim();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return;
	}

	@Override
	protected void mouseClicked(final int var0, final int var1, final int var2) {
		super.mouseClicked(var0, var1, var2);
		this.fieldNick.mouseClicked(var0, var1, var2);
		this.fieldPass.mouseClicked(var0, var1, var2);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		this.fieldNick.updateCursorCounter();
		this.fieldPass.updateCursorCounter();
	}

	public static String excutePost(final String var0, final String var1) {
		HttpsURLConnection var2 = null;
		try {
			try {
				final URL url = new URL(var0);
				var2 = (HttpsURLConnection) url.openConnection();
				var2.setRequestMethod("POST");
				var2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				var2.setRequestProperty("Content-Type", Integer.toString(var1.getBytes().length));
				var2.setRequestProperty("Content-Language", "en-US");
				var2.setUseCaches(false);
				var2.setDoInput(true);
				var2.setDoOutput(true);
				var2.connect();
				final DataOutputStream var3 = new DataOutputStream(var2.getOutputStream());
				var3.writeBytes(var1);
				var3.flush();
				var3.close();
				final InputStream var4 = var2.getInputStream();
				final BufferedReader var5 = new BufferedReader(new InputStreamReader(var4));
				final StringBuffer var6 = new StringBuffer();
				String var7;

				while ((var7 = var5.readLine()) != null) {
					var6.append(var7);
					var6.append('\r');
				}

				var5.close();
				final String var8 = var6.toString();
				return var8;
			} catch (final Exception exception) {
				exception.printStackTrace();
			}

			return null;
		} finally {
			if (var2 != null) {
				var2.disconnect();
			}
		}
	}

}