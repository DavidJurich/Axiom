package me.cyadd.axiom.client.module.world;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.Block;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemStack;

public class AxiomModuleAutoTool extends AxiomModule {

	public AxiomModuleAutoTool() {
		super("AutoTool", EnumModuleType.WORLD);
		this.setDescription("Selects the best tool for the block being hit.");
	}

	@Override
	public void onClickBlock(final int i, final int j, final int k, final int f) {
		this.autoTool(i, j, k);
	}

	public void autoTool(final int i, final int j, final int k) {
		final int blockID = this.mc.theWorld.getBlockId(i, j, k);
		if (blockID != 0) {
			final EntityClientPlayerMP entity = this.mc.thePlayer;
			float f = 0.1F;
			int currentItem = this.mc.thePlayer.inventory.currentItem;
			for (int loop = 36; loop < 45; ++loop) {
				final ItemStack is = entity.openContainer.getSlot(loop).getStack();
				if (is != null) {
					final float strength = is.getStrVsBlock(Block.blocksList[blockID]);
					if (strength > f) {
						f = strength;
						currentItem = loop - 36;
					}
				}
			}
			this.mc.thePlayer.inventory.currentItem = currentItem;
		}
	}

}
