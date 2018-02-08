package com.nhave.dse.items;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;
import com.nhave.nhc.api.items.IItemQuality;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorBase extends ItemArmor implements IItemQuality
{
	private String rarity = "";
	
	public ItemArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(Reference.MODID + "." + name.replaceAll("_", ""));
		this.setRegistryName(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETAB);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot equipmentSlot, String armorTexture)
	{
		return Reference.MODID + ":textures/models/armor/" + getItemName(stack) + ".png";
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}
	
	public ItemArmorBase setQualityColor(String color)
	{
		this.rarity = color;
		return this;
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		return this.rarity;
	}
}