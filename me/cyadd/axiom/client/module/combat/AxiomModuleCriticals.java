package me.cyadd.axiom.client.module.combat;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.Potion;

public class AxiomModuleCriticals extends AxiomModule {

	public AxiomModuleCriticals() {
		super("Criticals", EnumModuleType.COMBAT);
		this.setDescription("Forces the player to jump to create criticals.");
	}

	@Override
	protected void onEnable() {
	}

	@Override
	public void preMotionUpdate() {
		if (this.mc.thePlayer.onGround && !this.mc.thePlayer.isPotionActive(Potion.blindness) && !this.mc.thePlayer.isOnLadder() && !this.mc.thePlayer.isInWater()) {
			this.mc.thePlayer.motionY += 0.2f;
			this.mc.thePlayer.fallDistance = 0.46415937f;
			this.mc.thePlayer.onGround = false;
		}
	}

	@Override
	protected void onDisable() {
	}

}
