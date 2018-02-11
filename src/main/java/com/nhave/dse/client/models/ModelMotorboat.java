package com.nhave.dse.client.models;

import com.nhave.dse.entity.EntityMotorboat;

import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMotorboat extends ModelBase implements IMultipassModel
{
	public ModelRenderer[] boatSides = new ModelRenderer[5];
    public ModelRenderer[] paddles = new ModelRenderer[2];
    /** Part of the model rendered to make it seem like there's no water in the boat */
    public ModelRenderer noWater;
    private final int patchList = GLAllocation.generateDisplayLists(1);
    public ModelRenderer propeller;
    public ModelRenderer propeller1;
    public ModelRenderer engine;
    public ModelRenderer chest;

    public ModelMotorboat()
    {
        this.boatSides[0] = (new ModelRenderer(this, 0, 0)).setTextureSize(128, 64);
        this.boatSides[1] = (new ModelRenderer(this, 0, 19)).setTextureSize(128, 64);
        this.boatSides[2] = (new ModelRenderer(this, 0, 27)).setTextureSize(128, 64);
        this.boatSides[3] = (new ModelRenderer(this, 0, 35)).setTextureSize(128, 64);
        this.boatSides[4] = (new ModelRenderer(this, 0, 43)).setTextureSize(128, 64);
        int i = 32;
        int j = 6;
        int k = 20;
        int l = 4;
        int i1 = 28;
        this.boatSides[0].addBox(-14.0F, -9.0F, -3.0F, 28, 16, 3, 0.0F);
        this.boatSides[0].setRotationPoint(0.0F, 3.0F, 1.0F);
        this.boatSides[1].addBox(-13.0F, -7.0F, -1.0F, 18, 6, 2, 0.0F);
        this.boatSides[1].setRotationPoint(-15.0F, 4.0F, 4.0F);
        this.boatSides[2].addBox(-8.0F, -7.0F, -1.0F, 16, 6, 2, 0.0F);
        this.boatSides[2].setRotationPoint(15.0F, 4.0F, 0.0F);
        this.boatSides[3].addBox(-14.0F, -7.0F, -1.0F, 28, 6, 2, 0.0F);
        this.boatSides[3].setRotationPoint(0.0F, 4.0F, -9.0F);
        this.boatSides[4].addBox(-14.0F, -7.0F, -1.0F, 28, 6, 2, 0.0F);
        this.boatSides[4].setRotationPoint(0.0F, 4.0F, 9.0F);
        this.boatSides[0].rotateAngleX = ((float)Math.PI / 2F);
        this.boatSides[1].rotateAngleY = ((float)Math.PI * 3F / 2F);
        this.boatSides[2].rotateAngleY = ((float)Math.PI / 2F);
        this.boatSides[3].rotateAngleY = (float)Math.PI;
        this.paddles[0] = this.makePaddle(true);
        this.paddles[0].setRotationPoint(3.0F, -5.0F, 9.0F);
        this.paddles[1] = this.makePaddle(false);
        this.paddles[1].setRotationPoint(3.0F, -5.0F, -9.0F);
        this.paddles[1].rotateAngleY = (float)Math.PI;
        this.paddles[0].rotateAngleZ = 0.19634955F;
        this.paddles[1].rotateAngleZ = 0.19634955F;
        this.noWater = (new ModelRenderer(this, 0, 0)).setTextureSize(128, 64);
        this.noWater.addBox(-14.0F, -9.0F, -3.0F, 28, 16, 3, 0.0F);
        this.noWater.setRotationPoint(0.0F, -3.0F, 1.0F);
        this.noWater.rotateAngleX = ((float)Math.PI / 2F);
        
        this.propeller = makePropeller(-18F, 5F, -5F);
        this.propeller1 = makePropeller(-18F, 5F, 5F);
        this.engine = makeEngine();
    	this.chest = new ModelRenderer(this, 62, 47).setTextureSize(128, 64);
    	this.chest.addBox(-2F, -1F, -6F, 6, 5, 12, 0.0F);
    	this.chest.setRotationPoint(-9F, -1F, 0F);
    }
    
    public ModelRenderer makePropeller(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn)
    {
    	ModelRenderer propeller = new ModelRenderer(this, 118, 0).setTextureSize(128, 64);
     	propeller.addBox(-1F, -1F, -1F, 3, 2, 2, 0.0F);
     	propeller.setRotationPoint(rotationPointXIn, rotationPointYIn, rotationPointZIn);
     	
    	ModelRenderer propeller1 = new ModelRenderer(this, 122, 4).setTextureSize(128, 64);
     	propeller1.addBox(0F, 0F, -1F, 1, 4, 2, 0.0F);
     	propeller1.rotateAngleY = (float) Math.toRadians(15);
     	propeller.addChild(propeller1);
     	
     	ModelRenderer propeller2B = new ModelRenderer(this, 122, 4).setTextureSize(128, 64);
     	propeller.addChild(propeller2B);
     	propeller2B.rotateAngleX = (float) Math.toRadians(360F / 3F);
     	
     	ModelRenderer propeller2 = new ModelRenderer(this, 122, 4).setTextureSize(128, 64);
     	propeller2.addBox(0F, 0F, -1F, 1, 4, 2, 0.0F);
     	propeller2.rotateAngleY = (float) Math.toRadians(15);
     	propeller2B.addChild(propeller2);
 		
     	ModelRenderer propeller3B = new ModelRenderer(this, 122, 4).setTextureSize(128, 64);
     	propeller.addChild(propeller3B);
     	propeller3B.rotateAngleX = (float) Math.toRadians(2 * 360F / 3F);
     	
     	ModelRenderer propeller3 = new ModelRenderer(this, 122, 4).setTextureSize(128, 64);
     	propeller3.addBox(0F, 0F, -1F, 1, 4, 2, 0.0F);
     	propeller3.rotateAngleY = (float) Math.toRadians(15);
     	propeller3B.addChild(propeller3);
     	
     	return propeller;
    }
    
    public ModelRenderer makeEngine()
    {
    	ModelRenderer engine = new ModelRenderer(this, 98, 46).setTextureSize(128, 64);
    	engine.addBox(-1F, -2F, -6F, 3, 6, 12, 0.0F);
    	engine.setRotationPoint(-13F, -2F, 0F);
    	
    	ModelRenderer engine1 = new ModelRenderer(this, 98, 30).setTextureSize(128, 64);
    	engine1.addBox(-2F, -1F, -6F, 3, 4, 12, 0.0F);
    	engine1.setRotationPoint(-2F, -1F, 0F);
    	engine.addChild(engine1);
    	
    	ModelRenderer engine2 = new ModelRenderer(this, 120, 16).setTextureSize(128, 64);
    	engine2.addBox(-1.5F, -3F, -1F, 2, 6, 2, 0.0F);
    	engine2.setRotationPoint(0F, 5F, 0);
    	engine1.addChild(engine2);
    	
    	ModelRenderer engine3 = new ModelRenderer(this, 100, 2).setTextureSize(128, 64);
    	engine3.addBox(-1F, -1F, -6F, 2, 2, 12, 0.0F);
    	engine3.setRotationPoint(0F, 3F, 0);
    	engine2.addChild(engine3);

     	return engine;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        EntityMotorboat entityboat = (EntityMotorboat)entityIn;
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        for (int i = 0; i < 5; ++i)
        {
            this.boatSides[i].render(scale);
        }
        
        float f = ((EntityMotorboat) entityIn).getRowingTime(0, limbSwing) * 100.0F;
        this.propeller.rotateAngleX = entityboat.isUsingPaddles() ? 0 : f;
        this.propeller.render(scale);
        this.propeller1.rotateAngleX = entityboat.isUsingPaddles() ? 0 : f;
        this.propeller1.render(scale);
        
        this.engine.render(scale);
        
        if (entityboat.hasStorage()) this.chest.render(scale);
    }
    
    public void renderPaddle(EntityMotorboat boat, int paddle, float scale, float limbSwing, boolean rowing)
    {
    	if (rowing)
    	{
	        float f1 = boat.getRowingTime(paddle, limbSwing);
	        ModelRenderer modelrenderer = this.paddles[paddle];
	        modelrenderer.rotateAngleX = (float)MathHelper.clampedLerp(-1.0471975803375244D, -0.2617993950843811D, (double)((MathHelper.sin(-f1) + 1.0F) / 2.0F));
	        modelrenderer.rotateAngleY = (float)MathHelper.clampedLerp(-(Math.PI / 4D), (Math.PI / 4D), (double)((MathHelper.sin(-f1 + 1.0F) + 1.0F) / 2.0F));
	       
	        modelrenderer.setRotationPoint(3.0F, -5.0F, 9.0F);

	        
	        if (paddle == 1)
	        {
	        	modelrenderer.setRotationPoint(3.0F, -5.0F, -9.0F);
	            modelrenderer.rotateAngleY = (float)Math.PI - modelrenderer.rotateAngleY;
	        }
	
	        modelrenderer.render(scale);
    	}
    	else
    	{
	        ModelRenderer modelrenderer = this.paddles[paddle];
	        modelrenderer.rotateAngleX = (float) Math.toRadians(-25);
	        modelrenderer.rotateAngleY = (float) Math.toRadians(-90);
	
	        modelrenderer.setRotationPoint(3.0F, -2.0F, 11.0F);

	        if (paddle == 1)
	        {
	        	modelrenderer.setRotationPoint(3.0F, -2.0F, -11.0F);
	            modelrenderer.rotateAngleY = (float)Math.PI - modelrenderer.rotateAngleY;
	        }
	
	        modelrenderer.render(scale);
    	}
    }
    
    public void renderPaddles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	EntityMotorboat entityboat = (EntityMotorboat)entityIn;
        this.renderPaddle(entityboat, 0, scale, limbSwing, entityboat.isUsingPaddles());
        this.renderPaddle(entityboat, 1, scale, limbSwing, entityboat.isUsingPaddles());
    }
    
    public void renderMultipass(Entity p_187054_1_, float p_187054_2_, float p_187054_3_, float p_187054_4_, float p_187054_5_, float p_187054_6_, float scale)
    {
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.colorMask(false, false, false, false);
        this.noWater.render(scale);
        GlStateManager.colorMask(true, true, true, true);
    }

    protected ModelRenderer makePaddle(boolean p_187056_1_)
    {
        ModelRenderer modelrenderer = (new ModelRenderer(this, 62, p_187056_1_ ? 0 : 20)).setTextureSize(128, 64);
        int i = 20;
        int j = 7;
        int k = 6;
        float f = -5.0F;
        modelrenderer.addBox(-1.0F, 0.0F, -5.0F, 2, 2, 18);
        modelrenderer.addBox(p_187056_1_ ? -1.001F : 0.001F, -3.0F, 8.0F, 1, 6, 7);
        return modelrenderer;
    }
}