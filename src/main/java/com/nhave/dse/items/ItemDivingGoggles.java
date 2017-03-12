package com.nhave.dse.items;

import com.nhave.dse.api.items.IDivingHelmet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemDivingGoggles extends ItemArmorBase implements IDivingHelmet
{
	public ItemDivingGoggles(String name, ArmorMaterial materialIn)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.HEAD);
	}
	
	@Override
	public boolean isHelmetActive(EntityPlayer player, ItemStack stack)
	{
		return true;
	}
}