package com.nhave.dse.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelScubaTankLarge extends ModelBiped
{
    public static final ModelScubaTankLarge ARMOR = new ModelScubaTankLarge(0F, false);
    public static final ModelScubaTankLarge NOARMOR = new ModelScubaTankLarge(1F, true);

    ModelRenderer head;
    ModelRenderer tankMain1;
    ModelRenderer tank1;
    ModelRenderer tank2;
    ModelRenderer tankMain2;
    ModelRenderer tank3;
    ModelRenderer tank4;
    ModelRenderer pipeCenter;
    ModelRenderer pipeLeft;
    ModelRenderer pipeRight;
    
    boolean renderHead;
    
    public ModelScubaTankLarge(float var, boolean renderHead)
    {
    	super(1.0F, 0, 64, 32);
    	
    	this.renderHead = renderHead;
    	
    	this.bipedBody.showModel = true;
        this.bipedRightArm.showModel = true;
        this.bipedLeftArm.showModel = true;
        this.bipedHead.showModel = false;
        this.bipedHeadwear.showModel = false;
        this.bipedRightLeg.showModel = false;
        this.bipedLeftLeg.showModel = false;
        
        if (this.renderHead)
        {
	        head = new ModelRenderer(this, 32, 0);
	        head.addBox(-4F, -7.5F, -4F, 8, 8, 8);
	        head.setRotationPoint(0F, 0F, 0F);
	        head.setTextureSize(64, 32);
	        head.mirror = true;
	        setRotation(head, 0F, 0F, 0F);
        }
        
        tankMain1 = new ModelRenderer(this, 0, 0);
        tankMain1.addBox(1F, 0F+var, 4F-var, 3, 10, 3);
        tankMain1.setRotationPoint(0F, 0F, 0F);
        tankMain1.setTextureSize(64, 32);
        tankMain1.mirror = true;
        setRotation(tankMain1, 0F, 0F, 0F);
        
        tank1 = new ModelRenderer(this, 12, 0);
        tank1.addBox(0F, 1F+var, 4F-var, 5, 8, 3);
        tank1.setRotationPoint(0F, 0F, 0F);
        tank1.setTextureSize(64, 32);
        tank1.mirror = true;
        setRotation(tank1, 0F, 0F, 0F);
        
        tank2 = new ModelRenderer(this, 12, 0);
        tank2.addBox(-8F+var, 1F+var, 1F, 5, 8, 3);
        tank2.setRotationPoint(0F, 0F, 0F);
        tank2.setTextureSize(64, 32);
        tank2.mirror = true;
        setRotation(tank2, 0F, 1.570796F, 0F);
        
        tankMain2 = new ModelRenderer(this, 0, 0);
        tankMain2.addBox(-4F, 0F+var, 4F-var, 3, 10, 3);
        tankMain2.setRotationPoint(0F, 0F, 0F);
        tankMain2.setTextureSize(64, 32);
        tankMain2.mirror = true;
        setRotation(tankMain2, 0F, 0F, 0F);
        
        tank3 = new ModelRenderer(this, 12, 0);
        tank3.addBox(-5F, 1F+var, 4F-var, 5, 8, 3);
        tank3.setRotationPoint(0F, 0F, 0F);
        tank3.setTextureSize(64, 32);
        tank3.mirror = true;
        setRotation(tank3, 0F, 0F, 0F);
        
        tank4 = new ModelRenderer(this, 12, 0);
        tank4.addBox(-8F+var, 1F+var, -4F, 5, 8, 3);
        tank4.setRotationPoint(0F, 0F, 0F);
        tank4.setTextureSize(64, 32);
        tank4.mirror = true;
        setRotation(tank4, 0F, 1.570796F, 0F);
        
        pipeCenter = new ModelRenderer(this, 28, 4);
        pipeCenter.addBox(-1F, -1F+var, 3F-var, 2, 3, 1);
        pipeCenter.setRotationPoint(0F, 0F, 0F);
        pipeCenter.setTextureSize(64, 32);
        pipeCenter.mirror = true;
        setRotation(pipeCenter, 0F, 0F, 0F);
        
        pipeLeft = new ModelRenderer(this, 34, 4);
        pipeLeft.addBox(1F, -1F+var, 3F-var, 2, 1, 1);
        pipeLeft.setRotationPoint(0F, 0F, 0F);
        pipeLeft.setTextureSize(64, 32);
        pipeLeft.mirror = true;
        setRotation(pipeLeft, 0F, 0F, 0F);
        
        pipeRight = new ModelRenderer(this, 34, 4);
        pipeRight.addBox(-3F, -1F+var, 3F-var, 2, 1, 1);
        pipeRight.setRotationPoint(0F, 0F, 0F);
        pipeRight.setTextureSize(64, 32);
        pipeRight.mirror = true;
        setRotation(pipeRight, 0F, 0F, 0F);
        
        pipeCenter.addChild(tankMain1);
        pipeCenter.addChild(tank1);
        pipeCenter.addChild(tank2);
        pipeCenter.addChild(tankMain2);
        pipeCenter.addChild(tank3);
        pipeCenter.addChild(tank4);
        pipeCenter.addChild(pipeLeft);
        pipeCenter.addChild(pipeRight);
		this.bipedBody.addChild(pipeCenter);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	super.render(entity, f, f1, f2, f3, f4, f5);
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	if (this.renderHead)
        {
            GlStateManager.pushMatrix();
	    	head.rotateAngleX = this.bipedHead.rotateAngleX;
	    	if (entity instanceof EntityArmorStand) head.rotateAngleY = (float)entity.rotationPitch;
	    	else head.rotateAngleY = this.bipedHead.rotateAngleY;
	    	if (entity.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.25F, 0.0F);
            }
	    	head.render(f5 + 0.01F);
	        GlStateManager.popMatrix();
        }
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
    }
}
