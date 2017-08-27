package com.nhave.dse.items;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;
import com.nhave.nhc.api.items.IItemQuality;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IItemQuality
{
	private String quality = "";
	
	public ItemBase(String name)
	{
		this.setRegistryName(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETABITEMS);
		this.setUnlocalizedName(Reference.MODID + "." + name);
	}

	public ItemBase(String name, String quality)
	{
		this(name);
		this.quality = quality;
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		return this.quality;
	}
}