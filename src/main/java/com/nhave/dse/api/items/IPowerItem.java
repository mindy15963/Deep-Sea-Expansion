package com.nhave.dse.api.items;

import net.minecraft.item.ItemStack;

public interface IPowerItem
{
	public int getPower(ItemStack itemStack);
	
	public void setPower(ItemStack itemStack, int amount);

	public int getMaxPower(ItemStack itemStack);
	
	public String getPowerInfo(ItemStack itemStack);
}