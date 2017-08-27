package com.nhave.dse.blocks;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockBase extends Block
{
	public BlockBase(String name, Material materialIn)
	{
		super(materialIn);
		this.setRegistryName(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETABBLOCKS);
		this.setUnlocalizedName(Reference.MODID + "." + name);
	}
	
	public String getQualityColor(ItemStack stack)
	{
		return "";
	}
}