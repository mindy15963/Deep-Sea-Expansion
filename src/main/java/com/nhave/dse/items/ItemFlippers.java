package com.nhave.dse.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.dse.Reference;
import com.nhave.dse.api.items.IFlippers;
import com.nhave.dse.client.models.ModelFlippers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFlippers extends ItemArmorBase implements IFlippers, IItemColor
{
	public static int[] colorCodes = new int[] {2500134, 16711680, 65280, 6704179, 255, 11685080, 5013401, 10066329, 6710886, 15892389, 8388371, 15059968, 6730495, 15027416, 16757299, 16777215};
	
	public ItemFlippers(String name, ArmorMaterial materialIn)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.FEET);
		this.setHasSubtypes(true);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		return HashMultimap.<String, AttributeModifier>create();
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		subItems.add(new ItemStack(itemIn, 1, 12));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot equipmentSlot, ModelBiped modelBiped)
	{
		return ModelFlippers.INSTANCE;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot equipmentSlot, String armorTexture)
	{
		return Reference.MODID + ":textures/armor/" + stack.getItem().getRegistryName().getResourcePath() + (armorTexture == "overlay" ? "_0" : "_1") + ".png";
	}
	
	@Override
	public boolean hasOverlay(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public boolean hasColor(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public int getColor(ItemStack stack)
	{
		return getColorFromItemstack(stack, 1);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int pass)
	{
		return colorCodes[Math.max(0, Math.min(15, stack.getItemDamage()))];
	}
	
	@Override
	public boolean isFlippersActive(EntityPlayer player, ItemStack stack)
	{
		return true;
	}
}