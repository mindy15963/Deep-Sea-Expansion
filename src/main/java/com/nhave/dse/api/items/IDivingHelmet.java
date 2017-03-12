package com.nhave.dse.api.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IDivingHelmet
{
	public boolean isHelmetActive(EntityPlayer player, ItemStack stack);
}