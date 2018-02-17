package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.api.items.IItemUpgradeAdvanced;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorInsulation extends ItemBase implements IItemUpgradeAdvanced
{
	public ItemArmorInsulation(String name)
	{
		super(name);
		this.setQualityColor(StringUtils.PURPLE);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return StringUtils.localize("item.dse.insulation." + this.getItemName(stack) + ".name");
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(StringUtils.format("[Not Yet Implemented]", StringUtils.RED, StringUtils.BOLD));
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.insulation." + this.getItemName(stack)), ";", StringUtils.GRAY);
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.buff." + this.getItemName(stack)), ";", StringUtils.BRIGHT_GREEN);
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.armorplate.noarmor"), ";", StringUtils.ORANGE);
			
			tooltip.add("");
			tooltip.add(StringUtils.localize("tooltip.dse.mod.canuse") + ":");
			tooltip.add("  " + StringUtils.format(StringUtils.localize("tooltip.dse.shader.scuba"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public boolean canApplyUpgrade(ItemStack upgradeable, ItemStack upgrade)
	{
		return upgradeable.getItem() instanceof ItemArmorScuba;
	}
	
	@Override
	public String getUpgradeNBT(ItemStack upgradeable, ItemStack upgrade)
	{
		return "ARMORPLATE";
	}
	
	@Override
	public boolean ignoreMeta(ItemStack upgrade)
	{
		return true;
	}
	
	@Override
	public String getUpgradeName(ItemStack upgrade)
	{
		return StringUtils.localize("item.dse.armorplate.name");
	}
}
