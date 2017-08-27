package com.nhave.dse.registry;

import com.nhave.dse.Reference;
import com.nhave.dse.client.mesh.CustomMeshDefinitionMetaItem;
import com.nhave.dse.client.mesh.CustomMeshDefinitionPearl;
import com.nhave.dse.client.widget.WidgetCharge;
import com.nhave.dse.items.ItemAirTank;
import com.nhave.dse.items.ItemArmorPlate;
import com.nhave.dse.items.ItemBase;
import com.nhave.dse.items.ItemDivingGoggles;
import com.nhave.dse.items.ItemFlippers;
import com.nhave.dse.items.ItemHammer;
import com.nhave.dse.items.ItemMeta;
import com.nhave.dse.items.ItemPearl;
import com.nhave.dse.items.ItemPowerCell;
import com.nhave.nhc.client.widget.TooltipWidget;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems
{
	public static Item itemAirTankSmall;
	public static Item itemAirTankLarge;
	public static Item itemAirTankHighPressure;
	public static Item itemAirTankCreative;
	public static Item itemDivingGoggles;
	public static Item itemFlippers;
	public static Item itemHammerIron;
	public static Item itemHammerSteel;
	public static Item itemArmorPlateIron;
	public static Item itemArmorPlateDiamond;
	public static Item itemArmorPlateSteel;
	public static Item itemArmorPlatePlaststeel;
	public static ItemMeta itemComp;
	public static Item itemSupportToken;
	public static Item itemSupplyCrate;
	public static Item itemPearl;
	public static Item itemPowercellSmall;
	public static Item itemPowercellMedium;
	public static Item itemPowercellLarge;
	public static Item itemPowercellCreative;
	
	public static String[][] craftingComponents = new String[][]
	{
		{"ingot_steel", "0"}, {"plate_iron", "0"}, {"plate_steel", "0"}, {"plate_plaststeel", "1"}, {"plateheavy_iron", "0"},
		{"plateheavy_steel", "0"}, {"plateheavy_plaststeel", "1"}, {"rubbercompound", "0"}, {"rubber", "0"}, {"filter", "0"},
		{"pearl", "1"}, {"blackpearl", "2"}, {"gear_iron", "0"}, {"plate_copper", "0"}, {"plateheavy_copper", "0"},
		{"ingot_copper", "0"}
	};
	
	public static ArmorMaterial materialScuba = EnumHelper.addArmorMaterial("SCUBA", "SCUBA", 0, new int[] {1, 3, 2, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static ArmorMaterial materialNoArmor = EnumHelper.addArmorMaterial("NOARMOR", "NOARMOR", 0, new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	
	public static void init()
	{
		itemAirTankSmall = new ItemAirTank("airtank_small", materialNoArmor, 12000, 2640, false);
		itemAirTankLarge = new ItemAirTank("airtank_large", materialNoArmor, 24000, 3000, true, StringUtils.LIGHT_BLUE);
		itemAirTankHighPressure = new ItemAirTank("airtank_highpressure", materialNoArmor, 48000, 3500, true, StringUtils.PURPLE);
		itemAirTankCreative = new ItemAirTank("airtank_creative", materialNoArmor, -1, true, StringUtils.PINK);
		itemDivingGoggles = new ItemDivingGoggles("divinggoggles", materialNoArmor);
		itemFlippers = new ItemFlippers("flippers", materialNoArmor);
		itemHammerIron = new ItemHammer("hammer_iron", ModConfig.hammerDurabilityIron);
		itemHammerSteel = new ItemHammer("hammer_steel", ModConfig.hammerDurabilitySteel);
		itemArmorPlateIron = new ItemArmorPlate("armorplate_iron", new int[] {2, 6, 5, 2});
		itemArmorPlateDiamond = new ItemArmorPlate("armorplate_diamond", new int[] {3, 8, 6, 3}, StringUtils.LIGHT_BLUE);
		itemArmorPlateSteel = new ItemArmorPlate("armorplate_steel", new int[] {3, 8, 6, 3}, StringUtils.LIGHT_BLUE);
		itemArmorPlatePlaststeel = new ItemArmorPlate("armorplate_plaststeel", new int[] {3, 8, 6, 3}, StringUtils.PURPLE);
		itemComp = new ItemMeta("comp", craftingComponents);
		itemSupportToken = new ItemBase("token_support", StringUtils.ORANGE).setMaxStackSize(1);
		itemSupplyCrate = new ItemBase("supply_crate", StringUtils.PURPLE).setMaxStackSize(1);
		itemPearl = new ItemPearl("small_pearl");
		itemPowercellSmall = new ItemPowerCell("powercell_small", 400000);
		itemPowercellMedium = new ItemPowerCell("powercell_medium", 4000000, StringUtils.LIGHT_BLUE);
		itemPowercellLarge = new ItemPowerCell("powercell_large", 20000000, StringUtils.PURPLE);
		itemPowercellCreative = new ItemPowerCell("powercell_creative", -1, StringUtils.PINK);
	}
	
	public static void register()
	{
		GameRegistry.register(itemAirTankSmall);
		GameRegistry.register(itemAirTankLarge);
		GameRegistry.register(itemAirTankHighPressure);
		GameRegistry.register(itemAirTankCreative);
		GameRegistry.register(itemDivingGoggles);
		GameRegistry.register(itemFlippers);
		GameRegistry.register(itemHammerIron);
		GameRegistry.register(itemHammerSteel);
		GameRegistry.register(itemArmorPlateIron);
		GameRegistry.register(itemArmorPlateDiamond);
		GameRegistry.register(itemArmorPlateSteel);
		GameRegistry.register(itemArmorPlatePlaststeel);
		GameRegistry.register(itemComp);
		GameRegistry.register(itemSupportToken);
		GameRegistry.register(itemSupplyCrate);
		GameRegistry.register(itemPearl);
		GameRegistry.register(itemPowercellSmall);
		GameRegistry.register(itemPowercellMedium);
		GameRegistry.register(itemPowercellLarge);
		GameRegistry.register(itemPowercellCreative);

		OreDictionary.registerOre("plateHammer", new ItemStack(itemHammerIron, 1, 0));
		OreDictionary.registerOre("plateHammer", new ItemStack(itemHammerSteel, 1, 0));
		OreDictionary.registerOre("ingotSteel", ModItems.itemComp.getItem("ingot_steel", 1));
		OreDictionary.registerOre("ingotCopper", ModItems.itemComp.getItem("ingot_copper", 1));
		OreDictionary.registerOre("plateIron", ModItems.itemComp.getItem("plate_iron", 1));
		OreDictionary.registerOre("plateCopper", ModItems.itemComp.getItem("plate_copper", 1));
		OreDictionary.registerOre("plateSteel", ModItems.itemComp.getItem("plate_steel", 1));
		OreDictionary.registerOre("platePlaststeel", ModItems.itemComp.getItem("plate_plaststeel", 1));
		OreDictionary.registerOre("plateDenseIron", ModItems.itemComp.getItem("plateheavy_iron", 1));
		OreDictionary.registerOre("plateDenseCopper", ModItems.itemComp.getItem("plateheavy_copper", 1));
		OreDictionary.registerOre("plateDenseSteel", ModItems.itemComp.getItem("plateheavy_steel", 1));
		OreDictionary.registerOre("plateDensePlaststeel", ModItems.itemComp.getItem("plateheavy_plaststeel", 1));
		OreDictionary.registerOre("gearIron", ModItems.itemComp.getItem("gear_iron", 1));
		OreDictionary.registerOre("materialRubber", ModItems.itemComp.getItem("rubber", 1));
		OreDictionary.registerOre("itemRubber", ModItems.itemComp.getItem("rubber", 1));
	}
	
	public static void registerWidgets()
	{
		TooltipWidget.register(new WidgetCharge());
	}
	
	public static void registerRenders()
	{
		registerRender(itemAirTankSmall);
		registerRender(itemAirTankLarge);
		registerRender(itemAirTankHighPressure);
		registerRender(itemAirTankCreative);
		registerRender(itemDivingGoggles);
		registerMetaRender(itemFlippers, 16, true);
		registerRender(itemHammerIron);
		registerRender(itemHammerSteel);
		registerRender(itemArmorPlateIron);
		registerRender(itemArmorPlateDiamond);
		registerRender(itemArmorPlateSteel);
		registerRender(itemArmorPlatePlaststeel);
		registerMetaRender(itemComp, craftingComponents.length, false);
		registerRender(itemSupportToken);
		registerRender(itemSupplyCrate);
		registerRender(itemPowercellSmall);
		registerRender(itemPowercellMedium);
		registerRender(itemPowercellLarge);
		registerRender(itemPowercellCreative);
		
		registerRenderMesh(itemPearl, new CustomMeshDefinitionPearl());
		ModelBakery.registerItemVariants(itemPearl, new ResourceLocation(Reference.MODID + ":" + "micro_pearl"));
		ModelBakery.registerItemVariants(itemPearl, new ResourceLocation(Reference.MODID + ":" + "tiny_pearl"));
		ModelBakery.registerItemVariants(itemPearl, new ResourceLocation(Reference.MODID + ":" + "small_pearl"));
		ModelBakery.registerItemVariants(itemPearl, new ResourceLocation(Reference.MODID + ":" + "small_blackpearl"));
	}
	
	public static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
		
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((IItemColor)itemFlippers, itemFlippers);	
	}
	
	public static void registerMetaRender(Item item, int loop, boolean single)
	{
		for (int i = 0; i < loop; ++i)
		{
			String meta = "";
			if (i > 0 && !single) meta = "_" + i;
			ModelBakery.registerItemVariants(item, new ResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath() + meta));
		}
		registerRenderMesh(item, new CustomMeshDefinitionMetaItem(single));
	}
	
	public static void registerRenderMesh(Item item, ItemMeshDefinition mesh)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(item, mesh);
	}
}