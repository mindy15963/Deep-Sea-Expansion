package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;
import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBase extends Item implements IItemQuality
{
	private String rarity = "";
	
	public ItemBase(String name)
	{
		this.setUnlocalizedName(Reference.MODID + "." + name.replaceAll("_", ""));
		this.setRegistryName(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETAB);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		String descUnlocalized = "tooltip.dse.item." + this.getItemName(stack);
		String descLocalized = StringUtils.localize(descUnlocalized);
		
		if (!descLocalized.equals(descUnlocalized))
		{
			if (StringUtils.isShiftKeyDown()) TooltipHelper.addSplitString(tooltip, descLocalized, ";", StringUtils.GRAY);
			else tooltip.add(StringUtils.shiftForInfo);
		}
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}
	
	public ItemBase setQualityColor(String color)
	{
		this.rarity = color;
		return this;
	}
	
	@Override
	public String getQualityColor(ItemStack stack)
	{
		return this.rarity;
	}
}