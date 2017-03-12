package com.nhave.dse.items;

import com.nhave.dse.api.items.IFlippers;
import com.nhave.dse.client.models.ModelFlippers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFlippers extends ItemArmorBase implements IFlippers
{
	public ItemFlippers(String name, ArmorMaterial materialIn)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.FEET);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot equipmentSlot, ModelBiped modelBiped)
	{
		return ModelFlippers.INSTANCE;
	}
	
	@Override
	public boolean isFlippersActive(EntityPlayer player, ItemStack stack)
	{
		return true;
	}
}