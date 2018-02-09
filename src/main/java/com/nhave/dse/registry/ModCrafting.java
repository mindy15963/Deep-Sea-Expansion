package com.nhave.dse.registry;

import com.nhave.dse.Reference;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModCrafting
{
	public static final String[] OREDICT = new String[] {"dyeBlack", "dyeRed",  "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	private static int recipe = 0;
	
	public static void register(Register<IRecipe> event)
	{
		Block machineFrame = com.nhave.nhc.registry.ModBlocks.blockMachineFrame;
		Item energyPearl = com.nhave.nhc.registry.ModItems.itemEnergyPearl;
		
		//Shaped Crafting
		//Air Compressor
		addRecipe(event, new ShapedOreRecipe(null, ModBlocks.blockCompressor,
			new Object[] {"XXX", "YZY", "XAX",
			'X', "plateIron",
			'Y', Blocks.FURNACE,
			'Z', ModItems.createItemStack(ModItems.itemComponents, "oxygenfilter", 1),
			'A', machineFrame}));
		//Diving Goggles
		addRecipe(event, new ShapedOreRecipe(null, ModItems.itemDivingGoggles,
			new Object[] {"XXX", "YYY", "XXX",
			'X', "materialRubber",
			'Y', "paneGlassColorless"}));
		//Flippers - All 16 colors
		for (int i = 0; i < OREDICT.length; ++i)
		{
			addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemFlippers, 1, i),
				new Object[] {"X X", "Y Y", "Y Y",
				'X', OREDICT[i],
				'Y', "materialRubber"}));
		}
		//Small Air Tank
		addRecipe(event, new ShapedOreRecipe(null, ModItems.itemAirTankSmall,
			new Object[] {"XYX", "YZY", "YAY",
			'X', "materialRubber",
			'Y', "plateIron",
			'Z', ModItems.createItemStack(ModItems.itemComponents, "oxygenfilter", 1),
			'A', "plateDenseIron"}));
		//Large Air Tank
		addRecipe(event, new ShapedOreRecipe(null, ModItems.itemAirTankLarge,
			new Object[] {"XYX", "ZXZ", "A A",
			'X', "materialRubber",
			'Y', "plateSteel",
			'Z', ModItems.itemAirTankSmall,
			'A', "plateIron"}));
		//High Pressure Air Tank
		addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemAirTankHighPressure),
			new Object[] {"XYX", "XZX", "XYX",
			'X', "platePlasteel",
			'Y', "plateDensePlasteel",
			'Z', ModItems.itemAirTankLarge}));
		//Dinghy
		addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemDinghy),
			new Object[] {"X X", "XXX",
			'X', "materialRubber"}));
		
		//Shaders
		//Shader Core
		addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemShaderCore),
			new Object[] {"XYX", "YZY", "XYX",
			'X', Items.GOLD_NUGGET,
			'Y', Items.REDSTONE,
			'Z', energyPearl}));
		//Crimson Shader
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemShader, "crimson", 1),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "dyeBlack",
			'Y', Items.BLAZE_POWDER,
			'Z', ModItems.itemShaderCore}));
		//Red Shader
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemShader, "race", 1),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "dyeBlue",
			'Y', "dyeWhite",
			'Z', ModItems.itemShaderCore}));
		//Blue Shader
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemShader, "muscle", 1),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "dyeRed",
			'Y', "dyeWhite",
			'Z', ModItems.itemShaderCore}));
		//Dark Shader
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemShader, "dark", 1),
			new Object[] {"XXX", "XYX", "XXX",
			'X', "dyeBlack",
			'Y', ModItems.itemShaderCore}));
		//Pink Shader
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemShader, "pink", 1),
			new Object[] {"XXX", "XYX", "XXX",
			'X', "dyePink",
			'Y', ModItems.itemShaderCore}));
		//Space Shader
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemShader, "space", 1),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "dyeWhite",
			'Y', "dyeOrange",
			'Z', ModItems.itemShaderCore}));
		
		//Air Filter
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemComponents, "oxygenfilter", 1),
			new Object[] {"XYX",
			'X', "plateIron",
			'Y', new ItemStack(Blocks.WOOL, 1, 0)}));
		//Heavy Iron Plate
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemComponents, "heavyironplate", 1),
			new Object[] {"XX", "XX",
			'X', "plateIron"}));
		//Heavy Steel Plate
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemComponents, "heavysteelplate", 1),
			new Object[] {"XX", "XX",
			'X', "plateSteel"}));
		//Heavy Plasteel Plate
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemComponents, "heavyplasteelplate", 1),
			new Object[] {"XX", "XX",
			'X', "platePlasteel"}));
		//Heavy Boots
		addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemHeavyBoots),
			new Object[] {"X X", "X X", "Y Y",
			'X', Items.LEATHER,
			'Y', "plateIron"}));
		
		//Plate Crating
		if (ModConfig.allowPlateCrafting)
		{
			//Iron Hammer
			addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemHammerIron),
				new Object[] {" X ", " YX", "Y  ",
				'X', "ingotIron",
				'Y', "stickWood"}));
			//Steel Hammer
			addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemHammerSteel),
				new Object[] {" X ", " YX", "Y  ",
				'X', "ingotSteel",
				'Y', "stickWood"}));
			//Iron Plate
			addRecipe(event, new ShapelessOreRecipe(null, ModItems.createItemStack(ModItems.itemComponents, "ironplate", 1),
				"ingotIron",
				"plateHammer"));
			//Steel Plate
			addRecipe(event, new ShapelessOreRecipe(null, ModItems.createItemStack(ModItems.itemComponents, "steelplate", 1),
				"ingotSteel",
				"plateHammer"));
		}
		//Rubber Crafting
		if (ModConfig.allowRubberCrafting)
		{
			//Rubber Compound
			addRecipe(event, new ShapelessOreRecipe(null, ModItems.createItemStack(ModItems.itemComponents, "rubbercompound", 1),
				"slimeball",
				"slimeball",
				Items.COAL));
			//Rubber Compound to Rubber
			GameRegistry.addSmelting(ModItems.createItemStack(ModItems.itemComponents, "rubbercompound", 1), ModItems.createItemStack(ModItems.itemComponents, "rubber", 1), 0.35F);
		}
		if (ModConfig.allowSteelCrafting)
		{
			//Iron to Steel
			GameRegistry.addSmelting(Items.IRON_INGOT, ModItems.createItemStack(ModItems.itemComponents, "steelingot", 1), 0.7F);
		}
	}
	
	public static void addRecipe(Register<IRecipe> event, IRecipe rec)
	{
		event.getRegistry().register(rec.setRegistryName(new ResourceLocation(Reference.MODID, "recipe" + recipe)));
		++recipe;
	}
}