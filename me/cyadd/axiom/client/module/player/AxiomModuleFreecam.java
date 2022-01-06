package me.cyadd.axiom.client.module.player;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.WorldClient;

import org.lwjgl.input.Keyboard;


public class AxiomModuleFreecam extends AxiomModule {

	public static Location locationHelper;

	public AxiomModuleFreecam() {
		super("Freecam", 0xff6677, EnumModuleType.MOVEMENT, Keyboard.KEY_G);
		this.setDescription("Create a free entity.");
	}

	@Override
	public void preMotionUpdate() {
		if (Axiom.getAxiom().getModuleManager().getModuleByName("Flight").isEnabled() && Axiom.getAxiom().getModuleManager().getModuleByName("Freecam").isEnabled()) {
			this.mc.thePlayer.noClip = true;
		} else {
			this.mc.thePlayer.noClip = false;
		}
	}

	@Override
	public void onToggle() {
		super.onToggle();
		this.createPlayer();
	}

	public void createPlayer() {
		final EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
		if (this.mc.theWorld instanceof WorldClient) {
			if (this.isEnabled()) {
				entityPlayerSP.noClip = true;
				AxiomModuleFreecam.locationHelper = new Location(entityPlayerSP);
				final EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.username);
				entityotherplayermp.setPositionAndRotation(AxiomModuleFreecam.locationHelper.posX, AxiomModuleFreecam.locationHelper.posY - entityPlayerSP.yOffset, AxiomModuleFreecam.locationHelper.posZ, AxiomModuleFreecam.locationHelper.rotationYaw, AxiomModuleFreecam.locationHelper.rotationPitch);
				entityotherplayermp.inventory.copyInventory(this.mc.thePlayer.inventory);
				entityotherplayermp.setSneaking(entityotherplayermp.isSneaking());
				final WorldClient worldclient1 = this.mc.theWorld;
				worldclient1.addEntityToWorld(-1, entityotherplayermp);
			} else {
				entityPlayerSP.noClip = false;
				final WorldClient worldclient = this.mc.theWorld;
				worldclient.removeEntityFromWorld(-1);
				entityPlayerSP.setPositionAndRotation(AxiomModuleFreecam.locationHelper.posX, AxiomModuleFreecam.locationHelper.posY, AxiomModuleFreecam.locationHelper.posZ, AxiomModuleFreecam.locationHelper.rotationYaw, AxiomModuleFreecam.locationHelper.rotationPitch);
			}
		}
	}

	class Location {

		public double posX;
		public double posY;
		public double posZ;
		public float rotationYaw;
		public float rotationPitch;

		public String name;

		@Override
		public Location clone() {
			return new Location(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.name);
		}

		public Location(final Entity entity) {
			this(entity, "");
		}

		public Location(final Entity entity, final String s) {
			this(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch, s);
		}

		public Location() {
			this(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, "");
		}

		public Location(final double d, final double d1, final double d2, final String s) {
			this(d, d1, d2, 0.0F, 0.0F, s);
		}

		public Location(final double d, final double d1, final double d2) {
			this(d, d1, d2, 0.0F, 0.0F, "");
		}

		public Location(final double d, final double d1, final double d2, final float f, final float f1) {
			this(d, d1, d2, f, f1, "");
		}

		public Location(final double d, final double d1, final double d2, final float f, final float f1, final String s) {
			this.posX = d;
			this.posY = d1;
			this.posZ = d2;
			this.rotationYaw = f;
			this.rotationPitch = f1;
			this.name = s;
		}

		public double distance(final Location Location) {
			return Math.sqrt(this.distanceSquare(Location));
		}

		public double distanceSquare(final Location Location) {
			final double d = Location.posX - this.posX;
			final double d1 = Location.posY - this.posY;
			final double d2 = Location.posZ - this.posZ;
			return d * d + d1 * d1 + d2 * d2;
		}

		public double distance2D(final Location Location) {
			return Math.sqrt(this.distance2DSquare(Location));
		}

		public double distance2DSquare(final Location Location) {
			final double d = Location.posX - this.posX;
			final double d1 = Location.posZ - this.posZ;
			return d * d + d1 * d1;
		}

		public double distanceY(final Location Location) {
			return Location.posY - this.posY;
		}

		public Location(final String s) throws Exception {
			final String as[] = s.split(";", 6);

			if (as.length != 6) {
				throw new Exception("Invalid line!");
			} else {
				this.name = as[5];
				this.posX = Double.parseDouble(as[0]);
				this.posY = Double.parseDouble(as[1]);
				this.posZ = Double.parseDouble(as[2]);
				this.rotationYaw = Float.parseFloat(as[3]);
				this.rotationPitch = Float.parseFloat(as[4]);
				return;
			}
		}

		public String export() {
			return (new StringBuilder()).append(this.posX).append(";").append(this.posY).append(";").append(this.posZ).append(";").append(this.rotationYaw).append(";").append(this.rotationPitch).append(";").append(this.name).toString();
		}
	}
}
