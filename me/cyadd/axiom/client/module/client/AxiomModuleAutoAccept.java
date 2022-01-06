package me.cyadd.axiom.client.module.client;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.managers.FriendManager;
import me.cyadd.axiom.client.module.AxiomModule;
import me.cyadd.axiom.client.utilities.Friend;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.StringUtils;

public class AxiomModuleAutoAccept extends AxiomModule {

	public AxiomModuleAutoAccept() {
		super("AutoAccept", EnumModuleType.CLIENT);
		this.setDescription("Automatically accepts teleports from friends.");
		this.setEnabled(true);
	}

	@Override
	public boolean onRecieveChat(final Packet3Chat c) {
		if (Axiom.getAxiom().getModuleManager().getModuleByName("Friends").isEnabled()) {
			final String s = StringUtils.stripControlCodes(c.message);
			Axiom.getAxiom().getFriendManager();
			for (final Friend friend : FriendManager.friendsList) {
				if (s.contains(friend.getAlias()) || s.contains(friend.getName())) {
					if (s.contains("has requested to teleport to you.")) {
						this.mc.thePlayer.sendChatMessage("/tpaccept");
						AxiomInput.addChat("Accepted teleport automatically.");
						break;
					}
				}
			}
		}
		return true;
	}

}
