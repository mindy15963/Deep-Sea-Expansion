package com.nhave.dse.items;

import com.nhave.dse.DeepSeaExpansion;

import net.minecraft.item.Item;

public class ItemHammer extends ItemBase
{
	public ItemHammer(String name)
	{
		super(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETABTOOLS);
		this.setMaxStackSize(1);
	}
}