package com.nhave.dse.itemblocks;

import com.nhave.dse.blocks.BlockBase;
import com.nhave.nhc.api.items.IItemQuality;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlock implements IItemQuality
{
	public ItemBlockBase(Block block)
	{
		super(block);
	}
	
	@Override
	public String getQualityColor(ItemStack stack)
	{
		return (this.getBlock() instanceof BlockBase ? ((BlockBase) this.getBlock()).getQualityColor(stack) : "");
	}
}