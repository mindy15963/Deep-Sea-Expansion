package com.nhave.dse.tileentity;

import com.nhave.dse.api.tileentities.IItemDataTile;
import com.nhave.dse.energy.NHEnergyStorage;
import com.nhave.dse.registry.ModConfig;
import com.nhave.nhc.tiles.TileEntityMachine;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityEnergyStorage extends TileEntityMachine implements IItemDataTile
{
	protected NHEnergyStorage storage;
	
	public TileEntityEnergyStorage()
	{
		storage = new NHEnergyStorage(ModConfig.machinePowerCapacity);
	}
	
	@Override
	public void writeSyncableDataToNBT(NBTTagCompound tag)
	{
		super.writeSyncableDataToNBT(tag);
		writeDataToItemNBT(tag);
	}
	
	public void writeDataToItemNBT(NBTTagCompound tag)
	{
		storage.writeToNBT(tag);
	}
	
	@Override
	public void readSyncableDataFromNBT(NBTTagCompound tag)
	{
		super.readSyncableDataFromNBT(tag);
		readDataFromItemNBT(tag);
	}
	
	public void readDataFromItemNBT(NBTTagCompound tag)
	{
		storage.readFromNBT(tag);
	}
}