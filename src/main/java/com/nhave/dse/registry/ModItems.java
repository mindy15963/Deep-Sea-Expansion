package com.nhave.dse.registry;

import com.nhave.dse.Reference;
import com.nhave.dse.client.mesh.CustomMeshDefinitionMetaItem;
import com.nhave.dse.item.ItemArmorPlate;
import com.nhave.dse.item.ItemFlippers;
import com.nhave.dse.items.ItemAirTank;
import com.nhave.dse.items.ItemDivingGoggles;
import com.nhave.dse.items.ItemHammer;
import com.nhave.dse.items.ItemMeta;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	public static Item itemComp;
	
	public static String[][] craftingComponents = new String[][]
	{
		{"ingot_steel", "0"}, {"plate_iron", "0"}, {"plate_steel", "0"},
		{"plate_plaststeel", "1"}, {"plateheavy_iron", "0"}, {"plateheavy_steel", "0"},
		{"plateheavy_plaststeel", "1"}, {"rubbercompound", "0"}, {"rubber", "0"}
	};
	
	public static ArmorMaterial materialScuba = EnumHelper.addArmorMaterial("SCUBA", "SCUBA", 0, new int[] {1, 3, 2, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static ArmorMaterial materialNoArmor = EnumHelper.addArmorMaterial("NOARMOR", "NOARMOR", 0, new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	
	public static void init()
	{
		itemAirTankSmall = new ItemAirTank("airtank_small", materialNoArmor, 12000, false);
		itemAirTankLarge = new ItemAirTank("airtank_large", materialNoArmor, 24000, true);
		itemAirTankHighPressure = new ItemAirTank("airtank_highpressure", materialNoArmor, 48000, true, EnumRarity.UNCOMMON);
		itemAirTankCreative = new ItemAirTank("airtank_creative", materialNoArmor, -1, true, EnumRarity.EPIC);
		itemDivingGoggles = new ItemDivingGoggles("divinggoggles", materialNoArmor);
		itemFlippers = new ItemFlippers("flippers", materialNoArmor);
		itemHammerIron = new ItemHammer("hammer_iron");
		itemHammerSteel = new ItemHammer("hammer_steel");
		itemArmorPlateIron = new ItemArmorPlate("armorplate_iron", new int[] {2, 6, 5, 2});
		itemArmorPlateDiamond = new ItemArmorPlate("armorplate_diamond", new int[] {3, 8, 6, 3});
		itemArmorPlateSteel = new ItemArmorPlate("armorplate_steel", new int[] {3, 8, 6, 3});
		itemArmorPlatePlaststeel = new ItemArmorPlate("armorplate_plaststeel", new int[] {4, 9, 7, 4}, EnumRarity.UNCOMMON);
		itemComp = new ItemMeta("comp", craftingComponents);
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
	}
	
	public static void registerRenders()
	{
		registerRender(itemAirTankSmall);
		registerRender(itemAirTankLarge);
		registerRender(itemAirTankHighPressure);
		registerRender(itemAirTankCreative);
		registerRender(itemDivingGoggles);
		registerRender(itemFlippers);
		registerRender(itemHammerIron);
		registerRender(itemHammerSteel);
		registerRender(itemArmorPlateIron);
		registerRender(itemArmorPlateDiamond);
		registerRender(itemArmorPlateSteel);
		registerRender(itemArmorPlatePlaststeel);
		registerMetaRender(itemComp, craftingComponents.length, false);
	}
	
	public static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
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