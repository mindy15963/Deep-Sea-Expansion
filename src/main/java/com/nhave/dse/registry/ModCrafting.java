package com.nhave.dse.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModCrafting
{
	public static void init()
	{
		GameRegistry.addRecipe(new ItemStack(ModItems.itemAirTankSmall),
			new Object[] {"XYX", "YZY", "YAY",
			'X', ModItems.itemComp.getItem("rubber", 1),
			'Y', ModItems.itemComp.getItem("plate_iron", 1),
			'Z', ModItems.itemComp.getItem("filter", 1),
			'A', ModItems.itemComp.getItem("plateheavy_iron", 1)});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemAirTankLarge),
			new Object[] {"XYX", "ZXZ", "A A",
			'X', ModItems.itemComp.getItem("rubber", 1),
			'Y', ModItems.itemComp.getItem("plate_steel", 1),
			'Z', ModItems.itemAirTankSmall,
			'A', ModItems.itemComp.getItem("plate_iron", 1)});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemAirTankHighPressure),
			new Object[] {"XYX", "XZX", "XYX",
			'X', ModItems.itemComp.getItem("plate_plaststeel", 1),
			'Y', ModItems.itemComp.getItem("plateheavy_plaststeel", 1),
			'Z', ModItems.itemAirTankLarge});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemHammerIron),
			new Object[] {" X ", " YX", "Y  ",
			'X', Items.IRON_INGOT,
			'Y', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemHammerSteel),
			new Object[] {" X ", " YX", "Y  ",
			'X', ModItems.itemComp.getItem("ingot_steel", 1),
			'Y', Items.STICK});
		GameRegistry.addRecipe(ModItems.itemComp.getItem("plateheavy_iron", 1),
			new Object[] {"XX", "XX",
			'X', ModItems.itemComp.getItem("plate_iron", 1)});
		GameRegistry.addRecipe(ModItems.itemComp.getItem("plateheavy_steel", 1),
			new Object[] {"XX", "XX",
			'X', ModItems.itemComp.getItem("plate_steel", 1)});
		GameRegistry.addRecipe(ModItems.itemComp.getItem("plateheavy_plaststeel", 1),
			new Object[] {"XX", "XX",
			'X', ModItems.itemComp.getItem("plate_plaststeel", 1)});
		GameRegistry.addRecipe(ModItems.itemComp.getItem("plate_plaststeel", 1),
			new Object[] {"XY ", "YZY", " YX",
			'X', Items.QUARTZ,
			'Y', ModItems.itemComp.getItem("rubbercompound", 1),
			'Z', ModItems.itemComp.getItem("plate_steel", 1)});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemDivingGoggles),
			new Object[] {"XXX", "YYY", "XXX",
			'X', ModItems.itemComp.getItem("rubber", 1),
			'Y', Blocks.GLASS_PANE});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemFlippers),
			new Object[] {"X X", "Y Y",
			'X', "dyeBlue",
			'Y', ModItems.itemComp.getItem("rubber", 1)}));
		GameRegistry.addRecipe(ModItems.itemComp.getItem("filter", 1),
			new Object[] {"XYX",
			'X', ModItems.itemComp.getItem("plate_iron", 1),
			'Y', new ItemStack(Blocks.WOOL, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemArmorPlateIron),
			new Object[] {"XXX", "X X", "XXX",
			'X', ModItems.itemComp.getItem("plate_iron", 1)});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemArmorPlateSteel),
			new Object[] {"XXX", "X X", "XXX",
			'X', ModItems.itemComp.getItem("plate_steel", 1)});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemArmorPlateDiamond),
			new Object[] {"XXX", "XYX", "XXX",
			'X', Items.DIAMOND,
			'Y', ModItems.itemArmorPlateIron});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemArmorPlatePlaststeel),
			new Object[] {"XXX", "X X", "XXX",
			'X', ModItems.itemComp.getItem("plate_plaststeel", 1)});
	}
}