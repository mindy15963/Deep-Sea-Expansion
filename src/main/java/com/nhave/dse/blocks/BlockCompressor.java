package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.dse.api.items.IAirTank;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.tiles.TileEntityCompressor;
import com.nhave.dse.tiles.TileEntityCompressorAdvanced;
import com.nhave.dse.util.PowerUtils;
import com.nhave.nhc.api.blocks.IHudBlock;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCompressor extends BlockMachineBase implements IHudBlock
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private boolean advanced;
	
	public BlockCompressor(String name, boolean advanced)
	{
		super(name);
		this.advanced = advanced;
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
		return this.advanced ? new TileEntityCompressorAdvanced() : new TileEntityCompressor();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		boolean result = super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		if (result) return true;
		
		if (hand == EnumHand.MAIN_HAND)
		{
			TileEntityCompressor tile = (TileEntityCompressor) worldIn.getTileEntity(pos);
			if (facing == EnumFacing.UP && tile != null && !playerIn.isSneaking() && worldIn.isAirBlock(pos.up()))
			{
				playerIn.swingArm(hand);
				return tile.onTileActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
			}
		}
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!worldIn.isRemote)
        {
    		if (worldIn.getTileEntity(pos) != null && !worldIn.isAirBlock(pos.up(1)))
    		{
    			TileEntityCompressor tile = (TileEntityCompressor) worldIn.getTileEntity(pos);
    			ItemStack stack = tile.getItemStack();
    			if (stack != null && !stack.isEmpty()) ItemHelper.dropBlockAsItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
    			tile.clearItemStack();
    		}
        }
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
    			if (stack != null && !stack.isEmpty()) ItemHelper.dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
    			tile.clearItemStack();
    		}
        }
	}

	@Override
	public void addHudInfo(World world, BlockPos pos, IBlockState state, List list)
	{
		TileEntityCompressor tile = (TileEntityCompressor) world.getTileEntity(pos);
		if (tile != null)
		{
			list.add(StringUtils.format(getLocalizedName(), StringUtils.YELLOW, StringUtils.ITALIC));
			list.add("Charge: " + StringUtils.format(PowerUtils.getEnergyDisplay(tile.getEnergyStored()) + " / " + PowerUtils.getEnergyDisplay(tile.getMaxEnergyStored()), StringUtils.YELLOW, StringUtils.ITALIC));
			
			ItemStack stack = tile.getItemStack();
			if (stack != null && !stack.isEmpty() && stack.getItem() instanceof IAirTank)
			{
				IAirTank tank = (IAirTank) stack.getItem();
				list.add(StringUtils.localize("tooltip.nhc.toolstation.item") + ": " + StringUtils.format(StringUtils.limitString(stack.getDisplayName(), 20), StringUtils.YELLOW, StringUtils.ITALIC));
				if (ModConfig.displayPressure && tank.getPSI(stack) > 0)
				{
					if (ModConfig.pressureUnit.equals("PSI")) list.add("pressure: " + StringUtils.format(getPercentage(tank.getPSI(stack), tank.getO2(stack), tank.getMaxO2(stack)) + "psi / " + tank.getPSI(stack) + "psi", StringUtils.YELLOW, StringUtils.ITALIC));
					else
					{
						int bar = (int) (Math.round(tank.getPSI(stack) * 0.0689475729D));
						list.add("pressure: " + StringUtils.format(getPercentage(bar, tank.getO2(stack), tank.getMaxO2(stack)) + "bar / " + bar + "bar", StringUtils.YELLOW, StringUtils.ITALIC));
					}
				}
			}
		}
	}
	
	private int getPercentage(int draw, int current, int max)
	{
		double percentage = ((double) current / (double) max) * 100D;
		double result = Math.ceil(((double) draw / 100D) * percentage);
		return (int) result;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced)
	{
		super.addInformation(stack, player, list, advanced);
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
		list.add(StringUtils.localize("tooltip.dse.charge") + ": " + PowerUtils.getEnergyDisplay(powerStored) + " / " + PowerUtils.getEnergyDisplay((this.advanced ? 70000 : 50000)));
	}
}