package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.tileentity.TileEntityCompressor;
import com.nhave.dse.utils.NumberUtils;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCompressor extends BlockMachine
{
	public BlockCompressor(String name)
	{
		super(name, true);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState blockState)
	{
		return false;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityCompressor();
	}
	
	@Override
	public boolean doBlockActivate(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
			TileEntityCompressor tile = (TileEntityCompressor) worldIn.getTileEntity(pos);
			if (tile != null && !playerIn.isSneaking())
			{
				playerIn.swingArm(hand);
				return tile.onTileActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
			}
		}
		return super.doBlockActivate(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player)
	{
		if (!world.isRemote)
        {
    		if (world.getTileEntity(blockPos) != null)
    		{
    			TileEntityCompressor tile = (TileEntityCompressor) world.getTileEntity(blockPos);
    			ItemStack stack = tile.getItemStack();
    			if (stack != null) ItemHelper.dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
    		}
        }
		super.onBlockHarvested(world, blockPos, blockState, player);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		super.addInformation(stack, world, tooltip, advanced);
		
		int powerStored = 0;
        NBTTagCompound nbttagcompound = stack.getTagCompound();
		if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag"))
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");
            
            if (nbttagcompound1 != null && nbttagcompound1.hasKey("Energy"))
            {
            	powerStored = nbttagcompound1.getInteger("Energy");
            }
        }
		if (StringUtils.isShiftKeyDown()) tooltip.add(StringUtils.localize("tooltip.dse.charge") + ": " + NumberUtils.getDisplayShort(powerStored) + " / " + NumberUtils.getDisplayShort(ModConfig.machinePowerCapacity) + " " + ModConfig.energyUnit);
	}
	
	@Override
	public String replaceVariables(ItemStack stack, String info)
	{
		info = info.replace("%power%", ModConfig.compressorPowerUsage + " " + ModConfig.energyUnit + "/t");
		return info;
	}
}