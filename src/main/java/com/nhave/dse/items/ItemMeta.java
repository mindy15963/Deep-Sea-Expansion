package com.nhave.dse.items;

import java.util.List;

import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemMeta extends ItemBase
{
	private String[] rarityNames = new String[] {"", StringUtils.LIGHT_BLUE, StringUtils.PURPLE, StringUtils.ORANGE};
	private int rarity = 0;
	public String[] names;
	private int[] rarities;
	
	public ItemMeta(String name, String[] names, int rarity)
	{
		super(name);
		this.names = names;
		this.rarity = rarity;
		this.setHasSubtypes(true);
	}
	
	public ItemMeta(String name, String[] names, String type, int[] rarities)
	{
		this(name, names, 0);
		if (rarities.length == names.length) this.rarities = rarities;
	}
	
	public ItemMeta(String name, String[][] names)
	{
		super(name);
		this.rarities = new int[names.length];
		this.names = new String[names.length];
		for (int i = 0; i < names.length; ++i)
		{
			this.names[i] = names[i][0];
			this.rarities[i] = Integer.parseInt(names[i][1]);
		}
		this.rarity = 0;
		this.setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
        {
			for (int i = 0; i < names.length; ++i)
			{
				items.add(new ItemStack(this, 1, i));
			}
        }
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		return StringUtils.localize("item.dse." + names[meta] + ".name");
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		String descUnlocalized = "tooltip.dse.item." + names[meta];
		String descLocalized = StringUtils.localize(descUnlocalized);
		
		if (!descLocalized.equals(descUnlocalized))
		{
			if (StringUtils.isShiftKeyDown()) TooltipHelper.addSplitString(tooltip, descLocalized, ";", StringUtils.GRAY);
			else tooltip.add(StringUtils.shiftForInfo);
		}
	}
	
	@Override
	public String getQualityColor(ItemStack stack)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		return this.rarities != null ? this.rarityNames[rarities[meta]] : this.rarityNames[0];
	}
}