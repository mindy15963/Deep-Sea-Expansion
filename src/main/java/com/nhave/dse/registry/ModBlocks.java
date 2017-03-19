package com.nhave.dse.registry;

import com.nhave.dse.Reference;
import com.nhave.dse.blocks.BlockOyster;
import com.nhave.dse.client.renders.RenderTileOyster;
import com.nhave.dse.tiles.TileEntityOyster;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	public static Block blockOyster;
	
	public static void init()
	{
		blockOyster = new BlockOyster("oyster");
		
		GameRegistry.registerTileEntity(com.nhave.dse.tiles.TileEntityOyster.class, "TileOyster");
	}
	
	public static void register()
	{
		registerBlock(blockOyster, (ItemBlock) new ItemBlock(blockOyster).setMaxStackSize(1));
	}

	public static void registerRenders()
	{
		registerRender(blockOyster);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOyster.class, new RenderTileOyster());
	}
	
	public static void registerBlock(Block block)
	{
		registerBlock(block, new ItemBlock(block));
	}
	
	public static void registerBlock(Block block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock, block.getRegistryName());
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
}