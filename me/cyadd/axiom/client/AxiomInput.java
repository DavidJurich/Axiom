package me.cyadd.axiom.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import me.cyadd.axiom.client.module.AxiomModule;
import me.cyadd.axiom.client.utilities.Logger;

import org.lwjgl.input.Keyboard;


/**
 * This is the input class where all of the utilities are held in.
 * 
 * 	8888888                            888    
 * 	  888                              888    
 *    888                              888    
 *    888   88888b.  88888b.  888  888 888888 
 *    888   888 "88b 888 "88b 888  888 888    
 *    888   888  888 888  888 888  888 888    
 *    888   888  888 888 d88P Y88b 888 Y88b.  
 *  8888888 888  888 88888P"   "Y88888  "Y888 
 *                   888                      
 *                   888                      
 *                   888                      
 * 
 * @author Cyadd + Wulf
 * @since 31/05/2013
 */
public class AxiomInput {

	/** The prefix used when entering a chat command. **/
	public static String prefix = ".";

	/** This is the string that holds the version of the client. **/
	private static String CLIENT_VERSION = "2.0";

	/** This is the string that holds the name of the client. **/
	private static String CLIENT_NAME = "Axiom";

	/** This is the string that holds the author of the clients name. **/
	private static String CLIENT_AUTHOR = "Cyadd";

	/** This is the string that holds the version of Minecraft **/
	private static String MC_VERSION = "1.5.2";

	/**
	 * This is the getter for the client version.
	 * 
	 * @return
	 */
	public static String getClientVersion() {
		return AxiomInput.CLIENT_VERSION;
	}

	/**
	 * This is the getter for the version of Minecraft.
	 * 
	 * @return
	 */
	public static String getMCVersion() {
		return AxiomInput.MC_VERSION;
	}

	/**
	 * This is the getter for the formatted client name.
	 * 
	 * @return
	 */
	public static String getClientNameInGame() {
		return "§3[" + AxiomInput.CLIENT_NAME + "]:§f ";
	}

	/**
	 * This is the getter for the client author.
	 * 
	 * @return
	 */
	public static String getAuthor() {
		return AxiomInput.CLIENT_AUTHOR;
	}

	/**
	 * A loop through the built in key-bind method within the 'Module' class.
	 * 
	 * @see {@link net.minecraft.client.Minecraft#runTick}
	 * @param i
	 *            Integer for the keybind(<code>Keyboard.KEY_*</code>)
	 */
	public static void runKeybind(final int i) {
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.getBind() == i) {
				m.onToggle();
				AxiomInput.playSound(true);
			}
		}
	}

	/**
	 * Plays a sound if boolean is true
	 * 
	 * @param b
	 */
	public static void playSound(final boolean b) {
		AxiomWrapper.getMinecraftObj().sndManager.playSoundFX("random.click", 1.0f, b ? 1.0f : 1.0f);
	}

	/**
	 * Adds a message to the chat with the '[Axiom]:' prefix.
	 * 
	 * @param s
	 *            String after prefix.
	 */
	public static void addChat(final String s) {
		AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage(AxiomInput.getClientNameInGame() + s);
	}

	/**
	 * This is the file which loads/creates the help file for the client.
	 * 
	 * @throws IOException
	 */
	public static void loadHelp() throws IOException {
		final File help = new File(Axiom.getAxiom().getClientDir(), "help.txt");
		if (!help.exists()) {
			help.createNewFile();
		}
		try {
			final PrintWriter printwriter = new PrintWriter(new FileWriter(help));
			printwriter.println("-- # Axiom Help document # --");
			printwriter.println(" ");
			for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
				printwriter.println(" * " + m.getName());
				printwriter.println(" - Key: " + Keyboard.getKeyName(m.getBind()));
				printwriter.println(" - Category: " + m.getCategory());
				printwriter.println("   - Description: " + m.getDescription());
				printwriter.println(" ");
			}
			printwriter.close();
			Logger.registerLogEventSystem(Logger.INFO, "Generated Help document.");
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * This is the check to make sure you can actually sprint, which means that
	 * no NoCheat detections will be set off.
	 * 
	 * @return
	 */
	public static boolean canSprint() {
		return !AxiomWrapper.getMinecraftObj().thePlayer.isSneaking() && AxiomWrapper.getMinecraftObj().thePlayer.movementInput.moveForward > 0 && AxiomWrapper.getMinecraftObj().thePlayer.getFoodStats().getFoodLevel() > 6 && !AxiomWrapper.getMinecraftObj().thePlayer.isCollidedHorizontally;
	}

}
