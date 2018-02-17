package com.nhave.dse.itemblocks;

import com.nhave.nhc.api.items.IItemQuality;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlock implements IItemQuality
{
	private String quality = "";
	
	public ItemBlockBase(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setNoRepair();
	}
	
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
	
	public ItemBlockBase(Block block, String quality)
	{
		this(block);
		this.quality = quality;
	}
	
	public String getQualityColor(ItemStack stack)
	{
		return this.quality;
	}
}