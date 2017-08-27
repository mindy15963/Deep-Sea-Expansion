package com.nhave.dse.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.dse.api.items.IDivingHelmet;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemDivingGoggles extends ItemArmorBase implements IDivingHelmet
{
	public ItemDivingGoggles(String name, ArmorMaterial materialIn)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.HEAD);
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
}