package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.dse.api.tileentities.IItemDataTile;
import com.nhave.nhc.helpers.ItemHelper;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMachineBase extends BlockMachinePart
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockMachineBase(String name)
	{
		super(name);
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
		return getDefaultState().withProperty(FACING, entityLiving.getHorizontalFacing().getOpposite());
	}
	
	public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState blockState, EntityLivingBase entityLiving, ItemStack stack)
	{
		world.setBlockState(blockPos, blockState.withProperty(FACING, entityLiving.getHorizontalFacing().getOpposite()), 2);
		
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
	
	public IBlockState getStateFromMeta(int p_getStateFromMeta_1_)
	{
	    EnumFacing lvt_2_1_ = EnumFacing.getFront(p_getStateFromMeta_1_);
	    if (lvt_2_1_.getAxis() == EnumFacing.Axis.Y)
	    {
	    	lvt_2_1_ = EnumFacing.NORTH;
	    }
	    return getDefaultState().withProperty(FACING, lvt_2_1_);
	}
	
	public int getMetaFromState(IBlockState p_getMetaFromState_1_)
	{
	    return ((EnumFacing)p_getMetaFromState_1_.getValue(FACING)).getIndex();
	}
	
	public IBlockState withRotation(IBlockState p_withRotation_1_, Rotation p_withRotation_2_)
	{
		return p_withRotation_1_.withProperty(FACING, p_withRotation_2_.rotate((EnumFacing)p_withRotation_1_.getValue(FACING)));
	}
	
	public IBlockState withMirror(IBlockState p_withMirror_1_, Mirror p_withMirror_2_)
	{
		return p_withMirror_1_.withRotation(p_withMirror_2_.toRotation((EnumFacing)p_withMirror_1_.getValue(FACING)));
	}
	
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
	{
		boolean result = false;
		if (world.getTileEntity(pos) != null)
		{
			NBTTagCompound nbt = world.getTileEntity(pos).serializeNBT();
			result = super.rotateBlock(world, pos, axis);
			if (result) world.getTileEntity(pos).deserializeNBT(nbt);
			return result;
		}
		return result;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
			if (!playerIn.getHeldItemMainhand().isEmpty() && ItemHelper.isToolWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ()))
			{
				if (!worldIn.isRemote)
				{
					if (playerIn.isSneaking())
					{
						ItemHelper.dismantleBlock(worldIn, pos, state, playerIn);
						ItemHelper.useWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ());
						return true;
					}
					else
					{
						this.rotateBlock(worldIn, pos, facing);
						ItemHelper.useWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ());
						return true;
					}
				}
				else
				{
					playerIn.playSound(this.blockSoundType.getPlaceSound(), 1.0F, 0.6F);
					playerIn.swingArm(EnumHand.MAIN_HAND);
				}
			}
		}
		return false;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        if (tileentity instanceof IItemDataTile)
        {
        	IItemDataTile tile = (IItemDataTile) tileentity;
        	
            ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            tile.writeDataToItemNBT(nbttagcompound1);
            nbttagcompound.setTag("BlockEntityTag", nbttagcompound1);
            itemstack.setTagCompound(nbttagcompound);
            
            spawnAsEntity(worldIn, pos, itemstack);
        }
        
        super.breakBlock(worldIn, pos, state);
    }
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new java.util.ArrayList<ItemStack>();
	}
}