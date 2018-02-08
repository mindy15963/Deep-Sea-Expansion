package com.nhave.dse.api.items;

import net.minecraft.item.ItemStack;

public interface IItemUpgrade
{
	public boolean canApplyUpgrade(ItemStack upgradeable, ItemStack upgrade);
	
	public String getUpgradeNBT(ItemStack upgradeable, ItemStack upgrade);
}