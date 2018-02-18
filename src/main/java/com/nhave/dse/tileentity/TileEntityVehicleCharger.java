package com.nhave.dse.tileentity;

import java.util.List;

import com.nhave.dse.entity.EntityMotorboat;
import com.nhave.dse.registry.ModConfig;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityVehicleCharger extends TileEntityEnergyReceiver implements ITickable
{
	@Override
	public void update()
	{
		if (getWorld().isRemote) return;
		
		IBlockState state = world.getBlockState(pos);
        EnumFacing facing = state.getValue(BlockDirectional.FACING);
		net.minecraft.util.math.AxisAlignedBB EXTENT_AABB = getEntityDetectionRange(facing);
		
		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(null, EXTENT_AABB);
		for (int i = 0; i < Math.min(entities.size(), 9); ++i)
		{
			if (entities.get(i) instanceof EntityMotorboat)
			{
				EntityMotorboat boat = (EntityMotorboat) entities.get(i);
				int powerStored = boat.getPowerStored();
				int powerNeeded = boat.getMaxPowerStored() - boat.getPowerStored();
				int powerToGive =  Math.min(Math.min(getEnergyStored(), powerNeeded), ModConfig.boatPowerTransfer);
				if (powerToGive > 0)
				{
					boat.setPowerStored(powerStored + powerToGive);
					storage.modifyEnergyStored(-powerToGive);
					return;
				}
			}
		}
	}
	
	public net.minecraft.util.math.AxisAlignedBB getEntityDetectionRange(EnumFacing side)
	{
		switch (side)
		{
			case UP:
				return new net.minecraft.util.math.AxisAlignedBB(this.pos.getX() - 1, this.pos.getY() + 1, this.pos.getZ() - 1, this.pos.getX() + 1, this.pos.getY() + 4, this.pos.getZ() + 1);
			case DOWN:
				return new net.minecraft.util.math.AxisAlignedBB(this.pos.getX() - 1, this.pos.getY() - 1, this.pos.getZ() - 1, this.pos.getX() + 1, this.pos.getY() - 4, this.pos.getZ() + 1);
			case NORTH:
				return new net.minecraft.util.math.AxisAlignedBB(this.pos.getX() - 1, this.pos.getY() + 1, this.pos.getZ() - 1, this.pos.getX() + 1, this.pos.getY() - 1, this.pos.getZ() - 4);
			case EAST:
				return new net.minecraft.util.math.AxisAlignedBB(this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() + 1, this.pos.getX() + 4, this.pos.getY() - 1, this.pos.getZ() - 1);
			case SOUTH:
				return new net.minecraft.util.math.AxisAlignedBB(this.pos.getX() - 1, this.pos.getY() + 1, this.pos.getZ() + 1, this.pos.getX() + 1, this.pos.getY() - 1, this.pos.getZ() + 4);
			case WEST:
				return new net.minecraft.util.math.AxisAlignedBB(this.pos.getX() - 1, this.pos.getY() + 1, this.pos.getZ() + 1, this.pos.getX() - 4, this.pos.getY() - 1, this.pos.getZ() - 1);
			default:
				break;
		}
		return new net.minecraft.util.math.AxisAlignedBB(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX(), this.pos.getY(), this.pos.getZ());
	}
}