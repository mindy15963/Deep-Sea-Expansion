package com.nhave.dse.item;

import java.util.List;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.items.ItemBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorPlate extends ItemBase implements IArmorPlate
{
	private int[] damageReduction;
	private EnumRarity rarity = EnumRarity.COMMON;
	
	public ItemArmorPlate(String name, int[] damageReduction)
	{
		super(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETABITEMS);
		this.damageReduction = damageReduction;
	}
	
	public ItemArmorPlate(String name, int[] damageReduction, EnumRarity rarity)
	{
		this(name, damageReduction);
		this.rarity = rarity;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return this.rarity;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		list.add(I18n.translateToLocal("tooltip.dse.armor"));
		list.add(getDamageReduction(EntityEquipmentSlot.HEAD) + " " + I18n.translateToLocal("tooltip.dse.armor.head"));
		list.add(getDamageReduction(EntityEquipmentSlot.CHEST) + " " + I18n.translateToLocal("tooltip.dse.armor.chest"));
		list.add(getDamageReduction(EntityEquipmentSlot.LEGS) + " " + I18n.translateToLocal("tooltip.dse.armor.legs"));
		list.add(getDamageReduction(EntityEquipmentSlot.FEET) + " " + I18n.translateToLocal("tooltip.dse.armor.feet"));
	}

	@Override
	public int getDamageReduction(EntityEquipmentSlot armorType)
	{
		return damageReduction[Math.min(Math.max(0, armorType.getIndex()), damageReduction.length-1)];
	}
}