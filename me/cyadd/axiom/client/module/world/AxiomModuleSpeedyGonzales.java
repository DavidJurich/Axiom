package me.cyadd.axiom.client.module.world;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.Block;

import org.lwjgl.input.Keyboard;

public class AxiomModuleSpeedyGonzales extends AxiomModule {

	public AxiomModuleSpeedyGonzales() {
		super("Speedy Gonzales", 0xc40000, EnumModuleType.WORLD, Keyboard.KEY_L);
		this.setDescription("Break blocks faster.");
	}

	@Override
	public float setCurBlockDamage(final Block b, final int i1, final int i2, final int i3, final float i4) {
		final float damage = b.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.theWorld, i1, i2, i3);
		return damage * 4f;
	}

}
