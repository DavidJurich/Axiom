package me.cyadd.axiom.client.module.player;

import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;

import org.lwjgl.opengl.GL11;

public class AxiomModuleTracers extends AxiomModule {

	private boolean shouldTrace = true;

	public AxiomModuleTracers() {
		super("Tracers", EnumModuleType.PLAYER);
		this.setDescription("Draws a line towards an entity.");
	}

	@Override
	public void onRenderObject() {
		this.drawTracerLine();
	}

	public void drawTracerLine() {
		//GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		for (final Object o : this.mc.theWorld.loadedEntityList) {
			if (o instanceof EntityLiving) {
				final EntityLiving entityLiving = (EntityLiving) o;
				if (entityLiving != this.mc.thePlayer) {
					final double x = entityLiving.lastTickPosX;
					final double y = entityLiving.lastTickPosY;
					final double z = entityLiving.lastTickPosZ;
					final double renderX = RenderManager.renderPosX;
					final double renderY = RenderManager.renderPosY;
					final double renderZ = RenderManager.renderPosZ;
					final double posX = renderX - x;
					final double posY = renderY - y;
					final double posZ = renderZ - z;
					GL11.glBegin(GL11.GL_LINES);
					GL11.glLineWidth(1.5F);
					if (!(entityLiving instanceof EntityPlayer)) {
						this.shouldTrace = true;
						if (this.mc.thePlayer.getDistanceToEntity(entityLiving) <= 30.0F) {
							this.shouldTrace = true;
						}
					}
					if (this.shouldTrace) {
						this.mc.thePlayer.getDistanceToEntity(entityLiving);
						GL11.glColor3f(1.0F, 0F, 0F);
						GL11.glBegin(GL11.GL_LINES);
						GL11.glVertex3d(0.0D, 0.0D, 0.0D);
						GL11.glVertex3d(-posX, -posY, -posZ);
						GL11.glEnd();
					}
				}
			}
			//GL11.glEnable(GL11.GL_DEPTH_TEST);
			//GL11.glDepthMask(true);
			//GL11.glDisable(GL11.GL_BLEND);
			//GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
		}
	}

}
