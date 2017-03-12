package com.nhave.dse.items;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item
{
	public ItemBase(String name)
	{
		this.setCreativeTab(DeepSeaExpansion.CREATIVETABITEMS);
		this.setUnlocalizedName(Reference.MODID + "." + name);
		this.setRegistryName(name);
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}
}