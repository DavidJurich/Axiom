package me.cyadd.axiom.client.module.client;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.managers.FriendManager;
import me.cyadd.axiom.client.module.AxiomModule;
import me.cyadd.axiom.client.utilities.Friend;

public class AxiomModuleFriends extends AxiomModule {

	public AxiomModuleFriends() {
		super("Friends", EnumModuleType.CLIENT);
		this.setDescription("A friend system with aliases.");
		this.setCustomSyntax(AxiomInput.prefix + "friend <add/del/list/clear> <username> <alias>");
		this.setEnabled(true);
	}

	@Override
	public String onRenderChat(final String par1Str) {
		String s = par1Str;
		for (final Friend friend : FriendManager.friendsList) {
			s = s.replaceAll(friend.getName(), "§3" + friend.getAlias() + "§f");
		}
		return s;
	}

	@Override
	public void runCommand(final String s) {
		final String args[] = s.split(" ");
		try {
			if (args[0].equalsIgnoreCase(AxiomInput.prefix + "friend")) {
				if (args[1].equalsIgnoreCase("add")) {
					final String name = args[2];
					final String alias = args[3];
					if (!Axiom.getAxiom().getFriendManager().isFriend(name)) {
						AxiomInput.addChat(alias + " has been added.");
						Axiom.getAxiom().getFriendManager().addFriend(name, alias);
						Axiom.getAxiom().getFriendManager().saveFriends();
					} else {
						AxiomInput.addChat(name + " is already your friend.");
					}
				}
				if (args[1].equalsIgnoreCase("del")) {
					final String name = args[2];
					if (Axiom.getAxiom().getFriendManager().isFriend(name)) {
						Axiom.getAxiom().getFriendManager().removeFriend(name);
						AxiomInput.addChat(name + " has been removed.");
						Axiom.getAxiom().getFriendManager().saveFriends();
					} else {
						AxiomInput.addChat(name + " is not your friend.");
					}
				}
				if (args[1].equalsIgnoreCase("list")) {
					AxiomInput.addChat(Axiom.getAxiom().getFriendManager().friendsList.size() + " friend(s).");
					for (final Friend friend : FriendManager.friendsList) {
						AxiomInput.addChat(friend.getAlias());
					}
				}
				if (args[1].equalsIgnoreCase("clear")) {
					try {
						FriendManager.friendsList.clear();
						Axiom.getAxiom().getFriendManager().saveFriends();
						AxiomInput.addChat("Cleared friends.");
					} catch (final Exception e) {
					}
				}
			}
		} catch (final Exception e) {
			AxiomInput.addChat("Usage: " + this.getCustomSyntax());
		}
	}
}
