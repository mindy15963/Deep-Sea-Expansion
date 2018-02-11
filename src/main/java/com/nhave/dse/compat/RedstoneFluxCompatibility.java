package com.nhave.dse.compat;

import cofh.redstoneflux.api.IEnergyConnection;
import cofh.redstoneflux.api.IEnergyContainerItem;
import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class RedstoneFluxCompatibility
{
	public static boolean isEnergyHandler(TileEntity tile)
	{
        return tile instanceof IEnergyHandler;
    }
	
    public static boolean isEnergyReceiver(TileEntity tile)
    {
        return tile instanceof IEnergyReceiver;
    }
    
    public static int getEnergyStored(TileEntity tile)
    {
        return ((IEnergyHandler) tile).getEnergyStored(EnumFacing.DOWN);
    }
    
    public static int getEnergyStored(TileEntity tile, EnumFacing side)
    {
        return ((IEnergyHandler) tile).getEnergyStored(side);
    }
    
    public static int getMaxEnergyStored(TileEntity tile)
    {
        return ((IEnergyHandler) tile).getMaxEnergyStored(EnumFacing.DOWN);
    }
    
    public static int getMaxEnergyStored(TileEntity tile, EnumFacing side)
    {
        return ((IEnergyHandler) tile).getMaxEnergyStored(side);
    }
    
    public static int receiveEnergy(TileEntity tile, EnumFacing from, int maxReceive)
    {
        return ((IEnergyReceiver) tile).receiveEnergy(from, maxReceive, false);
    }
    
    public static boolean isEnergyConnection(TileEntity tile)
    {
        return tile instanceof IEnergyConnection;
    }
    
    public static boolean canConnectEnergy(TileEntity tile, EnumFacing facing)
    {
        return ((IEnergyConnection)tile).canConnectEnergy(facing);
    }
    
    public static boolean isEnergyItem(Item item)
    {
        return item instanceof IEnergyContainerItem;
    }
    
    public static int getEnergyStored(ItemStack stack)
    {
    	return ((IEnergyContainerItem) stack.getItem()).getEnergyStored(stack);
    }
    
    public static int getMaxEnergyStored(ItemStack stack)
    {
    	return ((IEnergyContainerItem) stack.getItem()).getMaxEnergyStored(stack);
    }
    
    public static int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate)
    {
        return ((IEnergyContainerItem) stack.getItem()).receiveEnergy(stack, maxReceive, simulate);
    }
    
    public static int extractEnergy(ItemStack stack, int maxExtract, boolean simulate)
    {
        return ((IEnergyContainerItem) stack.getItem()).extractEnergy(stack, maxExtract, simulate);
    }
}