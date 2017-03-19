package com.nhave.dse.items;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public class ItemPearl extends ItemBase
{
	public int maxPearlSize = 24000;
	
	public ItemPearl(String name)
	{
		super(name);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(getSizePercentage(stack) + "%");
		if (Minecraft.getMinecraft().gameSettings.advancedItemTooltips) tooltip.add("§8blackpearl: " + isBlack(stack));
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabb, NonNullList<ItemStack> list)
	{
		list.add(new ItemStack(this));
		list.add(setBlack(new ItemStack(this)));
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return Math.max(1.0 - (double)getSize(stack) / (double)maxPearlSize, 0);
	}
	
	public int getSize(ItemStack itemStack)
	{
		if(itemStack.getTagCompound() == null)
		{
			return 0;
		}
	
		int size = itemStack.getTagCompound().getInteger("pearlsize");
	
		return size;
	}
	
	public ItemStack setSize(ItemStack itemStack, int amount)
	{
		if(itemStack.getTagCompound() == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
	
		int size = Math.max(Math.min(amount, maxPearlSize), 0);
		itemStack.getTagCompound().setDouble("pearlsize", size);
		return itemStack;
	}
	
	public ItemStack setBlack(ItemStack itemStack)
	{
		if(itemStack.getTagCompound() == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
	
		itemStack.getTagCompound().setBoolean("isblack", true);
		return itemStack;
	}
	
	public boolean isBlack(ItemStack itemStack)
	{
		if(itemStack.getTagCompound() == null)
		{
			return false;
		}
	
		boolean isBlack = itemStack.getTagCompound().getBoolean("isblack");
	
		return isBlack;
	}
	
	public int getSizePercentage(ItemStack itemStack)
	{
		int cur = getSize(itemStack);
		int max = maxPearlSize;
		return (int)((cur * 100.0f) / max);
	}
}