package me.cyadd.axiom.client;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ScaledResolution;

/**
 * This is the wrapper class, it creates static objects for certain classes
 * within the Minecraft package.
 * 
 * 	888       888                                                    
 *	888   o   888                                                    
 *	888  d8b  888                                                    
 *	888 d888b 888 888d888 8888b.  88888b.  88888b.   .d88b.  888d888 
 *	888d88888b888 888P"      "88b 888 "88b 888 "88b d8P  Y8b 888P"   
 *	88888P Y88888 888    .d888888 888  888 888  888 88888888 888     
 *	8888P   Y8888 888    888  888 888 d88P 888 d88P Y8b.     888     
 *	888P     Y888 888    "Y888888 88888P"  88888P"   "Y8888  888     
 *                             	  888      888                       
 *                                888      888                       
 *                                888      888                       
 * 
 * @author Cyadd and Wulf
 * @since 31/05/2013
 */
public class AxiomWrapper {

	/**
	 * A static reference to the Minecraft object.
	 * 
	 * @see {@link net.minecraft.client.Minecraft}
	 * @return Minecraft.java
	 */
	public final static Minecraft getMinecraftObj() {
		return Minecraft.getMinecraft();
	}

	/**
	 * This is the static reference to 'ScaledResolution' object. Which is used
	 * for text positioning, GUI positioning and others.
	 * 
	 * @see {@link net.minecraft.src.ScaledResolution}
	 * @return ScaledResolution.java
	 */
	public final static ScaledResolution getResolution() {
		return new ScaledResolution(AxiomWrapper.getMinecraftObj().gameSettings, AxiomWrapper.getMinecraftObj().displayWidth, AxiomWrapper.getMinecraftObj().displayHeight);
	}
}
