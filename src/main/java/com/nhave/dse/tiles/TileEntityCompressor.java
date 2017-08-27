package com.nhave.dse.tiles;

import com.nhave.dse.api.items.IAirTank;
import com.nhave.dse.api.tileentities.IItemDataTile;
import com.nhave.nhc.helpers.ItemHelper;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityCompressor extends TileEntity implements IEnergyReceiver, IEnergyStorage, ITickable, IItemDataTile
{
    private ItemStack item = null;
    protected EnergyStorage storage;
    
    public TileEntityCompressor()
	{
    	storage = new EnergyStorage(getPowerStorage());
        storage.setMaxReceive((int) (getPowerStorage() * 0.01));
	}
    
    @Override
    public void update()
    {
    	if (!this.getWorld().isRemote)
    	{
    		if (this.item != null && !this.item.isEmpty() && this.item.getItem() instanceof IAirTank)
    		{
    			if (getEnergyStored() >= getPowerUse())
        		{
    				IAirTank tank = (IAirTank) this.item.getItem();
    				int airNeeded = Math.min(getAirGen(), tank.getMaxO2(this.item) - tank.getO2(this.item));
    				if (airNeeded > 0)
    				{
    					tank.setO2(item, tank.getO2(this.item) + airNeeded);
    					storage.extractEnergy(getPowerUse(), false);
    					sync();
    				}
        		}
    		}
    	}
    }
    
    public int getPowerStorage()
    {
    	return 50000;
    }
    
    public int getPowerUse()
    {
    	return 100;
    }
    
    public int getAirGen()
    {
    	return 20;
    }
	
	public boolean onTileActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		if ((this.item == null || this.item.isEmpty()) && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof IAirTank)
		{
			this.item = player.getHeldItemMainhand().copy();
			this.item.setCount(1);
			player.getHeldItemMainhand().shrink(1);
			sync();
			return true;
		}
		else if (this.item != null && !this.item.isEmpty())
		{
			ItemHelper.addItemToPlayer(player, item.copy());
			this.item = null;
			sync();
			return true;
		}
		return false;
	}
	
	private void sync()
	{
		world.notifyBlockUpdate(this.pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		markDirty();
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
	
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeSyncableDataToNBT(tag);
		return tag;
	}

	public void writeSyncableDataToNBT(NBTTagCompound tag)
	{
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
		
		writeDataToItemNBT(tag);
	}
	

	public void writeDataToItemNBT(NBTTagCompound tag)
	{
		storage.writeToNBT(tag);
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readSyncableDataFromNBT(tag);
	}
	
	public void readSyncableDataFromNBT(NBTTagCompound tag)
	{
		NBTTagList items = tag.getTagList("ITEM", tag.getId());

		int i = 0;
		NBTTagCompound item = items.getCompoundTagAt(i);
        int j = item.getByte("Slot");
        ItemStack stack = new ItemStack(item);
        
        this.item = stack.copy();
        
        readDataFromItemNBT(tag);
	}
	
	public void readDataFromItemNBT(NBTTagCompound tag)
	{
        storage.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound p_handleUpdateTag_1_)
	{
		readSyncableDataFromNBT(p_handleUpdateTag_1_);
		super.handleUpdateTag(p_handleUpdateTag_1_);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeSyncableDataToNBT(syncData);
		return new SPacketUpdateTileEntity(this.pos, 3, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readSyncableDataFromNBT(pkt.getNbtCompound());
	}

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY)
        {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }

	/* =========================================================== Interface: IEnergyStorage ===============================================================*/
	
	@Override
    public int receiveEnergy(int maxReceive, boolean simulate)
	{
		sync();
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return 0;
    }

    @Override
    public int getEnergyStored()
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored()
    {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract()
    {
        return false;
    }

    @Override
    public boolean canReceive()
    {
        return true;
    }

	/* =========================================================== Interface: IEnergyReceiver ===============================================================*/
	
	@Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
	{
        return receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from)
    {
        return getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from)
    {
        return getMaxEnergyStored();
    }
	
	@Override
	public boolean canConnectEnergy(EnumFacing from)
	{
		return from != EnumFacing.UP;
	}
}