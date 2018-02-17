package com.nhave.dse.registry;

import com.nhave.dse.Reference;
import com.nhave.dse.blocks.BlockCharger;
import com.nhave.dse.blocks.BlockColoredMetal;
import com.nhave.dse.blocks.BlockCompressor;
import com.nhave.dse.client.render.RenderTileCharger;
import com.nhave.dse.client.render.RenderTileCompressor;
import com.nhave.dse.itemblocks.ItemBlockBase;
import com.nhave.dse.itemblocks.ItemBlockColor;
import com.nhave.dse.tileentity.TileEntityCharger;
import com.nhave.dse.tileentity.TileEntityCompressor;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks
{
	public static Block blockCompressor;
	public static Block blockCharger;
	public static Block blockMetalColored;
	
	public static void init()
	{
		blockCompressor = new BlockCompressor("compressor");
		blockCharger = new BlockCharger("charger");
		
		blockMetalColored = new BlockColoredMetal("coloredmetal", Material.IRON);

		GameRegistry.registerTileEntity(com.nhave.dse.tileentity.TileEntityCompressor.class, "TileCompressor");
		GameRegistry.registerTileEntity(com.nhave.dse.tileentity.TileEntityCharger.class, "TileCharger");
	}
	
	public static void register(Register<Block> event)
	{
		event.getRegistry().register(blockCompressor);
		event.getRegistry().register(blockCharger);
		event.getRegistry().register(blockMetalColored);
	}
	
	public static void registerItemBlocks(Register<Item> event)
	{
		registerItemBlock(event, blockCompressor);
		registerItemBlock(event, blockCharger);
		event.getRegistry().register(new ItemBlockColor(blockMetalColored).setRegistryName(blockMetalColored.getRegistryName()));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		registerRender(blockCompressor);
		registerRender(blockCharger);
		registerColorRender(blockMetalColored);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderData()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompressor.class, new RenderTileCompressor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCharger.class, new RenderTileCharger());
	}
	
	public static void registerItemBlock(Register<Item> event, Block block)
	{
		event.getRegistry().register(new ItemBlockBase(block, StringUtils.LIGHT_BLUE).setRegistryName(block.getRegistryName()));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerColorRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		
		for (int i = 0; i < EnumDyeColor.values().length; i++)
		{
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "color=" + EnumDyeColor.byMetadata(i).getName()));
		}
	}
}