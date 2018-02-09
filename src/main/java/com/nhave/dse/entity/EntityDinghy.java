package com.nhave.dse.entity;

import com.nhave.dse.registry.ModItems;

import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityDinghy extends EntityBoatBase
{
	public EntityDinghy(World worldIn)
	{
		super(worldIn);
	}
	
	public EntityDinghy(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}
	
	@Override
	public Item getItemBoat()
    {
		return ModItems.itemDinghy;
    }
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (player.isSneaking() && player.getHeldItemMainhand() == ItemStack.EMPTY)
        {
        	if (!this.world.isRemote)
        	{
        		this.setDead();
        		player.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(getItemBoat()));
        	}
        	player.swingArm(hand);
            return true;
        }
        return super.processInitialInteract(player, hand);
    }
	
	@Override
	public void onUpdate()
	{
		//Run base boat update code. Then add the overrides
		super.onUpdate();
		
		//Propels the Dinghy back to the surface
		if (this.status == EntityBoat.Status.UNDER_WATER)
		{
			this.motionY = 0.7D;
		}
		
		//Prevents the Player from being kicked of the Dinghy
    	this.outOfControlTicks = 0.0F;
	}
}