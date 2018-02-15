package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.api.items.IArmorplate;
import com.nhave.dse.api.items.IItemUpgradeAdvanced;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorplate extends ItemMeta implements IItemUpgradeAdvanced, IArmorplate
{
	private int[] damageReductionHead;
	private int[] damageReductionChest;
	private int[] damageReductionLegs;
	private int[] damageReductionBoots;
	
	public ItemArmorplate(String name, String[][] names)
	{
		super(name, names);
		this.damageReductionHead = new int[names.length];
		this.damageReductionChest = new int[names.length];
		this.damageReductionLegs = new int[names.length];
		this.damageReductionBoots = new int[names.length];
		
		for (int i = 0; i < names.length; ++i)
		{
			this.damageReductionHead[i] = Integer.parseInt(names[i][5]);
			this.damageReductionChest[i] = Integer.parseInt(names[i][4]);
			this.damageReductionLegs[i] = Integer.parseInt(names[i][3]);
			this.damageReductionBoots[i] = Integer.parseInt(names[i][2]);
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		return StringUtils.localize("item.dse.armorplate." + names[meta] + ".name");
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item." + this.getItemName(stack)), ";", StringUtils.GRAY);
			
			tooltip.add("");
			tooltip.add(StringUtils.localize("tooltip.dse.armorplate.onscuba") + ":");
			tooltip.add(StringUtils.format(" +" + getDamageReduction(stack, EntityEquipmentSlot.HEAD) + " " + StringUtils.localize("tooltip.dse.armorplate.onhead"), StringUtils.LIGHT_BLUE));
			tooltip.add(StringUtils.format(" +" + getDamageReduction(stack, EntityEquipmentSlot.CHEST) + " " + StringUtils.localize("tooltip.dse.armorplate.onbody"), StringUtils.LIGHT_BLUE));
			tooltip.add(StringUtils.format(" +" + getDamageReduction(stack, EntityEquipmentSlot.LEGS) + " " + StringUtils.localize("tooltip.dse.armorplate.onlegs"), StringUtils.LIGHT_BLUE));
			tooltip.add(StringUtils.format(" +" + getDamageReduction(stack, EntityEquipmentSlot.FEET) + " " + StringUtils.localize("tooltip.dse.armorplate.onfeet"), StringUtils.LIGHT_BLUE));
		}
		else tooltip.add(StringUtils.shiftForInfo);
		
		//UpgradeHelper.addInformation(stack, world, tooltip, advanced);
	}

	@Override
	public int getDamageReduction(ItemStack stack, EntityEquipmentSlot slot)
	{
		int meta = Math.max(0, Math.min(this.damageReductionHead.length - 1, stack.getItemDamage()));
		
		if (slot == EntityEquipmentSlot.HEAD) return this.damageReductionHead[meta];
		else if (slot == EntityEquipmentSlot.CHEST) return this.damageReductionChest[meta];
		else if (slot == EntityEquipmentSlot.LEGS) return this.damageReductionLegs[meta];
		else if (slot == EntityEquipmentSlot.FEET) return this.damageReductionBoots[meta];
		else return 0;
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