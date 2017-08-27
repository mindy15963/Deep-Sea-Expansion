package com.nhave.dse.api.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public interface IItemDataTile
{
	public void writeDataToItemNBT(NBTTagCompound tag);
	
	public void readDataFromItemNBT(NBTTagCompound tag);
}