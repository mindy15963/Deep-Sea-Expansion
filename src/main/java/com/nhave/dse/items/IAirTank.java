package com.nhave.dse.items;

import net.minecraft.item.ItemStack;

public interface IAirTank
{
	public int getO2(ItemStack itemStack);
	
	public void setO2(ItemStack itemStack, int amount);

	public int getMaxO2(ItemStack itemStack);

	public boolean isDualTank(ItemStack itemStack);
}