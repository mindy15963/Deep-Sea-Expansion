package com.nhave.dse.api.items;

import net.minecraft.item.ItemStack;

public interface IEnergyItem
{
	int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate);
	
    int extractEnergy(ItemStack stack, int maxExtract, boolean simulate);
    
    int getEnergyStored(ItemStack stack);
    
    int getMaxEnergyStored(ItemStack stack);
    
    boolean canReceive(ItemStack stack);
    
    boolean canExtract(ItemStack stack);
}