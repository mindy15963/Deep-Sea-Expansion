package com.nhave.dse.items;

import java.util.List;

import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHeavyBoots extends ItemArmorBase
{
	public ItemHeavyBoots(String name, ArmorMaterial materialIn)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.FEET);
		this.setQualityColor(StringUtils.LIGHT_BLUE);
		this.setNoRepair();
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item." + this.getItemName(stack)), ";", StringUtils.GRAY);
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
}