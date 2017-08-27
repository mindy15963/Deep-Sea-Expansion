package com.nhave.dse.items;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.dse.api.items.IAirTank;
import com.nhave.dse.client.models.ModelScubaTankLarge;
import com.nhave.dse.client.models.ModelScubaTankSmall;
import com.nhave.dse.registry.ModConfig;
import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAirTank extends ItemArmorBase implements IAirTank, IItemQuality
{
	private boolean isDualTank;
	public boolean isCreative = false;
	private int maxAir;
	private int psi;
	private String quality = "";
	
	public ItemAirTank(String name, ArmorMaterial materialIn, int maxAir, int psi, boolean isDualTank)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.CHEST);
		this.isDualTank = isDualTank;
		this.maxAir = maxAir;
		this.psi = psi;
		if (maxAir <= 0) this.isCreative = true;
	}
	
	public ItemAirTank(String name, ArmorMaterial materialIn, int maxAir, boolean isDualTank)
	{
		this(name, materialIn, maxAir, 0, isDualTank);
	}
	
	public ItemAirTank(String name, ArmorMaterial materialIn, int maxAir, int psi, boolean isDualTank, String quality)
	{
		this(name, materialIn, maxAir, psi, isDualTank);
		this.quality = quality;
	}
	
	public ItemAirTank(String name, ArmorMaterial materialIn, int maxAir, boolean isDualTank, String quality)
	{
		this(name, materialIn, maxAir, 0, isDualTank, quality);
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
	{
		list.add(StringUtils.localize("tooltip.dse.oxygen") + ": " + getO2Info(stack));
		if (ModConfig.displayPressure && getPSI(stack) > 0)
		{
			if (ModConfig.pressureUnit.equals("PSI")) list.add("pressure: " + getPercentage(getPSI(stack), getO2(stack), getMaxO2(stack)) + "psi / " + getPSI(stack) + "psi");
			else
			{
				int bar = (int) (Math.round(getPSI(stack) * 0.0689475729D));
				list.add("pressure: " + getPercentage(bar, getO2(stack), getMaxO2(stack)) + "bar / " + bar + "bar");
			}
		}
	}
	
	private int getPercentage(int draw, int current, int max)
	{
		double percentage = ((double) current / (double) max) * 100D;
		double result = Math.ceil(((double) draw / 100D) * percentage);
		return (int) result;
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
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		return HashMultimap.<String, AttributeModifier>create();
	}

	@Override
	public int getO2(ItemStack itemStack)
	{
		if (this.isCreative) return 0;
		if(itemStack.getTagCompound() == null)
		{
			return 0;
		}
	
		int o2Stored = Math.min(itemStack.getTagCompound().getInteger("o2"), this.maxAir);
	
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
		itemStack.getTagCompound().setDouble("o2", o2Stored);
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

	@Override
	public String getO2Info(ItemStack itemStack)
	{
		double time = getO2(itemStack)/20D;
		double minutes = Math.floor(time / 60);
		double seconds = Math.floor(time - minutes * 60);
		boolean showMinutes = (int)minutes > 0;
		return (this.isCreative ? "∞" : (showMinutes ? (int)minutes + "m:" : "") + (int)seconds + "s");
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		return this.quality;
	}
	
	@Override
	public int getPSI(ItemStack itemStack)
	{
		return this.psi;
	}
}