package com.nhave.dse.client.render;

import com.nhave.dse.Reference;
import com.nhave.dse.client.models.ModelMotorboat;
import com.nhave.dse.entity.EntityMotorboat;
import com.nhave.dse.items.ItemShader;

import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMotorboat extends Render<EntityMotorboat>
{
	private static ResourceLocation texture = new ResourceLocation(Reference.MODID + ":textures/models/motorboat.png");
	/** instance of ModelBoat for rendering */
	protected ModelMotorboat modelBoat = new ModelMotorboat();

	public RenderMotorboat(RenderManager renderManagerIn)
	{
		super(renderManagerIn);
		
		this.shadowSize = 0.5F;
	}
	
	public void doRender(EntityMotorboat entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(entity, entityYaw, partialTicks);
        this.bindEntityTexture(entity);
        
        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        
        this.modelBoat.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        if (entity.hasPaddles()) this.modelBoat.renderPaddles(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        
        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public void setupRotation(EntityMotorboat boat, float p_188311_2_, float p_188311_3_)
    {
        GlStateManager.rotate(180.0F - p_188311_2_, 0.0F, 1.0F, 0.0F);
        float f = (float)boat.getTimeSinceHit() - p_188311_3_;
        float f1 = boat.getDamageTaken() - p_188311_3_;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f > 0.0F)
        {
            GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * (float)boat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }
        
        if (boat.isBoosting)
		{
			GlStateManager.rotate(3, 1, 0, 0);
		}
        
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
    }

    public void setupTranslation(double p_188309_1_, double p_188309_3_, double p_188309_5_)
    {
        GlStateManager.translate((float)p_188309_1_, (float)p_188309_3_ + 0.375F, (float)p_188309_5_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMotorboat entity)
    {
    	ItemStack stack = entity.getShader();
        return stack.getItem() instanceof ItemShader && ((ItemShader) stack.getItem()).getShader(stack) != null ? ((ItemShader) stack.getItem()).getShader(stack).getResourceLocation("boat", "textures/models/", ".png") : texture;
    }

    public boolean isMultipass()
    {
        return true;
    }

    public void renderMultipass(EntityMotorboat p_188300_1_, double p_188300_2_, double p_188300_4_, double p_188300_6_, float p_188300_8_, float p_188300_9_)
    {
        GlStateManager.pushMatrix();
        this.setupTranslation(p_188300_2_, p_188300_4_, p_188300_6_);
        this.setupRotation(p_188300_1_, p_188300_8_, p_188300_9_);
        this.bindEntityTexture(p_188300_1_);
        ((IMultipassModel)this.modelBoat).renderMultipass(p_188300_1_, p_188300_9_, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }
}