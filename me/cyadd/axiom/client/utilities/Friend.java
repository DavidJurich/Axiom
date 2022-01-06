package me.cyadd.axiom.client.utilities;

/**
 * This is the friend class. It has methods used for the Friends command.
 * 
 * 	8888888888      d8b                        888 
 *	888             Y8P                        888 
 *	888                                        888 
 *	8888888 888d888 888  .d88b.  88888b.   .d88888 
 *	888     888P"   888 d8P  Y8b 888 "88b d88" 888 
 *	888     888     888 88888888 888  888 888  888 
 *	888     888     888 Y8b.     888  888 Y88b 888 
 *	888     888     888  "Y8888  888  888  "Y88888 
 *                                                                                        	
 * @author Cyadd + Wulf
 * @since 03/06/2013
 */
public class Friend {

	/**
	 * The string data types to store the name and alias of the player.
	 */
	private String name;
	private String alias;

	public Friend(final String name, final String alias) {
		this.name = name;
		this.alias = alias;
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the alias of the player.
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}

	/**
	 * Sets the name of a player.
	 * 
	 * @param s
	 */
	public void setName(final String s) {
		this.name = s;
	}

	/**
	 * Sets the alias of the player.
	 * 
	 * @param s
	 */
	public void setAlias(final String s) {
		this.alias = s;
	}

}
