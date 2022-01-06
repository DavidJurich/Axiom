package me.cyadd.axiom.client.module.world;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

import org.lwjgl.opengl.GL11;

public class AxiomModuleNegative extends AxiomModule {

	public AxiomModuleNegative() {
		super("Negative", EnumModuleType.WORLD);
		this.setDescription("Negative mode.");
	}

	@Override
	public void onEnable() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glLogicOp(GL11.GL_INVERT);
	}
	
	@Override
	public void onDisable() {
		GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glDisable(GL11.GL_BLEND);
	}

}
