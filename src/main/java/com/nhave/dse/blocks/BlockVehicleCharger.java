package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.tileentity.TileEntityVehicleCharger;
import com.nhave.dse.utils.NumberUtils;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVehicleCharger extends BlockMachine
{
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	
	public BlockVehicleCharger(String name)
	{
		super(name, true);
	}
	
	public void onBlockAdded(World world, BlockPos blockPos, IBlockState blockState)
	{
	    setDefaultFacing(world, blockPos, blockState);
	}
	
	private void setDefaultFacing(World world, BlockPos blockPos, IBlockState blockState)
	{
    	if (world.isRemote)
    	{
    		return;
	    }
	    IBlockState faceNorth = world.getBlockState(blockPos.north());
	    IBlockState faceSouth = world.getBlockState(blockPos.south());
	    IBlockState faceWest = world.getBlockState(blockPos.west());
	    IBlockState faceEast = world.getBlockState(blockPos.east());
	    
	    EnumFacing defaultFace = (EnumFacing)blockState.getValue(FACING);
	    if ((defaultFace == EnumFacing.NORTH) && (faceNorth.isFullBlock()) && (!faceSouth.isFullBlock()))
	    {
	    	defaultFace = EnumFacing.SOUTH;
	    }
	    else if ((defaultFace == EnumFacing.SOUTH) && (faceSouth.isFullBlock()) && (!faceNorth.isFullBlock()))
	    {
	    	defaultFace = EnumFacing.NORTH;
	    }
	    else if ((defaultFace == EnumFacing.WEST) && (faceWest.isFullBlock()) && (!faceEast.isFullBlock()))
	    {
	    	defaultFace = EnumFacing.EAST;
	    }
	    else if ((defaultFace == EnumFacing.EAST) && (faceEast.isFullBlock()) && (!faceWest.isFullBlock()))
	    {
	    	defaultFace = EnumFacing.WEST;
	    }
	    world.setBlockState(blockPos, blockState.withProperty(FACING, defaultFace), 2);
	}
	
	public IBlockState onBlockPlaced(World world, BlockPos blockPos, EnumFacing enumFacing, float x, float y, float z, int side, EntityLivingBase entityLiving)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(blockPos, entityLiving).getOpposite());
	}
	
	public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState blockState, EntityLivingBase entityLiving, ItemStack stack)
	{
		//world.setBlockState(blockPos, blockState.withProperty(FACING, entityLiving.getHorizontalFacing().getOpposite()), 2);
		world.setBlockState(blockPos, blockState.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(blockPos, entityLiving)), 2);
	}
	
	public IBlockState getStateFromMeta(int meta)
	{
	    EnumFacing facing = EnumFacing.getFront(meta);
	    /*if (facing.getAxis() == EnumFacing.Axis.Y)
	    {
	    	facing = EnumFacing.NORTH;
	    }*/
	    return getDefaultState().withProperty(FACING, facing);
	}
	
	public int getMetaFromState(IBlockState state)
	{
	    return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	public IBlockState withRotation(IBlockState state, Rotation rotation)
	{
		return state.withProperty(FACING, rotation.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	public IBlockState withMirror(IBlockState state, Mirror mirror)
	{
		return state.withRotation(mirror.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }
	
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	/*public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        this.setDefaultDirection(worldIn, pos, state);
    }
	
    private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
            boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
            boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();

            if (enumfacing == EnumFacing.NORTH && flag && !flag1)
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag)
            {
                enumfacing = EnumFacing.NORTH;
            }
            else
            {
                boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
                boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();

                if (enumfacing == EnumFacing.WEST && flag2 && !flag3)
                {
                    enumfacing = EnumFacing.EAST;
                }
                else if (enumfacing == EnumFacing.EAST && flag3 && !flag2)
                {
                    enumfacing = EnumFacing.WEST;
                }
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
    }
	
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }
	
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();
        
        return i;
    }*/
	
	public boolean doBlockRotation(World world, BlockPos pos, EnumFacing axis)
    {
        IBlockState state = world.getBlockState(pos);
        
        EnumFacing newFacing = state.getValue(FACING);
    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
		else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
		else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
		else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.UP;
		else if (newFacing == EnumFacing.UP) newFacing = EnumFacing.DOWN;
		else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
    	
		IBlockState blockState = world.getBlockState(pos);
    	blockState = blockState.withProperty(FACING, newFacing);
		world.setBlockState(pos, blockState);
        
        return true;
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityVehicleCharger();
	}
	
	@Override
	public boolean doBlockActivate(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(StringUtils.format("[Work In Progress]", StringUtils.RED, StringUtils.BOLD));
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.block." + this.getRegistryName().getResourcePath()), ";", StringUtils.GRAY);
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.block." + this.getRegistryName().getResourcePath() + ".details"), ";", StringUtils.ORANGE);
			
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
			tooltip.add(StringUtils.localize("tooltip.dse.charge") + ": " + NumberUtils.getDisplayShort(powerStored) + " / " + NumberUtils.getDisplayShort(ModConfig.machinePowerCapacity) + " " + ModConfig.energyUnit);
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
}