package com.nhave.dse.api.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IFlippers
{
	public boolean isFlippersActive(EntityPlayer player, ItemStack stack);
}