package me.cyadd.axiom.client.managers;

import java.util.HashMap;
import java.util.Map;

import me.cyadd.axiom.client.module.AxiomModule;
import me.cyadd.axiom.client.module.client.AxiomModuleAutoAccept;
import me.cyadd.axiom.client.module.client.AxiomModuleChat;
import me.cyadd.axiom.client.module.client.AxiomModuleFriends;
import me.cyadd.axiom.client.module.client.AxiomModuleHelp;
import me.cyadd.axiom.client.module.combat.AxiomModuleAntiVelocity;
import me.cyadd.axiom.client.module.combat.AxiomModuleAutoSoup;
import me.cyadd.axiom.client.module.combat.AxiomModuleBowAimbot;
import me.cyadd.axiom.client.module.combat.AxiomModuleCriticals;
import me.cyadd.axiom.client.module.combat.AxiomModuleKillAura;
import me.cyadd.axiom.client.module.combat.AxiomModuleTestCrits;
import me.cyadd.axiom.client.module.movement.AxiomModuleFlight;
import me.cyadd.axiom.client.module.movement.AxiomModuleSneak;
import me.cyadd.axiom.client.module.movement.AxiomModuleSpeed;
import me.cyadd.axiom.client.module.movement.AxiomModuleSprint;
import me.cyadd.axiom.client.module.player.AxiomModuleFastPlace;
import me.cyadd.axiom.client.module.player.AxiomModuleFreecam;
import me.cyadd.axiom.client.module.player.AxiomModuleNoFall;
import me.cyadd.axiom.client.module.player.AxiomModuleTracers;
import me.cyadd.axiom.client.module.world.AxiomModuleAutoTool;
import me.cyadd.axiom.client.module.world.AxiomModuleBrightness;
import me.cyadd.axiom.client.module.world.AxiomModuleNegative;
import me.cyadd.axiom.client.module.world.AxiomModuleSpeedyGonzales;
import me.cyadd.axiom.client.module.world.AxiomModuleWallhack;
import me.cyadd.axiom.client.utilities.Logger;


/**
 * This is the module manager. It loads all of the Modules into runtime.
 * 
 * @author Cyadd + Wulf
 * @since 31/05/2013
 */
public class ModuleManager {

	/**
	 * This is the map which holds all of the loaded modules. <br>
	 * 
	 * @usage loadedModules.put("String", new Module());
	 */
	Map<String, AxiomModule> loadedModules = new HashMap<String, AxiomModule>();

	public ModuleManager() {
		this.loadModules();
		Logger.registerLogEventSystem(Logger.INFO, this.loadedModules.size() + " Module(s) loaded.");
		Logger.registerLogEventSystem(Logger.INFO, this.getClass().getSimpleName() + " has been loaded.");
	}

	/**
	 * This is the method that actually loads all of the commands. Useing the
	 * hashmap.
	 */
	public void loadModules() {
		this.loadedModules.put("Flight", new AxiomModuleFlight());
		this.loadedModules.put("Sprint", new AxiomModuleSprint());
		this.loadedModules.put("Help", new AxiomModuleHelp());
		this.loadedModules.put("Chat", new AxiomModuleChat());
		this.loadedModules.put("NoFall", new AxiomModuleNoFall());
		this.loadedModules.put("AutoSoup", new AxiomModuleAutoSoup());
		this.loadedModules.put("Sneak", new AxiomModuleSneak());
		this.loadedModules.put("Criticals", new AxiomModuleCriticals());
		this.loadedModules.put("Friends", new AxiomModuleFriends());
		this.loadedModules.put("AutoAccept", new AxiomModuleAutoAccept());
		this.loadedModules.put("AutoTool", new AxiomModuleAutoTool());
		this.loadedModules.put("Freecam", new AxiomModuleFreecam());
		this.loadedModules.put("KillAura", new AxiomModuleKillAura());
		this.loadedModules.put("Wallhack", new AxiomModuleWallhack());
		this.loadedModules.put("BowAimbot",  new AxiomModuleBowAimbot());
		this.loadedModules.put("SpeedyGonzales", new AxiomModuleSpeedyGonzales());
		this.loadedModules.put("AntiVelocity", new AxiomModuleAntiVelocity());
		this.loadedModules.put("FastPlace", new AxiomModuleFastPlace());
		this.loadedModules.put("Speed", new AxiomModuleSpeed());
		this.loadedModules.put("Brightness", new AxiomModuleBrightness());
		this.loadedModules.put("TestCrits", new AxiomModuleTestCrits());
		this.loadedModules.put("Tracers", new AxiomModuleTracers());
		this.loadedModules.put("Negative", new AxiomModuleNegative());
	}

	/**
	 * A getter for the loadedModules.
	 * 
	 * @return
	 */
	public Map<String, AxiomModule> getLoadedModules() {
		return this.loadedModules;
	}

	/**
	 * This method is used to get an individual Module class from just the name
	 * string.
	 * 
	 * @param s
	 *            The name of the Module
	 * @return The class
	 */
	public AxiomModule getModuleByName(final String s) {
		for (final AxiomModule m : this.loadedModules.values()) {
			if (m.getName().equalsIgnoreCase(s)) {
				return m;
			}
		}
		return null;
	}

}
