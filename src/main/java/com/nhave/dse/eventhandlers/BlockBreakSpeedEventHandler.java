package com.nhave.dse.eventhandlers;

import com.nhave.dse.registry.ModItems;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.util.ItemUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockBreakSpeedEventHandler
{
	@SubscribeEvent
	public void blockBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		EntityPlayer player = event.getEntityPlayer();
		if (player == null) return;
		ItemStack stack = player.getHeldItemMainhand();
		float speed = event.getOriginalSpeed();
		if (isSetComplete(player))
		{
			ItemStack legs = ItemHelper.getCurrentItemOrArmor(player, 3);
			if(ItemUtil.getItemFromStack(legs, "AMPLIFIER") != null && player.isInsideOfMaterial(Material.WATER) && !player.capabilities.isFlying)
			{
				speed *= (!player.onGround ? 25 : 5);
			}
		}
		event.setNewSpeed(speed);
	}
	
	public boolean isSetComplete(EntityPlayer player)
	{
		return (checkArmor(player, 5, ModItems.itemScubaMask) && checkArmor(player, 4, ModItems.itemScubaChest) && checkArmor(player, 3, ModItems.itemScubaLegs) && checkArmor(player, 2, ModItems.itemScubaBoots));
	}
	
	public boolean checkArmor(EntityPlayer player, int slot, Item item)
	{
		return (ItemHelper.getCurrentItemOrArmor(player, slot) != null && ItemHelper.getCurrentItemOrArmor(player, slot).getItem() == item);
	}
}