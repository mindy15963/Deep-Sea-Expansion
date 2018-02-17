package com.nhave.dse.itemblocks;

import java.util.List;

import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockColor extends ItemBlockBase
{
	public ItemBlockColor(Block block)
	{
		super(block);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return StringUtils.localize(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "tile.dse." + stack.getItem().getRegistryName().getResourcePath() + "." + EnumDyeColor.byMetadata(stack.getItemDamage()).getName() + ".name";
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		super.addInformation(stack, world, tooltip, advanced);
	}
}