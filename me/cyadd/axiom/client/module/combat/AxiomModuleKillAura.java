package me.cyadd.axiom.client.module.combat;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityDragon;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntityWitch;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;

import org.lwjgl.input.Keyboard;

public class AxiomModuleKillAura extends AxiomModule {

	public long current;
	public long last = -1;
	public boolean attackPlayers = true;
	public boolean attackHostile = true;
	public boolean lockView;

	public static boolean attacking;

	public static Long lastAttack = null, attackDelay = 70L;

	public float prevYaw, prevPitch;

	public double range = 4.2;
	private float fakeYaw;

	public AxiomModuleKillAura() {
		super("Kill Aura", 0xc40000, EnumModuleType.COMBAT, Keyboard.KEY_F);
		this.setCustomSyntax(".killaura <range/speed/lv> <integer>");
		this.setDescription("Attacks players within the vicinity.");
	}

	@Override
	public void onRunnableTick() {
		if (this.mc.thePlayer.swingProgress > 0) {
			this.getBestWeapon();
		}
		if (this.shouldAttack()) {
			this.attack();
			AxiomModuleKillAura.lastAttack = this.sysTime();
		}
	}

	private void attack() {
		final Entity attack = this.getClosestEntity();

		if (attack != null && this.isAttackable(attack)) {
			if (this.lockView) {
				this.faceEnt(attack);
			}
			AxiomModuleKillAura.attacking = true;
			this.mc.playerController.attackEntity(this.mc.thePlayer, attack);
			this.mc.thePlayer.swingItem();
			AxiomModuleKillAura.attacking = false;
		}
	}

	public Entity getClosestEntity() {
		Entity a = null;

		for (final Object o : this.mc.theWorld.getLoadedEntityList()) {
			final Entity b = (Entity) o;

			if (b != null) {

				final float distanceB = this.mc.thePlayer.getDistanceToEntity(b);

				final boolean canSee = this.mc.thePlayer.canEntityBeSeen(b);
				final boolean withinReach = distanceB <= this.range;
				final boolean isNotMe = b != this.mc.thePlayer;
				final boolean isLiving = b instanceof EntityLiving;
				final boolean isAlive = b.isDead != true;

				if (canSee && withinReach && isNotMe && isLiving && isAlive) {
					boolean checkUser = false;
					if (b instanceof EntityPlayer) {
					} else {
						checkUser = false;
					}
					if (!checkUser) {
						if (a != null) {
							final float distanceA = this.mc.thePlayer.getDistanceToEntity(a);
							if (distanceA > distanceB) {
								a = b;
							}
						} else {
							a = b;
						}
					}
				}
			}
		}
		return a;
	}

	public void faceEnt(final Entity ent) {
		final double xD = ent.posX - this.mc.thePlayer.posX;
		final double zD = ent.posZ - this.mc.thePlayer.posZ;
		final double yD = (ent.posY - this.mc.thePlayer.posY) + (ent.height * 0.8);
		final double yaw = Math.atan2(zD, xD) * 180 / Math.PI - 90;
		final double zDxDSqrt = Math.sqrt(zD * zD + xD * xD);
		final double pitch = -Math.toDegrees(Math.atan(yD / zDxDSqrt));
		final int delta = 180;
		this.mc.thePlayer.rotationPitch = (float) pitch;
		this.fakeYaw = this.updateRotation(this.fakeYaw, (float) yaw, delta);
	}
	
	private float updateRotation(final float par1, final float par2, final float par3) {
		float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
		if (var4 > par3) {
			var4 = par3;
		}
		if (var4 < -par3) {
			var4 = -par3;
		}
		return par1 + var4;
	}

	public boolean isAttackable(final Entity e) {
		if (!(e instanceof EntityLiving)) {
			return false;
		}
		if (e instanceof EntityPlayer) {
			this.getNearestPlayer();
		}
		return (e.getDistanceToEntity(this.mc.thePlayer) <= this.range && (this.isPlayer(e) || this.isHostile(e)));
	}

	private boolean isPlayer(final Entity e) {
		if (!(e instanceof EntityPlayer)) {
			return false;
		}
		final EntityPlayer player = (EntityPlayer) e;
		if (!Axiom.getAxiom().getFriendManager().isFriend(player.username)) {
			return true;
		}
		return false;
	}

	private boolean isHostile(final Entity e) {
		return (e instanceof EntityMob || e instanceof EntityDragon || e instanceof EntityGhast || e instanceof EntitySlime || e instanceof EntityWitch);
	}

	public boolean shouldAttack() {
		return AxiomModuleKillAura.lastAttack == null || this.sysTime() >= AxiomModuleKillAura.lastAttack + AxiomModuleKillAura.attackDelay;
	}

	public long sysTime() {
		return System.nanoTime() / 1000000;
	}

	private void getBestWeapon() {
		float damageModifier = 1;
		int newItem = -1;
		for (int slot = 0; slot < 9; slot++) {
			final ItemStack stack = this.mc.thePlayer.inventory.mainInventory[slot];
			if (stack == null) {
				continue;
			}
			final float dmg = stack.getDamageVsEntity(this.mc.thePlayer);
			if (dmg > damageModifier) {
				newItem = slot;
				damageModifier = dmg;
			}
		}
		if (newItem > -1) {
			this.mc.thePlayer.inventory.currentItem = newItem;
		}
	}

	public EntityPlayer getNearestPlayer() {
		EntityPlayer nearest = null;
		if (this.mc.theWorld == null) {
			return null;
		} else {
			for (final Object o : this.mc.theWorld.playerEntities) {
				if (o != null && !(o instanceof EntityPlayerSP)) {
					final EntityPlayer e = (EntityPlayer) o;
					if (!e.isDead) {
						if (nearest == null) {
							nearest = e;
						} else if (nearest.getDistanceToEntity(this.mc.thePlayer) > e.getDistanceToEntity(this.mc.thePlayer)) {
							nearest = e;
						}
					}
				}
			}
			return nearest;
		}
	}

	@Override
	public void runCommand(final String s) {
		if (s.equalsIgnoreCase(AxiomInput.prefix + "killaura lv")) {
			this.lockView = !this.lockView;
			AxiomInput.addChat(this.lockView ? "Lock view has been toggled on." : "Lock view has been toggled off.");
		}

		if (s.startsWith(AxiomInput.prefix + "killaura")) {
			final String[] split = s.split(" ");
			try {
				final float speed = Float.parseFloat(split[1]);
				AxiomModuleKillAura.setAttackDelay((long) speed);
				AxiomInput.addChat("KillAura has been set to attack <" + speed + "> time(s) per second.");
			} catch (final Exception e) {
				AxiomInput.addChat("Usage: " + this.getCustomSyntax());
			}

			try {
				final float range = Float.parseFloat(split[1]);
				this.setRange(range);
				AxiomInput.addChat("KillAura has been set to reach up to <" + range + "> block(s).");
			} catch (final Exception e) {
				// AxiomInput.addChat("Usage: " + this.getCustomSyntax());
			}
		}
	}

	/**
	 * @param range
	 *            the range to set
	 */
	public synchronized final void setRange(final double range) {
		this.range = range;
	}

	/**
	 * @param attackDelay
	 *            the attackDelay to set
	 */
	public static synchronized final void setAttackDelay(final Long attackDelay) {
		AxiomModuleKillAura.attackDelay = attackDelay;
	}

}
