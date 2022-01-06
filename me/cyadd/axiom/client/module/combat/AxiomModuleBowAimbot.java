package me.cyadd.axiom.client.module.combat;

// Gnexela's code! Kudos to him, 100% skidded!

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AxiomModuleBowAimbot extends AxiomModule {

	public AxiomModuleBowAimbot() {
		super("BowAimbot", 0xffb200ff, EnumModuleType.COMBAT, Keyboard.KEY_K);
		this.setDescription("It's an aimbot... FOR YOUR BOW!");
	}

	private EntityLiving target;

	private final double gravityconstant = 0.044D;

	@Override
	public void onRunnableTick() {
		if (this.mc.thePlayer.getCurrentEquippedItem() != null) {
			if (this.mc.currentScreen == null) {
				if (this.mc.thePlayer.getCurrentEquippedItem().getItem().equals(Item.bow)) {
					if (Mouse.isButtonDown(1)) {
						this.target = this.getClosestEntity();
						if (this.target == null) {
							return;
						}
						final float var1 = this.getPitchA(this.target);
						this.mc.thePlayer.faceEntity(this.target, 100, 100);
						this.mc.thePlayer.rotationPitch = this.mc.thePlayer.rotationPitch + var1;

						this.mc.thePlayer.rotationYaw = this.getYaw(this.target);
					}
				}
			}
		}

	}

	private float getYaw(final Entity e) {
		final double xDistance = (e.posX - this.mc.thePlayer.posX);
		final double zDistance = (e.posZ - this.mc.thePlayer.posZ);
		return (float) ((Math.atan2(zDistance, xDistance) * 180D) / Math.PI) - 90F;
	}

	public float getPitchA(final EntityLiving t) {
		final double xDiff = t.posX - this.mc.thePlayer.posX;
		final double zDiff = t.posZ - this.mc.thePlayer.posZ;

		final double dist = -MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
		final double x = dist;
		double y = -MathHelper.sqrt_double((t.posY - this.mc.thePlayer.posY) * (t.posY - this.mc.thePlayer.posY));

		y = -0.51;
		final double vSq = this.getVelocity() * this.getVelocity();
		final double vQb = vSq * vSq;
		final double g = this.gravityconstant;

		float pitch = 0;
		final float topHalf = (float) (vSq - MathHelper.sqrt_double(vQb - g * (((g * (x * x)) + 2 * (y * vSq)))));
		final float bottomHalf = (float) (g * x);
		final float var1 = topHalf / bottomHalf;
		pitch = (float) Math.atan(var1);
		return (pitch * 100);
	}

	private double getVelocity() {
		final int var6 = this.mc.thePlayer.getItemInUse().getMaxItemUseDuration() - this.mc.thePlayer.getItemInUseCount();
		float var7 = var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

		if (var7 > 1.0F) {
			var7 = 1.0F;
		}
		return var7 * 3.2;
	}

	private EntityLiving getClosestEntity() {
		EntityLiving closest = null;
		double distance = 0;

		for (final Object o : this.mc.theWorld.loadedEntityList) {
			if (o instanceof EntityLiving) {
				final EntityLiving e = (EntityLiving) o;
				if (this.canBeSeen(e)) {
					if (e != this.mc.thePlayer) {
						if ((e.getDistanceToEntity(this.mc.thePlayer) < distance || closest == null) && e.getDistanceToEntity(this.mc.thePlayer) > 2) {
							closest = e;
							distance = e.getDistanceToEntity(this.mc.thePlayer);
						}
					}
				}
			}
		}
		return closest;
	}

	private boolean canBeSeen(final Entity par1) {
		return this.rayTrace(this.mc.theWorld.getWorldVec3Pool().getVecFromPool(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ), this.mc.theWorld.getWorldVec3Pool().getVecFromPool(par1.posX, par1.posY, par1.posZ)) == null;
	}

	private MovingObjectPosition rayTrace(final Vec3 par1, final Vec3 par2) {
		return this.mc.theWorld.rayTraceBlocks_do_do(par1, par2, false, true);
	}

}
