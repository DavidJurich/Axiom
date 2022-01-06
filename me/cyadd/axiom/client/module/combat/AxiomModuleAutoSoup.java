package me.cyadd.axiom.client.module.combat;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;

import org.lwjgl.input.Keyboard;


public class AxiomModuleAutoSoup extends AxiomModule {

	private int invSlot;
	private ItemStack is;
	private int stackSlot = -1;
	private long currentMS;
	private long lastStack = -1;
	private final long stackThreshhold = 150L;

	public AxiomModuleAutoSoup() {
		super("AutoSoup", 0xF781F3, EnumModuleType.PLAYER, Keyboard.KEY_I);
		this.setDescription("Automatically eats soup once your health drops below a certain level.");
	}

	@Override
	public void preMotionUpdate() {
		if (this.mc.thePlayer.getHealth() <= 14) {
			this.getSoup();
		}
	}

	private void getSoup() {
		this.currentMS = System.nanoTime() / 1000000;
		if (this.timeHasPassed(this.stackThreshhold)) {
			this.stackBowl();
			this.lastStack = System.nanoTime() / 1000000;
		}
		if (this.mc.thePlayer.getHealth() > 14) {
			return;
		}
		this.invSlot = -1;
		this.is = null;
		for (int slot = 44; slot >= 9; slot--) {
			this.is = this.getStackAt(slot);
			if (this.is == null) {
				continue;
			}
			if (this.is.itemID == 282) {
				if (slot >= 36 && slot <= 44) {
					final int theSlot = this.invSlot - 36;
					if (this.mc.thePlayer.inventory.currentItem != theSlot) {
						this.mc.thePlayer.inventory.currentItem = slot - 36;

						this.mc.getNetHandler().addToSendQueue(new Packet16BlockItemSwitch(this.mc.thePlayer.inventory.currentItem));
					}
					this.mc.playerController.updateController();
					this.eatHeldItem();
					break;
				}
				this.invSlot = slot;
				break;
			}
		}
		if (this.is == null) {
			return;
		}
		if (this.invSlot != -1) {

			this.mc.playerController.windowClick(0, this.invSlot, 0, 1, this.mc.thePlayer);
			this.mc.playerController.updateController();
		}

	}

	private boolean timeHasPassed(final long threshhold) {
		return this.currentMS - this.lastStack >= threshhold;
	}

	private void eatHeldItem() {
		this.mc.getNetHandler().addToSendQueue(new Packet15Place(-1, -1, -1, 255, this.mc.thePlayer.getCurrentEquippedItem(), 0F, 0F, 0F));
		this.mc.playerController.updateController();
	}

	private ItemStack getStackAt(final int slot) {
		return this.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
	}

	private void stackBowl() {
		for (int slot = 44; slot >= 9; slot--) {
			this.is = this.getStackAt(slot);

			if (this.is == null) {
				if (!(this.stackSlot >= 36 && this.stackSlot <= 44)) {
					this.stackSlot = slot;
				}
				continue;
			}

			if (this.is.itemID == 281) {
				if (this.stackSlot != -1) {
					if (this.is.stackSize >= 64) {
						continue;
					}
					this.mc.playerController.windowClick(0, slot, 0, 0, this.mc.thePlayer);
					this.mc.playerController.windowClick(0, this.stackSlot, 0, 0, this.mc.thePlayer);
					this.mc.playerController.updateController();
					break;
				} else {
					this.stackSlot = slot;
				}
			}
		}
		this.stackSlot = -1;
	}

}
