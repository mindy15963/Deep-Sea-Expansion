package com.nhave.dse.items;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item
{
	private EnumRarity rarity = EnumRarity.COMMON;
	
	public ItemBase(String name)
	{
		this.setCreativeTab(DeepSeaExpansion.CREATIVETABITEMS);
		this.setUnlocalizedName(Reference.MODID + "." + name);
		this.setRegistryName(name);
	}
	
	public ItemBase(String name, Object... obj)
	{
		this(name);
		for (int i = 0; i < obj.length; ++i)
		{
			if (obj[i] instanceof String)
			{
				if (((String) obj[i]).equals("TAB_HIDDEN")) this.setCreativeTab(null);
			}
			else if (obj[i] instanceof EnumRarity) this.rarity = (EnumRarity) obj[i];
			else if (obj[i] instanceof CreativeTabs) this.setCreativeTab((CreativeTabs) obj[i]);
		}
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return this.rarity;
	}
}