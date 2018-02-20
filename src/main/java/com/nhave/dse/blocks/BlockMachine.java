package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;
import com.nhave.dse.api.tileentities.IItemDataTile;
import com.nhave.nhc.blocks.BlockMachineBase;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMachine extends BlockMachineBase
{
	public BlockMachine(String name, boolean directional)
	{
		super(name, directional);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETAB);
		this.setUnlocalizedName(Reference.MODID + "." + name);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		String descUnlocalized = "tooltip.dse.block." + this.getRegistryName().getResourcePath();
		String descLocalized = StringUtils.localize(descUnlocalized);
		
		String descFinal = replaceVariables(stack, descLocalized);
		if (StringUtils.isShiftKeyDown()) TooltipHelper.addSplitString(tooltip, descFinal, ";", StringUtils.GRAY);
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	public String replaceVariables(ItemStack stack, String info)
	{
		return info;
	}
	
	public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState blockState, EntityLivingBase entityLiving, ItemStack stack)
	{
		super.onBlockPlacedBy(world, blockPos, blockState, entityLiving, stack);
		
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag"))
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");
            
            TileEntity tileentity = world.getTileEntity(blockPos);
            
            if (tileentity instanceof IItemDataTile)
            {
            	IItemDataTile tile = (IItemDataTile) tileentity;
            	tile.readDataFromItemNBT(nbttagcompound1);
            }
        }
	}
	
	/*public void onBlockHarvested(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			TileEntity tileentity = world.getTileEntity(blockPos);
			
	        ItemStack itemstack = new ItemStack(this);
	        
	        if (tileentity instanceof IItemDataTile)
	        {
	        	IItemDataTile tile = (IItemDataTile) tileentity;
	        	
	            NBTTagCompound nbttagcompound = new NBTTagCompound();
	            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            tile.writeDataToItemNBT(nbttagcompound1);
	            nbttagcompound.setTag("BlockEntityTag", nbttagcompound1);
	            itemstack.setTagCompound(nbttagcompound);
	        }
	        spawnAsEntity(world, blockPos, itemstack);
		}
        
		super.onBlockHarvested(world, blockPos, blockState, player);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new java.util.ArrayList<ItemStack>();
	}*/
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
		TileEntity tileentity = world.getTileEntity(pos);
		
        ItemStack itemstack = new ItemStack(this);
        
        if (tileentity instanceof IItemDataTile)
        {
        	IItemDataTile tile = (IItemDataTile) tileentity;
        	
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            tile.writeDataToItemNBT(nbttagcompound1);
            nbttagcompound.setTag("BlockEntityTag", nbttagcompound1);
            itemstack.setTagCompound(nbttagcompound);
        }
        
        drops.add(itemstack);
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		
        ItemStack itemstack = super.getPickBlock(state, target, world, pos, player);
        
        if (tileentity instanceof IItemDataTile)
        {
        	IItemDataTile tile = (IItemDataTile) tileentity;
        	
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            tile.writeDataToItemNBT(nbttagcompound1);
            nbttagcompound.setTag("BlockEntityTag", nbttagcompound1);
            itemstack.setTagCompound(nbttagcompound);
        }
		return itemstack;
	}
}