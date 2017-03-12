package com.nhave.dse.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFlippers extends ModelBiped
{
    public static final ModelFlippers INSTANCE = new ModelFlippers();

    ModelRenderer rightFlipper;
    ModelRenderer leftFlipper;
    
    public ModelFlippers()
    {
    	super(1.0F, 0, 64, 32);
    	
    	this.bipedBody.showModel = false;
        this.bipedRightArm.showModel = false;
        this.bipedLeftArm.showModel = false;
        this.bipedHead.showModel = false;
        this.bipedHeadwear.showModel = false;
        this.bipedRightLeg.showModel = true;
        this.bipedLeftLeg.showModel = true;
        
        rightFlipper = new ModelRenderer(this, 40, 0);
        rightFlipper.addBox(-3.25F, 11.75F, -7F, 5, 1, 7, 0.25F);
        rightFlipper.setRotationPoint(0F, 0F, 0F);
        rightFlipper.setTextureSize(64, 32);
        rightFlipper.mirror = true;
        setRotation(rightFlipper, 0F, 0F, 0F);
        
        leftFlipper = new ModelRenderer(this, 40, 0);
        leftFlipper.addBox(-1.75F, 11.75F, -7F, 5, 1, 7, 0.25F);
        leftFlipper.setRotationPoint(0F, 0F, 0F);
        leftFlipper.setTextureSize(64, 32);
        leftFlipper.mirror = false;
        setRotation(leftFlipper, 0F, 0F, 0F);

		this.bipedRightLeg.addChild(rightFlipper);
		this.bipedLeftLeg.addChild(leftFlipper);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	super.render(entity, f, f1, f2, f3, f4, f5);
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
    }
}