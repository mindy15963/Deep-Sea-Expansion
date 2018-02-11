package com.nhave.dse.tileentity;

import com.nhave.dse.api.items.IAirTankItem;
import com.nhave.dse.registry.ModConfig;
import com.nhave.nhc.helpers.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityCompressor extends TileEntityEnergyReceiver implements ITickable
{
	private ItemStack item = null;
	
	@Override
    public void update()
    {
    	if (!this.getWorld().isRemote)
    	{
    		if (this.item != null && !this.item.isEmpty() && this.item.getItem() instanceof IAirTankItem)
    		{
    			if (getEnergyStored() >= getPowerUse())
        		{
    				IAirTankItem tank = (IAirTankItem) this.item.getItem();
    				int airNeeded = Math.min(getAirGen(), tank.getMaxOxygen(this.item) - tank.getOxygenStored(this.item));
    				if (airNeeded > 0)
    				{
    					tank.setOxygen(item, tank.getOxygenStored(this.item) + airNeeded);
    					storage.modifyEnergyStored(-getPowerUse());
    					sync();
    				}
        		}
    		}
    	}
    }
	
	public int getPowerUse()
    {
    	return ModConfig.compressorPowerUsage;
    }
    
    public int getAirGen()
    {
    	return ModConfig.compressorAirGeneration;
    }
	
	@Override
	public boolean onTileActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		if ((this.item == null || this.item.isEmpty()) && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof IAirTankItem)
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