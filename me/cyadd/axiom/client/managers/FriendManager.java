package me.cyadd.axiom.client.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.utilities.Friend;
import me.cyadd.axiom.client.utilities.Logger;
import net.minecraft.src.StringUtils;

/**
 * This is the Friend manager. It holds methods that can be used to see if a
 * player is on your friends list.
 * 
 * @author Cyadd + Wulf
 * @since 03/06/2013
 */
public class FriendManager {

	/**
	 * The arrayList that holds the friends.
	 */
	public static ArrayList<Friend> friendsList = new ArrayList<Friend>();

	public FriendManager() {
		this.loadFriends();
		Logger.registerLogEventSystem(Logger.INFO, this.getClass().getSimpleName() + " has been loaded.");
	}

	/**
	 * This is the method that is used to add friends to the array list.
	 * 
	 * @param name
	 *            Name of player you want to add.
	 * @param alias
	 *            Alias you want the player to be under.
	 */
	public void addFriend(final String name, final String alias) {
		FriendManager.friendsList.add(new Friend(name, alias));
		this.saveFriends();
	}

	/**
	 * Removes the selected friend from the arralist.
	 * 
	 * @param name
	 *            Original name of player.
	 */
	public void removeFriend(final String name) {
		for (final Friend friend : FriendManager.friendsList) {
			if (friend.getName().equalsIgnoreCase(name)) {
				FriendManager.friendsList.remove(friend);
				Logger.registerLogEventSystem(Logger.INFO, "Removed " + name);
				break;
			}
		}
	}

	/**
	 * A boolean to check if a player is already on your friends list.
	 * 
	 * @param name
	 *            Name of player you want to check.
	 * @return
	 */
	public boolean isFriend(final String name) {
		boolean isFriend = false;
		for (final Friend friend : FriendManager.friendsList) {
			if (friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
				isFriend = true;
				break;
			}
		}
		return isFriend;
	}

	/**
	 * This method saves a String to a file.
	 */
	public void saveFriends() {
		try {
			final File file = new File(Axiom.getAxiom().getClientDir(), "friends.txt");
			final BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (final Friend friend : FriendManager.friendsList) {
				out.write(friend.getName() + ":" + friend.getAlias());
				out.write("\r\n");
			}
			out.close();
		} catch (final Exception e) {
		}
	}

	/**
	 * This method loads up the friends.
	 */
	public void loadFriends() {
		try {
			final File file = new File(Axiom.getAxiom().getClientDir(), "friends.txt");
			final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			final DataInputStream in = new DataInputStream(fstream);
			@SuppressWarnings("resource")
			final BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				final String curLine = line.trim();
				final String name = curLine.split(":")[0];
				final String alias = curLine.split(":")[1];
				this.addFriend(name, alias);
				Logger.registerLogEventSystem(Logger.INFO, name + ":" + alias);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			this.saveFriends();
		}
	}

}
