package com.nhave.dse.registry;

import com.nhave.dse.Reference;
import com.nhave.dse.blocks.BlockCompressor;
import com.nhave.dse.blocks.BlockMachinePart;
import com.nhave.dse.blocks.BlockOyster;
import com.nhave.dse.client.renders.RenderTileCompressor;
import com.nhave.dse.client.renders.RenderTileOyster;
import com.nhave.dse.itemblocks.ItemBlockBase;
import com.nhave.dse.tiles.TileEntityCompressor;
import com.nhave.dse.tiles.TileEntityOyster;
import com.nhave.nhc.util.StringUtils;

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
	public static Block blockAirCompressor;
	public static Block blockMachineFrame;
	public static Block blockHeatVent;
	public static Block blockHeatConductor;
	public static Block blockPowerTap;
	public static Block blockMachineFrameAdvanced;
	public static Block blockAirCompressorAdvanced;
	
	public static void init()
	{
		blockOyster = new BlockOyster("oyster");
		blockMachineFrame = new BlockMachinePart("machineframe");
		blockAirCompressor = new BlockCompressor("compressor", false).setDevTag("WIP");
		blockHeatVent = new BlockMachinePart("heatvent").setDevTag("NYI");
		blockHeatConductor = new BlockMachinePart("heatconductor").setDevTag("NYI");
		blockPowerTap = new BlockMachinePart("powertap").setDevTag("NYI");
		blockMachineFrameAdvanced = new BlockMachinePart("machineframe_advanced").setDevTag("NYI").setQuality(StringUtils.PURPLE);
		blockAirCompressorAdvanced = new BlockCompressor("compressor_advanced", true).setDevTag("WIP").setQuality(StringUtils.PURPLE);

		GameRegistry.registerTileEntity(com.nhave.dse.tiles.TileEntityOyster.class, "TileOyster");
		GameRegistry.registerTileEntity(com.nhave.dse.tiles.TileEntityCompressor.class, "TileCompressor");
		GameRegistry.registerTileEntity(com.nhave.dse.tiles.TileEntityCompressorAdvanced.class, "TileCompressorAdvanced");
	}
	
	public static void register()
	{
		registerBlock(blockOyster).setMaxStackSize(1);
		registerBlock(blockMachineFrame);
		registerBlock(blockAirCompressor);
		registerBlock(blockHeatVent);
		registerBlock(blockHeatConductor);
		registerBlock(blockPowerTap);
		registerBlock(blockMachineFrameAdvanced);
		registerBlock(blockAirCompressorAdvanced);
	}

	public static void registerRenders()
	{
		registerRender(blockOyster);
		registerRender(blockMachineFrame);
		registerRender(blockAirCompressor);
		registerRender(blockHeatVent);
		registerRender(blockHeatConductor);
		registerRender(blockPowerTap);
		registerRender(blockMachineFrameAdvanced);
		registerRender(blockAirCompressorAdvanced);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOyster.class, new RenderTileOyster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompressor.class, new RenderTileCompressor());
	}
	
	public static ItemBlockBase registerBlock(Block block)
	{
		ItemBlockBase itemBlock = new ItemBlockBase(block);
		registerBlock(block, itemBlock);
		return itemBlock;
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