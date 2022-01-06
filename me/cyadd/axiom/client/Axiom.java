package me.cyadd.axiom.client;

import java.io.File;
import java.io.IOException;

import me.cyadd.axiom.client.gui.screens.AxiomInGameGUI;
import me.cyadd.axiom.client.managers.CommandManager;
import me.cyadd.axiom.client.managers.FriendManager;
import me.cyadd.axiom.client.managers.HookManager;
import me.cyadd.axiom.client.managers.ModuleManager;
import me.cyadd.axiom.client.utilities.Logger;
import me.cyadd.axiom.client.utilities.RandomInteger;
import net.minecraft.client.Minecraft;

/**
 * Axiom is a client originally made by 'Widd'. This is going to be a re-make.
 * Check the changelog regularly.
 *
 *         d8888          d8b                        
 *        d88888          Y8P                        
 *    	 d88P888                                     
 *      d88P 888 888  888 888  .d88b.  88888b.d88b.  
 *     d88P  888 `Y8bd8P' 888 d88""88b 888 "888 "88b 
 *    d88P   888   X88K   888 888  888 888  888  888 
 *	 d8888888888 .d8""8b. 888 Y88..88P 888  888  888 
 *	d88P     888 888  888 888  "Y88P"  888  888  888                                
 *                                          
 *                                                                                                 
 * @author Cyadd + Wulf 
 * @since 03/06/2013
 */
public class Axiom {

	private static Axiom instance;
	private final File clientDirectory = new File(Minecraft.getMinecraftDir(), "axiom");

	private ModuleManager manager_Module;
	private CommandManager manager_Command;
	private FriendManager manager_Friend;
	private HookManager manager_Hook;

	private AxiomInGameGUI inGameGUI;

	public Axiom() {
		Logger.registerLogEventSystem(Logger.INFO, "[Axiom]: Client initializing.");
		this.initialize();
		Logger.registerLogEventSystem(Logger.INFO, "[Axiom]: Client initialized.");
	}

	/**
	 * Everything that needs to happen upon startup of the client. For example
	 * loading up name-protection and such.
	 */
	public void initialize() {
		Axiom.instance = this;
		this.getClientDir();
		this.manager_Module = new ModuleManager();
		this.manager_Command = new CommandManager();
		this.manager_Friend = new FriendManager();
		this.manager_Hook = new HookManager();
		this.inGameGUI = new AxiomInGameGUI(AxiomWrapper.getMinecraftObj());
		try {
			AxiomInput.loadHelp();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		AxiomWrapper.getMinecraftObj().ingameGUI = new AxiomInGameGUI(AxiomWrapper.getMinecraftObj());
	}

	/**
	 * This is used to create the client's Directory.
	 * 
	 * @return
	 */
	public File getClientDir() {
		if (!this.clientDirectory.exists()) {
			this.clientDirectory.mkdir();
			Logger.registerLogEventSystem(Logger.INFO, "[Axiom]: Created Dir.");
		}
		return this.clientDirectory;
	}

	/**
	 * Everything that needs to save on close of the client.
	 */
	@Override
	public void finalize() {
		this.getFriendManager().saveFriends();
		AxiomWrapper.getMinecraftObj().gameSettings.gammaSetting = 0;
		Logger.registerLogEventSystem(Logger.INFO, "[Axiom]: Client closing, Saved.");
	}

	/**
	 * The getter for the Axiom object. This is used in other classes for
	 * getting methods from within this class.
	 * 
	 * @return the instance
	 */
	public static Axiom getAxiom() {
		return Axiom.instance;
	}

	/**
	 * The getter for the ModuleManager object.
	 * 
	 * @return the manager_Module
	 */
	public ModuleManager getModuleManager() {
		return this.manager_Module;
	}

	/**
	 * The getter for the CommandManager object.
	 * 
	 * @return the manager_Command
	 */
	public CommandManager getCommandManager() {
		return this.manager_Command;
	}

	/**
	 * The getter for the HookManager object.
	 * 
	 * @return the manager_Hook
	 */
	public HookManager getHookManager() {
		return this.manager_Hook;
	}

	/**
	 * The getter for the FriendManager object.
	 * 
	 * @return the manager_Friend
	 */
	public FriendManager getFriendManager() {
		return this.manager_Friend;
	}

}
