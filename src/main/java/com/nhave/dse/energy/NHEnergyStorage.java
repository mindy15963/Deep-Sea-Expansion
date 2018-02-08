package com.nhave.dse.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class NHEnergyStorage extends EnergyStorage
{
    public NHEnergyStorage(int capacity)
    {
        super(capacity);
    }
    
    public NHEnergyStorage(int capacity, int maxTransfer)
    {
        super(capacity, maxTransfer);
    }
    
    public NHEnergyStorage(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);
    }
    
    public NHEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy)
    {
        super(capacity, maxReceive, maxExtract, energy);
    }
    
    public void modifyEnergyStored(int energy)
    {
        this.energy += energy;
        
        if (this.energy > capacity)
        {
            this.energy = capacity;
        }
        else if (this.energy < 0)
        {
            this.energy = 0;
        }
    }
    
    public void setMaxReceive(int max)
    {
        this.maxReceive = max;
    }

    public void setMaxExtract(int max)
    {
        this.maxExtract = max;
    }
    
    public NHEnergyStorage readFromNBT(NBTTagCompound nbt)
    {
        this.energy = nbt.getInteger("Energy");
        
        if (energy > capacity)
        {
            energy = capacity;
        }
        return this;
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        if (energy < 0)
        {
            energy = 0;
        }
        nbt.setInteger("Energy", energy);
        return nbt;
    }
}