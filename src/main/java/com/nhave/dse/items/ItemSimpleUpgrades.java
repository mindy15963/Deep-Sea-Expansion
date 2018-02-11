package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.api.items.IItemUpgrade;
import com.nhave.dse.registry.ModItems;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSimpleUpgrades extends ItemMeta implements IItemUpgrade
{
	public String[] upgradeNBT;
	public String[] itemTag;
	
	public ItemSimpleUpgrades(String name, String[][] names)
	{
		super(name, names);
		this.upgradeNBT = new String[names.length];
		this.itemTag = new String[names.length];
		for (int i = 0; i < names.length; ++i)
		{
			this.upgradeNBT[i] = names[i][2];
			if (names[i].length >= 4) this.itemTag[i] = names[i][3];
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		return StringUtils.localize("item.dse.upgrade." + names[meta] + ".name");
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		
		if (this.itemTag[meta] != null)
		{
			if (this.itemTag[meta].equals("WIP")) tooltip.add(StringUtils.format("[Work In Progress]", StringUtils.RED, StringUtils.BOLD));
			else if (this.itemTag[meta].equals("NYI")) tooltip.add(StringUtils.format("[Not Yet Implemented]", StringUtils.RED, StringUtils.BOLD));
		}
		
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.upgrade." + names[meta]), ";", StringUtils.GRAY);
			
			tooltip.add(StringUtils.localize("tooltip.dse.mod.canuse") + ":");
			for (int i = 0; i < ModItems.MOTORBOAT_UPGRADES.size(); ++i)
			{
				ItemStack mod = ModItems.MOTORBOAT_UPGRADES.get(i);
				if (mod.getItem() == stack.getItem() && mod.getItemDamage() == stack.getItemDamage())
				{
					tooltip.add("  " + StringUtils.format(StringUtils.localize("item.dse.motorboat.name"), StringUtils.YELLOW, StringUtils.ITALIC));
				}
			}
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}

	@Override
	public boolean canApplyUpgrade(ItemStack upgradeable, ItemStack upgrade)
	{
		if (upgradeable.getItem() == ModItems.itemMotorboat)
		{
			for (int i = 0; i < ModItems.MOTORBOAT_UPGRADES.size(); ++i)
			{
				ItemStack mod = ModItems.MOTORBOAT_UPGRADES.get(i);
				if (mod.getItem() == upgrade.getItem() && mod.getItemDamage() == upgrade.getItemDamage())
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getUpgradeNBT(ItemStack upgradeable, ItemStack upgrade)
	{
		int meta = Math.min(upgrade.getItemDamage(), names.length-1);
		return this.upgradeNBT[meta];
	}
}