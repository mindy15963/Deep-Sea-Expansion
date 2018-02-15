package com.nhave.dse.helpers;

import java.util.List;

import com.google.common.collect.Multimap;
import com.nhave.dse.items.ItemArmorScuba;
import com.nhave.dse.items.ItemArmorplate;
import com.nhave.dse.items.ItemSimpleUpgrades;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.registry.ModItems;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UpgradeHelper
{
    @SideOnly(Side.CLIENT)
	public static void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
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
		else if (stack.getItem() instanceof ItemArmorScuba)
		{
			if (stack.getItem() == ModItems.itemScubaMask)
			{
				if (ItemUtil.getItemFromStack(stack, "LIGHTFILTER") != null)
				{
					//tooltip.add(StringUtils.format(400 + " " + ModConfig.energyUnit + " " + StringUtils.localize("tooltip.dse.charge.tick"), StringUtils.ORANGE));
				}
			}
		}
	}
    
    public static void addAttributeModifiers(Multimap<String, AttributeModifier> multimap, ItemStack stack)
    {
    	if (stack.getItem() instanceof ItemArmorScuba)
		{
			ItemStack armorplate = ItemUtil.getItemFromStack(stack, "ARMORPLATE");
			if (armorplate != null && armorplate.getItem() == ModItems.itemArmorplate)
			{
				ItemArmorplate item = ((ItemArmorplate) armorplate.getItem());
				int meta = armorplate.getItemDamage();
				String name = item.names[Math.max(0, Math.min(item.names.length - 1, meta))];

				System.out.println(name);
				if (name.equals("hyperthermic"))
				{
					multimap.put("dse.hyperthermic", new AttributeModifier("dse.hyperthermic", 1D, 0));
				}
				else if (name.equals("hypothermic"))
				{
					multimap.put("dse.hypothermic", new AttributeModifier("dse.hypothermic", 1D, 0));
				}
			}
		}
    }
}