package me.cyadd.axiom.client.managers;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomWrapper;
import me.cyadd.axiom.client.module.AxiomModule;
import me.cyadd.axiom.client.utilities.Logger;
import net.minecraft.src.Block;
import net.minecraft.src.Packet3Chat;

/**
 * This is a hook class. It hooks the Modules into the runtime environment, and
 * therefore editing/modifying code.This class also includes synchronised
 * methods.
 * 
 * @author Cyadd + Wulf
 * @since 31/05/2013
 */
public class HookManager {

	public HookManager() {
		Logger.registerLogEventSystem(Logger.INFO, this.getClass().getSimpleName() + " has been loaded.");
	}

	/**
	 * This is the onTick() method. It will run if the module is enabled and on
	 * every in-game tick.
	 * 
	 * @see {@link net.minecraft.src.EntityPlayerSP#onLivingUpdate()}
	 */
	public synchronized void onTick() {
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				m.onRunnableTick();
			}
		}
	}

	/**
	 * This is the renderOverlay() method. It renders items onto the Minecraft
	 * screen.
	 * 
	 * @see {@link me.cyadd.axiom.client.gui.screens.AxiomInGameGUI#renderGameOverlay}
	 */
	public synchronized void renderOverlay() {
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				m.renderOverlay();
			}
		}
	}

	/**
	 * This method is called before the motion update is sent.
	 * 
	 * @see {@link net.minecraft.src.EntityClientPlayerMP#onUpdate}
	 */
	public synchronized void preMotionUpdate() {
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				m.preMotionUpdate();
			}
		}
	}

	/**
	 * This method is called after the motion update is sent.
	 * 
	 * @see {@link net.minecraft.src.EntityClientPlayerMP#onUpdate}
	 */
	public synchronized void postMotionUpdate() {
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				m.postMotionUpdate();
			}
		}
	}
	
	/**
	 * This method is called to render and object.
	 * 
	 * @see {@link net.minecraft.src.EntityRenderer#renderWorld()}
	 */
	public synchronized void renderObject() {
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				m.onRenderObject();
			}
		}
	}

	/**
	 * This method is called to change how the chat is rendered.
	 * 
	 * @see {@link net.minecraft.src.GuiNewChat#drawChat()}
	 * @see {@link me.cyadd.axiom.client.gui.screens.AxiomChat#drawChat()}
	 * @param s
	 *            String
	 * @return
	 */
	public synchronized String onChatLineRender(final String s) {
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				final String msg = m.onRenderChat(s);
				if (!s.equalsIgnoreCase(msg)) {
					return msg;
				}
			}
		}
		return s;
	}

	/**
	 * This method is called when the client receives a chat message.
	 * 
	 * @see {@link net.minecraft.src.NetClientHandler#handleChat}
	 * @param c
	 *            String
	 * @return
	 */
	public synchronized boolean onChatMessage(final Packet3Chat c) {
		boolean flag = true;
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				final boolean flag0 = m.onRecieveChat(c);
				if (!flag0) {
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is called when the player clicks a block.
	 * 
	 * @see {@link net.minecraft.src.PlayerControllerMP#clickBlock}
	 * @param i
	 * @param j
	 * @param k
	 * @param f
	 */
	public synchronized void onClickBlock(final int i, final int j, final int k, final int f) {
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				m.onClickBlock(i, j, k, f);
			}
		}
	}
	
	public synchronized float curBlockDamageSet(Block b, int i1, int i2, int i3, float i4) {
		float d = b.getPlayerRelativeBlockHardness(AxiomWrapper.getMinecraftObj().thePlayer, AxiomWrapper.getMinecraftObj().thePlayer.worldObj, i1, i2, i3);
		for (final AxiomModule m : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (m.isEnabled()) {
				d = m.setCurBlockDamage(b, i1, i2, i3, i4);
			}
		}
		return d;
	}

	/**
	 * This method is called to change the block opacity.
	 * 
	 * @return
	 */
	public int onOpacitySet() {
		for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (module.isEnabled()) {
				return module.setBlockOpacity();
			}
		}
		return 255;
	}

	public int onBlockBrightnessIntegerSet(final int i) {
		for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (module.isEnabled()) {
				return module.setBlockBrightnessInteger(i);
			}
		}
		return i;
	}

	public float onBlockBrightnessSet(final float f) {
		for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (module.isEnabled()) {
				return module.setBlockBrightness(f);
			}
		}
		return f;
	}

	public int onBlockRenderPass(final int i, final Block b) {
		for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (module.isEnabled()) {
				return module.onRenderBlockPass(i, b);
			}
		}
		return i;
	}

	public boolean onRenderAllFaces(final Block b, final int i, final int j, final int k) {
		for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (module.isEnabled()) {
				return module.onRenderAllFaces(b, i, j, k);
			}
		}
		return true;
	}

}
