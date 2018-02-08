package com.nhave.dse.items;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.dse.api.items.IDivingHelmet;
import com.nhave.dse.api.items.IItemUpgrade;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDivingGoggles extends ItemArmorBase implements IDivingHelmet, IItemUpgrade
{
	public ItemDivingGoggles(String name, ArmorMaterial materialIn)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.HEAD);
		this.setQualityColor(StringUtils.LIGHT_BLUE);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item." + this.getItemName(stack)), ";", StringUtils.GRAY);
			//tooltip.add(StringUtils.localize("tooltip.dse.mod.canuse") + ":");
			//tooltip.add("  " + StringUtils.format(StringUtils.localize("item.dse.rebreather.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		return HashMultimap.<String, AttributeModifier>create();
	}
	
	@Override
	public boolean isHelmetActive(EntityPlayer player, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public boolean canApplyUpgrade(ItemStack upgradeable, ItemStack upgrade)
	{
		return false;
	}

	@Override
	public String getUpgradeNBT(ItemStack upgradeable, ItemStack upgrade)
	{
		return "GOGGLES";
	}
}