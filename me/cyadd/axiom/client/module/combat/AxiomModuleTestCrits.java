package me.cyadd.axiom.client.module.combat;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.Material;

public class AxiomModuleTestCrits extends AxiomModule {

	private boolean onGround = false;
	private boolean tickGround = false;
	private double lastY = -1, fallDistance = -1;

	public AxiomModuleTestCrits() {
		super("TestCrits", EnumModuleType.COMBAT);
		this.setDescription("TESTETSETSTEST");
	}

	@Override
	protected void onDisable() {
		this.fallDistance = -1;
		this.lastY = -1;
		this.onGround = false;
		this.tickGround = false;
	}

	@Override
	public void onRunnableTick() {
		if (this.isSafe()) {
			this.fallDistance = 0D;
		}
		if (this.mc.thePlayer.posY < this.lastY && !this.isSafe()) {
			this.fallDistance += (this.lastY - this.mc.thePlayer.posY);
		}
		this.lastY = this.mc.thePlayer.posY;
		if (this.fallDistance >= 2.0D) {
			this.fallDistance = 0.0D;
			this.tickGround = true;
		}
	}

	@Override
	public void preMotionUpdate() {
		this.onGround = this.mc.thePlayer.onGround;
		this.mc.thePlayer.onGround = false;
		if (this.tickGround) {
			this.mc.thePlayer.onGround = true;
			this.fallDistance = 0D;
			this.tickGround = false;
		}
	}

	@Override
	public void postMotionUpdate() {
		this.mc.thePlayer.onGround = this.onGround;
	}

	private boolean isSafe() {
		return this.mc.thePlayer.isInWater() || this.mc.thePlayer.isInsideOfMaterial(Material.lava) || this.mc.playerController.isInCreativeMode();
	}

}
