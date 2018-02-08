package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHammer extends ItemBase
{
	private int MaxHammerDamage;
	
	public ItemHammer(String name, int maxDamage)
	{
		super(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETAB);
		this.setMaxStackSize(1);
		this.MaxHammerDamage = maxDamage;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item.hammer"), ";", StringUtils.GRAY);
			
			if (this.MaxHammerDamage > 0)
			{
				int usesLeft = this.MaxHammerDamage - ItemNBTHelper.getInteger(stack, "HAMMER", "DAMAGE", 0);
				tooltip.add(StringUtils.localize("tooltip.dse.usesleft") + ": " + usesLeft);
			}
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return this.MaxHammerDamage == 0 || ItemNBTHelper.getInteger(stack, "HAMMER", "DAMAGE", 0) < (this.MaxHammerDamage - 1);
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack)
	{
		int damage = ItemNBTHelper.getInteger(stack, "HAMMER", "DAMAGE", 0);
		if (this.MaxHammerDamage > 0) ItemNBTHelper.setInteger(stack, "HAMMER", "DAMAGE", damage + 1);;
		return stack.copy();
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return ItemNBTHelper.getInteger(stack, "HAMMER", "DAMAGE", 0) > 0;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return ItemNBTHelper.getInteger(stack, "HAMMER", "DAMAGE", 0) / (double) this.MaxHammerDamage;
	}
}