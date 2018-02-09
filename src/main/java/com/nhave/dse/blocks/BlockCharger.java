package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.tileentity.TileEntityCharger;
import com.nhave.dse.utils.NumberUtils;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCharger extends BlockMachine
{
	public BlockCharger(String name)
	{
		super(name, false);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityCharger();
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(StringUtils.format("[W.I.P]", StringUtils.RED, StringUtils.BOLD));
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
}