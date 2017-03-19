package com.nhave.dse.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityOyster extends TileEntity
{
	private ItemStack item = null;
	
	public boolean onTileActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		System.out.print("test");
		if (this.item == null && player.getHeldItemMainhand() != null)
		{
			this.item = player.getHeldItemMainhand().copy();
			this.item.setCount(1);
			player.getHeldItemMainhand().shrink(1);
			sync();
			return true;
		}
		else if (this.item != null)
		{
			addItemToPlayer(player, item.copy());
			this.item = null;
			sync();
			return true;
		}
		return false;
	}
	
	public static void addItemToPlayer(EntityPlayer player, ItemStack stack)
	{
		if (!player.inventory.addItemStackToInventory(stack))
		{
			if (!player.world.isRemote) player.entityDropItem(stack, 1F);
		}
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
}