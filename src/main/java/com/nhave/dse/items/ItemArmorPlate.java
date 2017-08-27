package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.api.items.IArmorPlate;
import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorPlate extends ItemBase implements IArmorPlate, IItemQuality
{
	private int[] damageReduction;
	private String quality = "";
	
	public ItemArmorPlate(String name, int[] damageReduction)
	{
		super(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETABITEMS);
		this.damageReduction = damageReduction;
	}
	
	public ItemArmorPlate(String name, int[] damageReduction, String quality)
	{
		this(name, damageReduction);
		this.quality = quality;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
	{
		list.add(StringUtils.format("[" + StringUtils.localize("tooltip.dse.block.wip") + "]", StringUtils.LIGHT_RED, StringUtils.ITALIC));
		if (StringUtils.isShiftKeyDown())
		{
			list.add(StringUtils.localize("tooltip.dse.armor"));
			list.add(getDamageReduction(EntityEquipmentSlot.HEAD) + " " + StringUtils.localize("tooltip.dse.armor.head"));
			list.add(getDamageReduction(EntityEquipmentSlot.CHEST) + " " + StringUtils.localize("tooltip.dse.armor.chest"));
			list.add(getDamageReduction(EntityEquipmentSlot.LEGS) + " " + StringUtils.localize("tooltip.dse.armor.legs"));
			list.add(getDamageReduction(EntityEquipmentSlot.FEET) + " " + StringUtils.localize("tooltip.dse.armor.feet"));
		}
		else list.add(StringUtils.shiftForInfo);
	}

	@Override
	public int getDamageReduction(EntityEquipmentSlot armorType)
	{
		return damageReduction[Math.min(Math.max(0, armorType.getIndex()), damageReduction.length-1)];
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		return this.quality;
	}
}