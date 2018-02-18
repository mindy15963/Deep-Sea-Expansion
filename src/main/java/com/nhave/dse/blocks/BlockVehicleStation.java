package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.dse.helpers.UpgradeHelper;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVehicleStation extends BlockMachine
{
	public BlockVehicleStation(String name)
	{
		super(name, true);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState blockState)
	{
		return false;
	}
	
	@Override
	public boolean doBlockRotation(World world, BlockPos pos, EnumFacing axis)
	{
		return false;
	}
	
	@Override
	public boolean doBlockActivate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
	        EnumFacing facing = state.getValue(FACING);
			net.minecraft.util.math.AxisAlignedBB EXTENT_AABB = getEntityDetectionRange(pos, facing);
			
			List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(null, EXTENT_AABB);
			for (int i = 0; i < Math.min(entities.size(), 9); ++i)
			{
				if (UpgradeHelper.handleEntityUpgrade(world, pos, state, entities.get(i), player)) return !world.isRemote;
			}
			if (ItemHelper.isToolWrench(player, player.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ()))
			{
				if (super.doBlockRotation(world, pos, facing))
				{
					if (!world.isRemote)
					{
						ItemHelper.useWrench(player, player.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ());
						return true;
					}
					else
					{
						player.playSound(this.blockSoundType.getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(StringUtils.format("[Work In Progress]", StringUtils.RED, StringUtils.BOLD));
		super.addInformation(stack, world, tooltip, advanced);
	}
	
	public net.minecraft.util.math.AxisAlignedBB getEntityDetectionRange(BlockPos pos, EnumFacing side)
	{
		switch (side)
		{
			case UP:
				return new net.minecraft.util.math.AxisAlignedBB(pos.getX() - 1, pos.getY() + 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 4, pos.getZ() + 1);
			case DOWN:
				return new net.minecraft.util.math.AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() - 4, pos.getZ() + 1);
			case SOUTH:
				return new net.minecraft.util.math.AxisAlignedBB(pos.getX() - 1, pos.getY() + 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() - 1, pos.getZ() - 4);
			case WEST:
				return new net.minecraft.util.math.AxisAlignedBB(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1, pos.getX() + 4, pos.getY() - 1, pos.getZ() - 1);
			case NORTH:
				return new net.minecraft.util.math.AxisAlignedBB(pos.getX() - 1, pos.getY() + 1, pos.getZ() + 1, pos.getX() + 1, pos.getY() - 1, pos.getZ() + 4);
			case EAST:
				return new net.minecraft.util.math.AxisAlignedBB(pos.getX() - 1, pos.getY() + 1, pos.getZ() + 1, pos.getX() - 4, pos.getY() - 1, pos.getZ() - 1);
			default:
				break;
		}
		return new net.minecraft.util.math.AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
	}
}