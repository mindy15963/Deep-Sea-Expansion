package com.nhave.dse.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IFlippers
{
	public boolean isFlippersActive(EntityPlayer player, ItemStack stack);
}