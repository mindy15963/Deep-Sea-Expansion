package com.nhave.dse.tileentity;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.compat.RedstoneFluxCompatibility;
import com.nhave.nhc.helpers.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityCharger extends TileEntityEnergyReceiver implements ITickable
{
	private ItemStack item = null;
	
	@Override
	public void update()
	{
		if (!getWorld().isRemote)
		{
			chargeItem();
		}
	}
	
	public void chargeItem()
	{
		if (this.item == null || this.item.isEmpty())
		{
            return;
        }
		
		if (this.item.hasCapability(CapabilityEnergy.ENERGY, null))
		{
            IEnergyStorage capability = this.item.getCapability(CapabilityEnergy.ENERGY, null);
            int energyStored = getEnergyStored();
        	int energyNeeded = capability.getMaxEnergyStored() - capability.getEnergyStored();
        	int energyToGive = (energyNeeded <= energyStored ? energyNeeded : energyStored);
        	int energyGiven = capability.receiveEnergy(energyToGive, false);
        	storage.modifyEnergyStored(-energyGiven);
		}
		else if (DeepSeaExpansion.redstoneflux && RedstoneFluxCompatibility.isEnergyItem(this.item.getItem()))
		{
            int energyStored = getEnergyStored();
            int energyNeeded = RedstoneFluxCompatibility.getMaxEnergyStored(this.item) - RedstoneFluxCompatibility.getEnergyStored(this.item);
        	int energyToGive = (energyNeeded <= energyStored ? energyNeeded : energyStored);
        	int energyGiven = RedstoneFluxCompatibility.receiveEnergy(this.item, energyToGive, false);
        	storage.modifyEnergyStored(-energyGiven);
		}
	}
	
	@Override
	public boolean onTileActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		if ((this.item == null || this.item.isEmpty()) && !player.getHeldItemMainhand().isEmpty() && isPowerItem(player.getHeldItemMainhand()))
		{
			this.item = player.getHeldItemMainhand().copy();
			this.item.setCount(1);
			player.getHeldItemMainhand().shrink(1);
			sync();
			return true;
		}
		else if (this.item != null)
		{
			ItemHelper.addItemToPlayer(player, item.copy());
			this.item = null;
			sync();
			return true;
		}
		return false;
	}
	
	public boolean isPowerItem(ItemStack stack)
	{
		return (stack.hasCapability(CapabilityEnergy.ENERGY, null) || (DeepSeaExpansion.redstoneflux && RedstoneFluxCompatibility.isEnergyItem(stack.getItem())));
	}
	
	public ItemStack getItemStack()
	{
		return this.item;
	}
	
	public void clearItemStack()
	{
		this.item = null;
		sync();
	}
	
	public void writeSyncableDataToNBT(NBTTagCompound tag)
	{
		super.writeSyncableDataToNBT(tag);
		NBTTagList tagList = new NBTTagList();
		int i = 0;
		if(this.item != null)
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			
			tag1.setByte("Slot", (byte)i);
			this.item.writeToNBT(tag1);
			
			tagList.appendTag(tag1);
		}
		tag.setTag("ITEM", tagList);
	}
	
	public void readSyncableDataFromNBT(NBTTagCompound tag)
	{
		super.readSyncableDataFromNBT(tag);
		NBTTagList items = tag.getTagList("ITEM", tag.getId());

		int i = 0;
		NBTTagCompound item = items.getCompoundTagAt(i);
        int j = item.getByte("Slot");
        ItemStack stack = new ItemStack(item);
        
        this.item = stack.copy();
	}
}
