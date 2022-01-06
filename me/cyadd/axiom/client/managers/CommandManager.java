package me.cyadd.axiom.client.managers;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.AxiomWrapper;
import me.cyadd.axiom.client.module.AxiomModule;
import me.cyadd.axiom.client.utilities.Logger;

/**
 * This is the class which holds all of the chat commands, it also holds the
 * hook for running an external command within the Module class.
 * 
 * @author Cyadd + Wulf
 * @since 31/05/2013
 */
public class CommandManager {

	public CommandManager() {
		Logger.registerLogEventSystem(Logger.INFO, this.getClass().getSimpleName() + " has been loaded.");
	}

	/**
	 * the method used to run the commands.
	 * 
	 * @see {@link net.minecraft.src.EntityClientPlayerMP#sendChatMessage}
	 * @param cmd
	 *            String.
	 */
	public static void runCommands(final String cmd) {
		if (cmd.startsWith(AxiomInput.prefix)) {
			for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
				module.runCommand(cmd);
			}
			for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
				if (cmd.equalsIgnoreCase(AxiomInput.prefix + "t " + module.getName())) {
					module.onToggle();
					AxiomInput.playSound(true);
					if (!cmd.equalsIgnoreCase(AxiomInput.prefix + "t help")) {
						if (module.isEnabled()) {
							AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage(AxiomInput.getClientNameInGame() + module.getName() + " enabled.");
						} else if (!module.isEnabled()) {
							AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage(AxiomInput.getClientNameInGame() + module.getName() + " disabled.");
						}
					}
				}
			}

			/**
			 * This if just a quick loop that will generate all of the
			 * formatting codes for Minecraft then present them to a string.
			 */
			if (cmd.equals(".tc")) {
				for (int i = 0; i < 16; i++) {
					AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage("\247" + Integer.toHexString(i) + "Testing colors! " + Integer.toHexString(i).toString());
				}
			}
		}
	}

}
