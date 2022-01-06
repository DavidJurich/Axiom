package me.cyadd.axiom.client.module;

import java.util.ArrayList;
import java.util.List;

import me.cyadd.axiom.client.AxiomWrapper;
import me.cyadd.axiom.client.enums.EnumModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Packet3Chat;

/**
 * This is the main Module class, it will hold the methods which the modules
 * will use to run.
 * 
 * 	888b     d888               888          888          
 *	8888b   d8888               888          888          
 *	88888b.d88888               888          888          
 *	888Y88888P888  .d88b.   .d88888 888  888 888  .d88b.  
 *	888 Y888P 888 d88""88b d88" 888 888  888 888 d8P  Y8b 
 *	888  Y8P  888 888  888 888  888 888  888 888 88888888 
 *	888   "   888 Y88..88P Y88b 888 Y88b 888 888 Y8b.     
 *	888       888  "Y88P"   "Y88888  "Y88888 888  "Y8888                                                   
 * 
 * @author Cyadd
 * @since 31/05/2013
 */
public class AxiomModule {

	protected Minecraft mc = AxiomWrapper.getMinecraftObj();

	protected boolean enabled;
	protected boolean onArray;

	protected String name;
	protected int colour;
	protected EnumModuleType category;
	protected int bind;
	protected String description;
	protected String syntax;

	/**
	 * An array to keep everything synchronised, which means no thread
	 * interference.
	 */
	List<AxiomModule> module = new ArrayList<AxiomModule>();

	/**
	 * This is the main constructor for the Modules.
	 * 
	 * @param name
	 *            Name of the Module.
	 * @param colour
	 *            Colour of the Module on the array.
	 * @param category
	 *            Category of the Module.
	 * @param binds
	 *            Key-bind of the Module.
	 */
	public AxiomModule(final String name, final int colour, final EnumModuleType category, final int bind) {
		this.name = name;
		this.colour = colour;
		this.category = category;
		this.bind = bind;
		this.onArray = true;
		this.module.add(this);
	}

	/**
	 * This is a second constructor for the Modules.
	 * 
	 * @param name
	 *            Name of Module.
	 * @param category
	 *            Category of the Module.
	 */
	public AxiomModule(final String name, final EnumModuleType category) {
		this.name = name;
		this.colour = 0;
		this.category = category;
		this.bind = 0;
		this.onArray = false;
		this.module.add(this);
	}

	/**
	 * Used to get the boolean state of a module.
	 * 
	 * @return the enabled
	 */
	public final boolean isEnabled() {
		synchronized (this.module) {
			return this.enabled;
		}
	}

	/**
	 * Used to get the name of a module, and if a name isn't set then it will
	 * get the simple class name.
	 * 
	 * @return the name
	 */
	public final String getName() {
		synchronized (this.module) {
			if (this.name == null) {
				this.name = this.getClass().getSimpleName();
			}
			return this.name;
		}
	}

	/**
	 * Used to get the colour of the Module for the ArrayList.
	 * 
	 * @return the colour
	 */
	public final int getColour() {
		synchronized (this.module) {
			return this.colour;
		}
	}

	/**
	 * Used to get the category of a certain module.
	 * 
	 * @return the category
	 */
	public final EnumModuleType getCategory() {
		synchronized (this.module) {
			return this.category;
		}
	}

	/**
	 * Used to get the keybind of the module.
	 * 
	 * @return the bind
	 */
	public final int getBind() {
		synchronized (this.module) {
			return this.bind;
		}
	}

	/**
	 * Returns true if the Module is supposed to be on the arrayList.
	 * 
	 * @return
	 */
	public boolean isOnArray() {
		synchronized (this.module) {
			return this.onArray;
		}
	}

	/**
	 * Used to get the description of the Module.
	 * 
	 * @return the description
	 */
	public final String getDescription() {
		synchronized (this.module) {
			return this.description;
		}
	}

	/**
	 * Used to get the syntax of the Module.
	 * 
	 * @return the syntax
	 */
	public final String getCustomSyntax() {
		synchronized (this.module) {
			if (this.syntax == null) {
				this.syntax = "No syntax available.";
			}
			return this.syntax;
		}
	}

	/**
	 * Used to set the syntax of a Module.
	 * 
	 * @param syntax
	 *            the syntax to set
	 */
	public final void setCustomSyntax(final String syntax) {
		synchronized (this.module) {
			this.syntax = syntax;
		}
	}

	/**
	 * Used to set the description of a Module.
	 * 
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(final String description) {
		synchronized (this.module) {
			this.description = description;
		}
	}

	/**
	 * Used to set a module boolean true or false.
	 * 
	 * @param enabled
	 *            the enabled to set
	 */
	public final void setEnabled(final boolean enabled) {
		synchronized (this.module) {
			this.enabled = enabled;
		}
	}

	/**
	 * Used to set a bind something else.
	 * 
	 * @param bind
	 *            the bind to set
	 */
	public final void setBind(final int bind) {
		synchronized (this.module) {
			this.bind = bind;
		}
	}

	/** Used to toggle a Modules state. **/
	protected void toggle() {
		synchronized (this.module) {
			this.enabled = !this.enabled;
			if (this.enabled) {
				this.onEnable();
			} else if (!this.enabled) {
				this.onDisable();
			}
		}
	}

	/** What to do on toggle of the Module. **/
	public void onToggle() {
		synchronized (this.module) {
			this.toggle();
		}
	}

	/** What happens once the Module is enabled. **/
	protected void onEnable() {
	}

	/** What happens once the Module is disabled. **/
	protected void onDisable() {
	}

	/** What happens every in-game tick. **/
	public void onRunnableTick() {
	}

	/** A method used to render something onto the screen. **/
	public void renderOverlay() {
	}

	/** A method for running a command within a class. **/
	public void runCommand(final String s) {
	}

	/** This method is called before the Motion update. **/
	public void preMotionUpdate() {
	}

	/** This method is called after the Motion update. **/
	public void postMotionUpdate() {
	}

	/** This method is called to render and object. **/
	public void onRenderObject() {
	}
	
	/** This method is called on render of the chat. **/
	public String onRenderChat(final String s) {
		return s;
	}

	/** This method is called once the client receives a chat message. **/
	public boolean onRecieveChat(final Packet3Chat c) {
		return true;
	}

	/** This method is called when the player hits a block. **/
	public void onClickBlock(final int i, final int j, final int k, final int f) {
	}

	/** Called when the block opacity is set */
	public int setBlockOpacity() {
		return 255;
	}

	/** Called when block brightness is set */
	public int setBlockBrightnessInteger(final int i) {
		return i;
	}

	/** Called when block brightness is set */
	public float setBlockBrightness(final float f) {
		return f;
	}

	/** Called when the block is rendered */
	public int onRenderBlockPass(final int i, final Block b) {
		return i;
	}

	/** Called when the block is rendered */
	public boolean onRenderAllFaces(final Block b, final int i, final int j, final int k) {
		return false;
	}

	/** Called when you attempt to break a block is set */
	public float setCurBlockDamage(final Block b, final int i, final int j, final int k, final float l) {
		return b.getPlayerRelativeBlockHardness(AxiomWrapper.getMinecraftObj().thePlayer, AxiomWrapper.getMinecraftObj().thePlayer.worldObj, i, j, k);
	}
	
	public String setModifiedChat(String message){ return message; }

}
