package me.cyadd.axiom.client.module;

/**
 * This is the base command class. An interface in the Java programming language
 * is an abstract type that is used to specify an interface (in the generic
 * sense of the term) that classes must implement.
 * 
 *   .d8888b.                                                              888 
 *	d88P  Y88b                                                             888 
 *	888    888                                                             888 
 *	888         .d88b.  88888b.d88b.  88888b.d88b.   8888b.  88888b.   .d88888 
 *	888        d88""88b 888 "888 "88b 888 "888 "88b     "88b 888 "88b d88" 888 
 *	888    888 888  888 888  888  888 888  888  888 .d888888 888  888 888  888 
 *	Y88b  d88P Y88..88P 888  888  888 888  888  888 888  888 888  888 Y88b 888 
 * 	 "Y8888P"   "Y88P"  888  888  888 888  888  888 "Y888888 888  888  "Y88888 
 *                                                                          
 * @see {@link net.minecraft.src.EntityClientPlayerMP#sendChatMessage}
 * @author Wulf
 * @since 06/06/2013
 */
public interface AxiomCommand {

	/**
	 * This returns the name of the Command. For ease of access only.
	 * 
	 * @return
	 */
	public String commandName();

	/**
	 * The help for the command which will help users to find out what the
	 * command does, it could also include the correct syntax.
	 * 
	 * @return
	 */
	public String commandHelp();

	/**
	 * The Syntax used for Modules which contain more 'complex' commands.
	 * Example; <br>
	 * <code> .killaura range/speed integer/float. </code>
	 * 
	 * @return
	 */
	public String commandSyntax();

	/**
	 * This method is used to tell the client what to do on toggle of the
	 * command.
	 */
	public void onCommand();

}
