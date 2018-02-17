package com.nhave.dse.blocks;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;

import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockColoredMetal extends BlockColored
{
	public String name;
	
	public BlockColoredMetal(String name, Material materialIn)
	{
		super(materialIn);
		this.setRegistryName(name);
		this.setCreativeTab(DeepSeaExpansion.CREATIVETAB);
		this.setUnlocalizedName(Reference.MODID + "." + name);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setHarvestLevel("pickaxe", 1);
		this.name = name;
	}
	
	@SideOnly (Side.CLIENT)
	public void registerModels()
	{
		for (int i = 0; i < EnumDyeColor.values().length; i++)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(Reference.MODID + ":" + name, "color=" + EnumDyeColor.byDyeDamage(i)));
		}
	}
}