package com.nhave.dse.helpers;

import java.util.List;

import com.nhave.dse.items.ItemSimpleUpgrades;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.registry.ModItems;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UpgradeHelper
{
	public static void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced)
	{
		if (stack == null || stack.isEmpty()) return;
		
		int meta = stack.getItemDamage();
		if (stack.getItem() == ModItems.itemSimpleUpgrades)
		{
			ItemSimpleUpgrades item = ((ItemSimpleUpgrades) stack.getItem());
			String name = item.names[Math.max(0, Math.min(item.names.length - 1, meta))];
			
			if (name.equals("boatbooster"))
			{
				tooltip.add(StringUtils.format(ModConfig.boatPowerUsage * ModConfig.boatBoostModifier + " " + ModConfig.energyUnit + "/t " + StringUtils.localize("tooltip.dse.charge.boatboost"), StringUtils.ORANGE));
			}
			else if (name.equals("lightfilter"))
			{
				//tooltip.add(StringUtils.format(400 + " " + ModConfig.energyUnit + " " + StringUtils.localize("tooltip.dse.charge.tick"), StringUtils.ORANGE));
			}
		}
		else if (stack.getItem() == ModItems.itemScubaMask)
		{
			if (ItemUtil.getItemFromStack(stack, "LIGHTFILTER") != null)
			{
				//tooltip.add(StringUtils.format(400 + " " + ModConfig.energyUnit + " " + StringUtils.localize("tooltip.dse.charge.tick"), StringUtils.ORANGE));
			}
		}
	}
}