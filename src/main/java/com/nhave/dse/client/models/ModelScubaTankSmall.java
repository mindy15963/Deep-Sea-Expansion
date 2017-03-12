package com.nhave.dse.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelScubaTankSmall extends ModelBiped
{
    public static final ModelScubaTankSmall ARMOR = new ModelScubaTankSmall(0F, false);
    public static final ModelScubaTankSmall NOARMOR = new ModelScubaTankSmall(1F, true);

    ModelRenderer head;
	ModelRenderer tankMain;
    ModelRenderer tank1;
    ModelRenderer tank2;
    ModelRenderer pipeLeft1;
    ModelRenderer pipeLeft2;
    ModelRenderer pipeRight1;
    ModelRenderer pipeRight2;
    
    boolean renderHead;
    
    public ModelScubaTankSmall(float var, boolean renderHead)
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
        
        tankMain = new ModelRenderer(this, 0, 0);
        tankMain.addBox(-1.5F, 0F+var, 4F-var, 3, 10, 3);
        tankMain.setRotationPoint(0F, 0F, 0F);
        tankMain.setTextureSize(64, 32);
        tankMain.mirror = true;
        setRotation(tankMain, 0F, 0F, 0F);
        
        tank1 = new ModelRenderer(this, 12, 0);
        tank1.addBox(-2.5F, 1F+var, 4F-var, 5, 8, 3);
        tank1.setRotationPoint(0F, 0F, 0F);
        tank1.setTextureSize(64, 32);
        tank1.mirror = true;
        setRotation(tank1, 0F, 0F, 0F);
        
        tank2 = new ModelRenderer(this, 12, 0);
        tank2.addBox(-8F+var, 1F+var, -1.5F, 5, 8, 3);
        tank2.setRotationPoint(0F, 0F, 0F);
        tank2.setTextureSize(64, 32);
        tank2.mirror = true;
        setRotation(tank2, 0F, 1.570796F, 0F);
        
        pipeLeft1 = new ModelRenderer(this, 28, 0);
        pipeLeft1.addBox(1.5F, -1F+var, 3F-var, 1, 3, 1);
        pipeLeft1.setRotationPoint(0F, 0F, 0F);
        pipeLeft1.setTextureSize(64, 32);
        pipeLeft1.mirror = true;
        setRotation(pipeLeft1, 0F, 0F, 0F);
        
        pipeLeft2 = new ModelRenderer(this, 32, 0);
        pipeLeft2.addBox(2.5F, -1F+var, 3F-var, 1, 1, 1);
        pipeLeft2.setRotationPoint(0F, 0F, 0F);
        pipeLeft2.setTextureSize(64, 32);
        pipeLeft2.mirror = true;
        setRotation(pipeLeft2, 0F, 0F, 0F);
        
        pipeRight1 = new ModelRenderer(this, 28, 0);
        pipeRight1.addBox(-2.5F, -1F+var, 3F-var, 1, 3, 1);
        pipeRight1.setRotationPoint(0F, 0F, 0F);
        pipeRight1.setTextureSize(64, 32);
        pipeRight1.mirror = true;
        setRotation(pipeRight1, 0F, 0F, 0F);
        
        pipeRight2 = new ModelRenderer(this, 32, 0);
        pipeRight2.addBox(-3.5F, -1F+var, 3F-var, 1, 1, 1);
        pipeRight2.setRotationPoint(0F, 0F, 0F);
        pipeRight2.setTextureSize(64, 32);
        pipeRight2.mirror = true;
        setRotation(pipeRight2, 0F, 0F, 0F);

		tankMain.addChild(tank1);
		tankMain.addChild(tank2);
		tankMain.addChild(pipeLeft1);
		tankMain.addChild(pipeLeft2);
		tankMain.addChild(pipeRight1);
		tankMain.addChild(pipeRight2);
		this.bipedBody.addChild(tankMain);
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
