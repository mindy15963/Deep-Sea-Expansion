package com.nhave.dse.api.items;

import net.minecraft.item.ItemStack;

public interface IAirTankItem
{
	public int getOxygenStored(ItemStack itemStack);
	
	public void setOxygen(ItemStack itemStack, int amount);
	
	public int getMaxOxygen(ItemStack itemStack);
	
	public boolean isDualTank(ItemStack itemStack);
}