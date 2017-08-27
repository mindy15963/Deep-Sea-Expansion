package com.nhave.dse.registry;

import com.nhave.dse.recipe.ShapedNBTRecipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModCrafting
{
	public static String[] oreDict = new String[] {"dyeBlack", "dyeRed",  "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
		
	public static void init()
	{
		//Items
		//Small Air Tank
		GameRegistry.addRecipe(new ShapedNBTRecipe(new ItemStack(ModItems.itemAirTankSmall),
			new Object[] {"XYX", "YZY", "YAY",
			'X', "materialRubber",
			'Y', "plateIron",
			'Z', ModItems.itemComp.getItem("filter", 1),
			'A', "plateDenseIron"}));
		//Large Air Tank
		GameRegistry.addRecipe(new ShapedNBTRecipe(new ItemStack(ModItems.itemAirTankLarge),
			new Object[] {"XYX", "ZXZ", "A A",
			'X', "materialRubber",
			'Y', "plateSteel",
			'Z', ModItems.itemAirTankSmall,
			'A', "plateIron"}));
		//High Pressure Air Tank
		GameRegistry.addRecipe(new ShapedNBTRecipe(new ItemStack(ModItems.itemAirTankHighPressure),
			new Object[] {"XYX", "XZX", "XYX",
			'X', "platePlaststeel",
			'Y', "plateDensePlaststeel",
			'Z', ModItems.itemAirTankLarge}));
		//Iron Hammer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemHammerIron),
			new Object[] {" X ", " YX", "Y  ",
			'X', "ingotIron",
			'Y', "stickWood"}));
		//Steel Hammer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemHammerSteel),
			new Object[] {" X ", " YX", "Y  ",
			'X', "ingotSteel",
			'Y', "stickWood"}));
		//Heavy Iron Plate
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemComp.getItem("plateheavy_iron", 1),
			new Object[] {"XX", "XX",
			'X', "plateIron"}));
		//Heavy Copper Plate
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemComp.getItem("plateheavy_copper", 1),
			new Object[] {"XX", "XX",
			'X', "plateCopper"}));
		//Heavy Steel Plate
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemComp.getItem("plateheavy_steel", 1),
			new Object[] {"XX", "XX",
			'X', "plateSteel"}));
		//Heavy Plaststeel Plate
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemComp.getItem("plateheavy_plaststeel", 1),
			new Object[] {"XX", "XX",
			'X', "platePlaststeel"}));
		//Plaststeel Plate
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemComp.getItem("plate_plaststeel", 1),
			new Object[] {"XY ", "YZY", " YX",
			'X', Items.QUARTZ,
			'Y', ModItems.itemComp.getItem("rubbercompound", 1),
			'Z', "plateSteel"}));
		//Diving Goggles
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemDivingGoggles),
			new Object[] {"XXX", "YYY", "XXX",
			'X', "materialRubber",
			'Y', Blocks.GLASS_PANE}));
		
		for (int i = 0; i < oreDict.length; ++i)
		{
			//Flippers
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemFlippers, 1, i),
				new Object[] {"X X", "Y Y",
				'X', oreDict[i],
				'Y', "materialRubber"}));
		}
		
		//Air Filter
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemComp.getItem("filter", 1),
			new Object[] {"XYX",
			'X', "plateIron",
			'Y', new ItemStack(Blocks.WOOL, 1, 0)}));
		//Iron Armor Plating
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemArmorPlateIron),
			new Object[] {"XXX", "X X", "XXX",
			'X', "plateIron"}));
		//Steel Armor Plating
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemArmorPlateSteel),
			new Object[] {"XXX", "X X", "XXX",
			'X', "plateSteel"}));
		//Diamond Armor Plating
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemArmorPlateDiamond),
			new Object[] {"XXX", "XYX", "XXX",
			'X', Items.DIAMOND,
			'Y', ModItems.itemArmorPlateIron}));
		//Plaststeel Armor Plating
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemArmorPlatePlaststeel),
			new Object[] {"XXX", "X X", "XXX",
			'X', "platePlaststeel"}));
		//Iron Gear
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemComp.getItem("gear_iron", 1),
			new Object[] {" X ", "X X", " X ",
			'X', "plateIron"}));
		
		//Plate Crating
		if (ModConfig.allowPlateCrafting)
		{
			//Iron Plate
			GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.itemComp.getItem("plate_iron", 1),
				"ingotIron",
				"plateHammer"));
			//Copper Plate
			GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.itemComp.getItem("plate_copper", 1),
				"ingotCopper",
				"plateHammer"));
			//Steel Plate
			GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.itemComp.getItem("plate_steel", 1),
				"ingotSteel",
				"plateHammer"));
		}
		
		//Smelting
		//Iron to Steel
		if (ModConfig.ironToSteel)
		{
			GameRegistry.addSmelting(Items.IRON_INGOT, ModItems.itemComp.getItem("ingot_steel", 1), 0.7F);
		}
		//Slime to Rubber
		GameRegistry.addSmelting(ModItems.itemComp.getItem("rubbercompound", 1), ModItems.itemComp.getItem("rubber", 1), 0.35F);
		

		//Steel Plate
		GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.itemComp.getItem("rubbercompound", 1),
			"slimeball",
			"slimeball",
			Items.COAL));
		
		//Blocks
		//Frame
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockMachineFrame),
			new Object[] {"XYX", "Y Y", "XYX",
			'X', "plateIron",
			'Y', "gearIron"}));
		//Compressor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockAirCompressor),
			new Object[] {"XYX", "ZAZ", "XBX",
			'X', "plateIron",
			'Y', Blocks.IRON_BARS,
			'Z', "dustRedstone",
			'A', ModBlocks.blockMachineFrame,
			'B', Blocks.HOPPER}));
		//Heat Vent
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockHeatVent),
			new Object[] {"XYX", "Y Y", "XYX",
			'X', "plateDenseIron",
			'Y', Blocks.IRON_BARS}));
		//Heat Conductor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockHeatConductor),
			new Object[] {"XYX", "YYY", "XYX",
			'X', "plateDenseIron",
			'Y', "plateCopper"}));
		//Power Tap
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockPowerTap),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "plateDenseIron",
			'Y', "dustRedstone",
			'Z', ModBlocks.blockMachineFrame}));
		//Advanced Frame
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockMachineFrameAdvanced),
			new Object[] {"XYX", "ZAZ", "XYX",
			'X', "plateDenseSteel",
			'Y', "gearIron",
			'Z', "platePlaststeel",
			'A', ModBlocks.blockMachineFrame}));
		//Advanced Compressor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockAirCompressorAdvanced),
			new Object[] {"XYX", "ZAZ", "XBX",
			'X', "plateSteel",
			'Y', Blocks.IRON_BARS,
			'Z', "dustRedstone",
			'A', ModBlocks.blockMachineFrameAdvanced,
			'B', Blocks.HOPPER}));
	}
}