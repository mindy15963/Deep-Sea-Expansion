package com.nhave.dse.eventhandlers;

import com.nhave.dse.api.items.IFlippers;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FlippersEventHandler
{
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			
			ItemStack stack = player.inventory.armorItemInSlot(0);
			
			if (!player.capabilities.isFlying)
			{
				if (stack != null && stack.getItem() instanceof IFlippers && ((IFlippers)stack.getItem()).isFlippersActive(player, stack))
				{
					if (player.isInWater())
					{
						player.motionX *= 1.125D;
						player.motionZ *= 1.125D;
						if (player.isSneaking() && player.isInsideOfMaterial(Material.WATER)) player.motionY = 0.0D;
						else player.motionY *= 1.1D;
					}
					else if (player.motionY == -0.0784000015258789)
					{
						player.motionX *= 0.5D;
						player.motionZ *= 0.5D;
					}
				}
			}
		}
	}
}