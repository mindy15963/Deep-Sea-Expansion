package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.api.items.IItemUpgradeAdvanced;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.registry.ModItems;
import com.nhave.dse.utils.NumberUtils;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemPowerunit extends ItemEnergyBase implements IItemUpgradeAdvanced
{
	public boolean isCreative = false;
	
	public ItemPowerunit(String name, int maxEnergy, int maxTransfer)
	{
		super(name, maxEnergy, maxTransfer);
		this.setMaxStackSize(1);
	}
	
	public ItemPowerunit setIsCreative(boolean isCreative)
	{
		this.isCreative = isCreative;
		return this;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item.powerunit" + (this.isCreative ? ".creative" : "")), ";", StringUtils.GRAY);
			
			tooltip.add(StringUtils.localize("tooltip.dse.charge") + ": " + (this.isCreative ? "∞" : NumberUtils.getDisplayShort(getEnergyStored(stack)) + " / " + NumberUtils.getDisplayShort(getMaxEnergyStored(stack))) + " " + ModConfig.energyUnit);
			tooltip.add(StringUtils.localize("tooltip.dse.charge.transfer") + ": " + NumberUtils.getDisplayShort(getMaxTransfer(stack), 0) + " " + ModConfig.energyUnit + "/t");
			
			tooltip.add("");
			tooltip.add(StringUtils.localize("tooltip.dse.mod.canuse") + ":");
			tooltip.add("  " + StringUtils.format(StringUtils.localize("item.dse.scubachest.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
        {
			ItemStack stackEmpty = new ItemStack(this);
		    this.setEnergy(stackEmpty, 0);
		    items.add(stackEmpty);
		    
	    	ItemStack stackFull = new ItemStack(this);
		    if (!this.isCreative)
		    {
		    	this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
		    	items.add(stackFull);
		    }
        }
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return (this.isCreative ?  false : super.showDurabilityBar(stack));
	}
	
	@Override
	public void setEnergy(ItemStack stack, int energy)
	{
		if (!this.isCreative) super.setEnergy(stack, energy);
	}
	
	@Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate)
	{
		return (this.isCreative ?  maxReceive : super.receiveEnergy(stack, maxReceive, simulate));
	}
	
    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate)
    {
		return (this.isCreative ?  maxExtract : super.extractEnergy(stack, maxExtract, simulate));
    }
	
	@Override
	public int getEnergyStored(ItemStack stack)
	{
		return (this.isCreative ?  0 : super.getEnergyStored(stack));
	}
	
	@Override
	public boolean canApplyUpgrade(ItemStack upgradeable, ItemStack upgrade)
	{
		return upgradeable.getItem() == ModItems.itemScubaChest;
	}
	
	@Override
	public String getUpgradeNBT(ItemStack upgradeable, ItemStack upgrade)
	{
		return "POWERUNIT";
	}
	
	@Override
	public boolean ignoreMeta(ItemStack upgrade)
	{
		return true;
	}
	
	@Override
	public String getUpgradeName(ItemStack upgrade)
	{
		return StringUtils.localize("item.dse.powerunit.name");
	}
}