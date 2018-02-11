package com.nhave.dse.items;

import com.nhave.dse.api.items.IEnergyItem;
import com.nhave.dse.capability.ItemCapabilityProvider;
import com.nhave.dse.registry.ModConfig;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(modid = "redstoneflux", iface = "cofh.redstoneflux.api.IEnergyContainerItem")
public class ItemEnergyBase extends ItemBase implements IEnergyItem, IEnergyContainerItem
{
	private int maxEnergy;
	private int maxTransfer;
	private boolean canReceive = true;
	private boolean canExtract = true;
	
	public ItemEnergyBase(String name, int maxEnergy, int maxTransfer)
	{
		super(name);
		this.maxEnergy = maxEnergy;
		this.maxTransfer = maxTransfer;
	}
	
	public ItemEnergyBase setNoReceive()
	{
		this.canReceive = false;
		return this;
	}
	
	public ItemEnergyBase setNoExtract()
	{
		this.canExtract = false;
		return this;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return !ModConfig.powerItemDurablityType.equals("HIDDEN");
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
	    double maxAmount = this.getMaxEnergyStored(stack);
	    double energyDif = maxAmount-this.getEnergyStored(stack);
	    return energyDif/maxAmount;
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
	{
		if (ModConfig.powerItemDurablityType.equals("SOLID")) return ModConfig.powerItemDurablityColor;
		else return super.getRGBDurabilityForDisplay(stack);
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
			this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
			items.add(stackFull);
        }
	}
	
	@Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
	{
        return new ItemCapabilityProvider(stack, this);
    }
	
	public void setEnergy(ItemStack stack, int energy)
	{
		if(!stack.hasTagCompound())
	    {
	        stack.setTagCompound(new NBTTagCompound());
	    }
		
	    stack.getTagCompound().setInteger("Energy", Math.min(getMaxEnergyStored(stack), Math.max(0, energy)));
	}
	
	@Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate)
	{
		if (!canReceive(stack))
		{
			return 0;
		}
		
        if (stack.getTagCompound() == null)
        {
            stack.setTagCompound(new NBTTagCompound());
        }
        int energy = stack.getTagCompound().getInteger("Energy");
        int energyReceived = Math.min(this.maxEnergy - energy, Math.min(this.maxTransfer, maxReceive));
        
        if (!simulate)
        {
            energy += energyReceived;
            stack.getTagCompound().setInteger("Energy", energy);
        }
        return energyReceived;
    }
	
    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate)
    {
    	if (!canExtract(stack))
		{
			return 0;
		}
    	
        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("Energy"))
        {
            return 0;
        }
        int energy = stack.getTagCompound().getInteger("Energy");
        int energyExtracted = Math.min(energy, Math.min(this.maxTransfer, maxExtract));
        
        if (!simulate)
        {
            energy -= energyExtracted;
            stack.getTagCompound().setInteger("Energy", energy);
        }
        return energyExtracted;
    }
	
	@Override
	public int getEnergyStored(ItemStack stack)
	{
		if(stack.hasTagCompound())
	    {
			int energy = stack.getTagCompound().getInteger("Energy");
	        return Math.min(getMaxEnergyStored(stack), Math.max(0, energy));
	    }
	    else
	    {
	        return 0;
	    }
	}
	
	@Override
	public int getMaxEnergyStored(ItemStack stack)
	{
		return this.maxEnergy;
	}
	
	@Override
	public boolean canReceive(ItemStack stack)
	{
		return canReceive;
	}
	
	@Override
	public boolean canExtract(ItemStack stack)
	{
		return canExtract;
	}
}