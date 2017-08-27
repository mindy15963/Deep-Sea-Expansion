package com.nhave.dse.blocks;

import java.util.List;

import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMachinePart extends BlockBase
{
	private String developmentTag;
	private String quality;
	
	public BlockMachinePart(String name)
	{
		super(name, Material.IRON);
		this.setHardness(50F);
	}
	
	public BlockMachinePart setDevTag(String tag)
	{
		this.developmentTag = tag;
		return this;
	}
	
	public BlockMachinePart setQuality(String quality)
	{
		this.quality = quality;
		return this;
	}
	
	@Override
	public String getQualityColor(ItemStack stack)
	{
		return (this.quality == null ? "" : this.quality);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced)
	{
		if (this.developmentTag != null && this.developmentTag.equals("NYI")) list.add(StringUtils.format("[" + StringUtils.localize("tooltip.dse.block.nyi") + "]", StringUtils.LIGHT_RED, StringUtils.ITALIC));
		else if (this.developmentTag != null && this.developmentTag.equals("WIP")) list.add(StringUtils.format("[" + StringUtils.localize("tooltip.dse.block.wip") + "]", StringUtils.LIGHT_RED, StringUtils.ITALIC));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
			if (ItemHelper.isToolWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ()))
			{
				if (playerIn.isSneaking())
				{
					if (!worldIn.isRemote)
					{
						ItemHelper.dismantleBlock(worldIn, pos, state, playerIn);
						ItemHelper.useWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ());
						return true;
					}
					else
					{
						playerIn.playSound(this.blockSoundType.getPlaceSound(), 1.0F, 0.6F);
						playerIn.swingArm(EnumHand.MAIN_HAND);
					}
				}
			}
		}
		return false;
	}
}