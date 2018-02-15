package com.nhave.dse.api.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public interface IArmorplate
{
	public int getDamageReduction(ItemStack stack, EntityEquipmentSlot slot);
}