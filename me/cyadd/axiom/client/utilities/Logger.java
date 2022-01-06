package me.cyadd.axiom.client.utilities;

import me.cyadd.axiom.client.AxiomWrapper;

/**
 * This is the logger class for showing the level of importance of information.
 * It is customised to suit Minecraft's chat methods and formatting colours,
 * therefore it is only useful in-game, it also only sends the information to
 * the player using the method 'addChatMessage(String)', therefore if for some
 * reason you want to send the information to the server you would have to use
 * 'sendChatMessage(String)'.
 * 
 * 	888                                                 
 *	888                                                 
 *	888                                                 
 *	888      .d88b.   .d88b.   .d88b.   .d88b.  888d888 
 *	888     d88""88b d88P"88b d88P"88b d8P  Y8b 888P"   
 *	888     888  888 888  888 888  888 88888888 888     
 *	888     Y88..88P Y88b 888 Y88b 888 Y8b.     888     
 *	88888888 "Y88P"   "Y88888  "Y88888  "Y8888  888     
 *                        888      888                  
 *                   Y8b d88P Y8b d88P                  
 *                    "Y88P"   "Y88P"                   
 * 
 * @author Cyadd + Wulf
 * @since 31/05/2013
 */
public enum Logger {

	/**
	 * These are the five levels of importance that the level change be changed
	 * to. More can be added, however these are the main ones I could think
	 * would be useful.
	 */
	WARN, FATAL, ERROR, INFO, DEBUG;

	/**
	 * This method is for giving info to the user in-game with a level of
	 * importance followed by a string with information. There are five level of
	 * importance which can be registered;
	 * <code> WARN, FATAL, ERROR, INFO, DEBUG. </code>
	 * 
	 * @param logger
	 *            Flag for level of Logger.
	 * @param message
	 *            String after the level.
	 */
	public static void registerLogEvent(final Logger logger, final String message) {
		switch (logger) {
			case WARN:
				AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage("§c" + logger.name() + ": " + message);
				break;
			case FATAL:
				AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage("§4" + logger.name() + ": " + message);
				break;
			case ERROR:
				AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage("§6" + logger.name() + ": " + message);
				break;
			case INFO:
				AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage("§9" + logger.name() + ": " + message);
				break;
			case DEBUG:
				AxiomWrapper.getMinecraftObj().thePlayer.addChatMessage("§d" + logger.name() + ": " + message);
				break;
			default:
				break;
		}
	}

	/**
	 * This method is for giving info to the user in the system with a level of
	 * importance followed by a string with information. There are five level of
	 * importance which can be registered;
	 * <code> WARN, FATAL, ERROR, INFO, DEBUG. </code>
	 * 
	 * @param logger
	 *            Flag for level of Logger.
	 * @param message
	 *            String after the level.
	 */
	public static void registerLogEventSystem(final Logger logger, final String message) {
		switch (logger) {
			case WARN:
				System.err.println(logger.name() + ": " + message);
				System.err.flush();
				break;
			case FATAL:
				System.err.println(logger.name() + ": " + message);
				System.err.flush();
				break;
			case ERROR:
				System.err.println(logger.name() + ": " + message);
				System.err.flush();
				break;
			case INFO:
				System.out.println(logger.name() + ": " + message);
				System.out.flush();
				break;
			case DEBUG:
				System.out.println(logger.name() + ": " + message);
				System.out.flush();
				break;
			default:
				break;
		}
	}

}
