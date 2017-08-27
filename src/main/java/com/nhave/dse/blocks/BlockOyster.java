package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.dse.tiles.TileEntityOyster;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOyster extends BlockBase
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockOyster(String name)
	{
		super(name, Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(2F);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
	{
		list.add(StringUtils.format("[" + StringUtils.localize("tooltip.dse.block.nyi") + "]", StringUtils.LIGHT_RED, StringUtils.ITALIC));
	}
	
	public String getQualityColor(ItemStack stack)
	{
		return StringUtils.LIGHT_BLUE;
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
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState blockState)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState)
	{
		return new TileEntityOyster();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.OFF_HAND) return false;
		TileEntityOyster tile = (TileEntityOyster) world.getTileEntity(pos);
		if (tile != null && !player.isSneaking())
		{
			player.swingArm(hand);
			return tile.onTileActivated(world, pos.getX(), pos.getY(), pos.getZ(), player);
		}
		return false;
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player)
	{
		if (!world.isRemote)
        {
    		if (world.getTileEntity(blockPos) != null)
    		{
    			TileEntityOyster tile = (TileEntityOyster) world.getTileEntity(blockPos);
    			ItemStack stack = tile.getItemStack();
    			if (stack != null) dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
    		}
        }
	}

	public void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack)
	{
		float f = 0.3F;
    	double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	EntityItem theItem = new EntityItem(world, x + x2, y + y2, z + z2, stack);
    	theItem.setDefaultPickupDelay();
    	world.spawnEntity(theItem);
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
	public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return 0;
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		IBlockState offset = world.getBlockState(pos.offset(face));
		Block block = offset.getBlock();
		if (block != null && block.getMaterial(offset) == Material.WATER) return true;
		return super.doesSideBlockRendering(state, world, pos, face);
	}
}