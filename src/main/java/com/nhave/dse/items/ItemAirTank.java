package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.client.models.ModelScubaTankLarge;
import com.nhave.dse.client.models.ModelScubaTankSmall;

import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAirTank extends ItemArmorBase implements IAirTank
{
	private boolean isDualTank;
	private int maxAir;
	public boolean isCreative = false;
	private EnumRarity rarity = EnumRarity.COMMON;
	
	public ItemAirTank(String name, ArmorMaterial materialIn, int maxAir, boolean isDualTank)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.CHEST);
		this.isDualTank = isDualTank;
		this.maxAir = maxAir;
		if (maxAir <= 0) this.isCreative = true;
	}
	
	public ItemAirTank(String name, ArmorMaterial materialIn, int maxAir, boolean isDualTank, EnumRarity rarity)
	{
		this(name, materialIn, maxAir, isDualTank);
		this.rarity = rarity;

	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabb, NonNullList<ItemStack> list)
	{
		ItemStack noO2 = new ItemStack(this);
		list.add(noO2);
		if (!this.isCreative)
		{
			ItemStack maxO2 = new ItemStack(this);
			setO2(maxO2, getMaxO2(maxO2));
			list.add(maxO2);
		}
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
		double time = getO2(stack)/20D;
		double minutes = Math.floor(time / 60);
		double seconds = Math.floor(time - minutes * 60);
		boolean showMinutes = (int)minutes > 0;
		list.add(I18n.translateToLocal("tooltip.dse.oxygen") + " " + (this.isCreative ? "∞" : (showMinutes ? (int)minutes + "m:" : "") + (int)seconds + "s"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot equipmentSlot, ModelBiped modelBiped)
	{
		ModelBiped model = null;
		if (this.isDualTank) model = ModelScubaTankLarge.NOARMOR;
		else model = ModelScubaTankSmall.NOARMOR;
		return model;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return !this.isCreative;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return Math.max(1.0 - (double)getO2(stack) / (double)getMaxO2(stack), 0);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (!player.capabilities.isCreativeMode && player.isInsideOfMaterial(Material.WATER))
		{
			if (getO2(stack) > 0 || this.isCreative)
			{
				player.setAir(player.getAir()+1);
				setO2(stack, getO2(stack) - 1);
			}
		}
	}

	@Override
	public int getO2(ItemStack itemStack)
	{
		if (this.isCreative) return 0;
		if(itemStack.getTagCompound() == null)
		{
			return 0;
		}
	
		int o2Stored = itemStack.getTagCompound().getInteger("co2");
	
		return o2Stored;
	}

	@Override
	public void setO2(ItemStack itemStack, int amount)
	{
		if (this.isCreative) return;
		if(itemStack.getTagCompound() == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
	
		int o2Stored = Math.max(Math.min(amount, getMaxO2(itemStack)), 0);
		itemStack.getTagCompound().setDouble("co2", o2Stored);
	}
	
	@Override
	public int getMaxO2(ItemStack itemStack)
	{
		return this.maxAir;
	}

	@Override
	public boolean isDualTank(ItemStack itemStack)
	{
		return this.isDualTank;
	}
}