package com.nhave.dse.api.items;

import net.minecraft.item.ItemStack;

public interface IItemUpgradeAdvanced extends IItemUpgrade
{
	public boolean ignoreMeta(ItemStack upgrade);
	
	public String getUpgradeName(ItemStack upgrade);
}