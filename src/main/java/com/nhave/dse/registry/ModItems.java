package com.nhave.dse.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.nhave.dse.Reference;
import com.nhave.dse.client.mesh.CustomMeshDefinitionMetaItem;
import com.nhave.dse.client.mesh.CustomMeshDefinitionShadeable;
import com.nhave.dse.client.render.ItemColorHandler;
import com.nhave.dse.items.ItemAirtank;
import com.nhave.dse.items.ItemArmorInsulation;
import com.nhave.dse.items.ItemArmorScuba;
import com.nhave.dse.items.ItemArmorplate;
import com.nhave.dse.items.ItemBase;
import com.nhave.dse.items.ItemDinghy;
import com.nhave.dse.items.ItemDivingGoggles;
import com.nhave.dse.items.ItemFlippers;
import com.nhave.dse.items.ItemHammer;
import com.nhave.dse.items.ItemHeavyBoots;
import com.nhave.dse.items.ItemMeta;
import com.nhave.dse.items.ItemMotorboat;
import com.nhave.dse.items.ItemPowerunit;
import com.nhave.dse.items.ItemShader;
import com.nhave.dse.items.ItemSimpleUpgrades;
import com.nhave.dse.shaders.Shader;
import com.nhave.dse.shaders.ShaderRegistry;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems
{
	public static final List<ItemStack> MOTORBOAT_UPGRADES = new ArrayList<ItemStack>();
	public static final List<ItemStack> SCUBAMASK_UPGRADES = new ArrayList<ItemStack>();
	public static final List<ItemStack> SCUBACHEST_UPGRADES = new ArrayList<ItemStack>();
	public static final List<ItemStack> SCUBALEGS_UPGRADES = new ArrayList<ItemStack>();
	public static final List<ItemStack> SCUBABOOTS_UPGRADES = new ArrayList<ItemStack>();
	public static final List<String> MOTORBOAT_UPGRADES_NBT = new ArrayList<String>();
	public static final List<String> SCUBAMASK_UPGRADES_NBT = new ArrayList<String>();
	public static final List<String> SCUBACHEST_UPGRADES_NBT = new ArrayList<String>();
	public static final List<String> SCUBALEGS_UPGRADES_NBT = new ArrayList<String>();
	public static final List<String> SCUBABOOTS_UPGRADES_NBT = new ArrayList<String>();
	
	public static Item itemIcon;
	public static Item itemDivingGoggles;
	public static Item itemFlippers;
	public static Item itemDinghy;
	public static Item itemShaderCore;
	public static Item itemShader;
	public static Item itemAirTankSmall;
	public static Item itemAirTankLarge;
	public static Item itemAirTankHighPressure;
	public static Item itemAirTankCreative;
	public static Item itemComponents;
	public static Item itemHammerIron;
	public static Item itemHammerSteel;
	public static Item itemHeavyBoots;
	public static Item itemMotorboat;
	public static Item itemSimpleUpgrades;
	public static Item itemScubaMask;
	public static Item itemScubaChest;
	public static Item itemScubaLegs;
	public static Item itemScubaBoots;
	
	public static Item itemPowerunitSmall;
	public static Item itemPowerunitMedium;
	public static Item itemPowerunitLarge;
	public static Item itemPowerunitCreative;

	public static Item itemArmorplate;
	public static Item itemInsulationHyper;
	public static Item itemInsulationHypo;
	
	public static final String[][] COMPONETNS = new String[][]
	{
		{"steelingot", "1"}, {"rubbercompound", "0"}, {"rubber", "0"}, {"ironplate", "0"}, {"steelplate", "1"}, {"plasteelplate", "2"},
		{"heavyironplate", "0"}, {"heavysteelplate", "1"}, {"heavyplasteelplate", "2"}, {"oxygenfilter", "1"}
	};
	public static final String[][] UPGRADES = new String[][]
	{
		{"paddles", "0", "PADDLE"}, {"storagebox", "0", "STORAGE", "NYI"}, {"boatbooster", "2", "BOOSTER"},
		{"lightfilter", "3", "LIGHTFILTER", "WIP"}, {"amplifier", "2", "AMPLIFIER", "WIP"}
	};
	public static final String[][] ARMORPLATES = new String[][]
	{
		{"iron", "0", "2", "5", "6", "2"}, {"diamond", "1", "3", "6", "8", "3"}, {"steel", "1", "2", "5", "7", "2"}, {"plasteel", "2", "4", "7", "9", "4"},
		{"copper", "0", "1", "3", "3", "1"}, {"bronze", "1", "2", "6", "6", "2"}, {"platinum", "2", "3", "6", "8", "3"}, {"invar", "1", "2", "5", "7", "2"},
		{"silver", "0", "2", "4", "4", "1"}
	};
	
	public static ArmorMaterial materialScuba = EnumHelper.addArmorMaterial("SCUBA", "SCUBA", 0, new int[] {1, 3, 2, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static ArmorMaterial materialNoArmor = EnumHelper.addArmorMaterial("NOARMOR", "NOARMOR", 0, new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	
	public static void init()
	{
		//Initialize Items
		itemIcon = new ItemBase("icon").setCreativeTab(null);
		itemDivingGoggles = new ItemDivingGoggles("divinggoggles", materialNoArmor);
		itemFlippers = new ItemFlippers("flippers", materialNoArmor);
		itemDinghy = new ItemDinghy("dinghy");
		itemShaderCore = new ItemBase("shadercore").setQualityColor(StringUtils.LIGHT_BLUE);
		itemShader = new ItemShader("shader");
		itemAirTankSmall = new ItemAirtank("airtank_small", materialNoArmor, ModConfig.airtankCapacitySmall, false).setQualityColor(StringUtils.LIGHT_BLUE);
		itemAirTankLarge = new ItemAirtank("airtank_large", materialNoArmor, ModConfig.airtankCapacityMedium, true).setQualityColor(StringUtils.PURPLE);
		itemAirTankHighPressure = new ItemAirtank("airtank_highpressure", materialNoArmor, ModConfig.airtankCapacityLarge, true).setQualityColor(StringUtils.ORANGE);
		itemAirTankCreative = new ItemAirtank("airtank_creative", materialNoArmor, -1, true).setQualityColor(StringUtils.PINK);
		itemComponents = new ItemMeta("components", COMPONETNS);
		itemHammerIron = new ItemHammer("hammeriron", ModConfig.hammerUsesIron).setQualityColor(StringUtils.LIGHT_BLUE);
		itemHammerSteel = new ItemHammer("hammersteel", ModConfig.hammerUsesSteel).setQualityColor(StringUtils.LIGHT_BLUE);
		itemHeavyBoots = new ItemHeavyBoots("heavyboots", ArmorMaterial.IRON);
		itemMotorboat = new ItemMotorboat("motorboat");
		itemSimpleUpgrades = new ItemSimpleUpgrades("upgrades", UPGRADES);
		itemScubaMask = new ItemArmorScuba("scubamask", materialScuba, EntityEquipmentSlot.HEAD);
		itemScubaChest = new ItemArmorScuba("scubachest", materialScuba, EntityEquipmentSlot.CHEST);
		itemScubaLegs = new ItemArmorScuba("scubalegs", materialScuba, EntityEquipmentSlot.LEGS);
		itemScubaBoots = new ItemArmorScuba("scubaboots", materialScuba, EntityEquipmentSlot.FEET);

		itemArmorplate = new ItemArmorplate("armorplate", ARMORPLATES);
		
		itemPowerunitSmall = new ItemPowerunit("powerunit_small", 5000000, 5000).setQualityColor(StringUtils.LIGHT_BLUE);
		itemPowerunitMedium = new ItemPowerunit("powerunit_medium", 10000000, 10000).setQualityColor(StringUtils.PURPLE);
		itemPowerunitLarge = new ItemPowerunit("powerunit_large", 20000000, 20000).setQualityColor(StringUtils.ORANGE);
		itemPowerunitCreative = new ItemPowerunit("powerunit_creative", Integer.MAX_VALUE, 250000).setIsCreative(true).setQualityColor(StringUtils.PINK);

		itemInsulationHyper = new ItemArmorInsulation("hyperthermic");
		itemInsulationHypo = new ItemArmorInsulation("hypothermic");
		
		//Initialize Item Shaders
		Item[] allShadeable = new Item[] {itemMotorboat, itemAirTankSmall, itemAirTankLarge, itemAirTankHighPressure, itemAirTankCreative, itemScubaMask, itemScubaChest, itemScubaLegs, itemScubaBoots};
		ShaderRegistry.registerShader(new Shader("crimson", 3158064, 16711680).setNamePrefix(StringUtils.ORANGE).addCompatibleItems(allShadeable));
		ShaderRegistry.registerShader(new Shader("race", 16639, 16777215).setNamePrefix(StringUtils.LIGHT_BLUE).addCompatibleItems(itemMotorboat, itemAirTankSmall, itemAirTankLarge, itemAirTankHighPressure, itemAirTankCreative));
		ShaderRegistry.registerShader(new Shader("muscle", 7602176, 16777215).setNamePrefix(StringUtils.LIGHT_BLUE).addCompatibleItems(itemMotorboat, itemAirTankSmall, itemAirTankLarge, itemAirTankHighPressure, itemAirTankCreative));
		ShaderRegistry.registerShader(new Shader("dark", 3158064, 49809).setNamePrefix(StringUtils.PURPLE).addCompatibleItems(itemScubaMask, itemScubaChest, itemScubaLegs, itemScubaBoots));
		ShaderRegistry.registerShader(new Shader("pink", 16760575, 16777215).setNamePrefix(StringUtils.PURPLE).addCompatibleItems(itemScubaMask, itemScubaChest, itemScubaLegs, itemScubaBoots));
		ShaderRegistry.registerShader(new Shader("space", 16777215, 16766745).setNamePrefix(StringUtils.PURPLE).addCompatibleItems(itemScubaMask, itemScubaChest, itemScubaLegs, itemScubaBoots));
		ShaderRegistry.registerShader(new Shader("tech", 16777215, 65535).setNamePrefix(StringUtils.ORANGE).addCompatibleItems(itemScubaMask, itemScubaChest, itemScubaLegs, itemScubaBoots));
	}
	
	public static void register(Register<Item> event)
	{
		event.getRegistry().register(itemIcon);
		event.getRegistry().register(itemDivingGoggles);
		event.getRegistry().register(itemFlippers);
		event.getRegistry().register(itemDinghy);
		event.getRegistry().register(itemShaderCore);
		event.getRegistry().register(itemShader);
		event.getRegistry().register(itemAirTankSmall);
		event.getRegistry().register(itemAirTankLarge);
		event.getRegistry().register(itemAirTankHighPressure);
		event.getRegistry().register(itemAirTankCreative);
		event.getRegistry().register(itemComponents);
		event.getRegistry().register(itemHammerIron);
		event.getRegistry().register(itemHammerSteel);
		event.getRegistry().register(itemHeavyBoots);
		event.getRegistry().register(itemMotorboat);
		event.getRegistry().register(itemSimpleUpgrades);
		event.getRegistry().register(itemScubaMask);
		event.getRegistry().register(itemScubaChest);
		event.getRegistry().register(itemScubaLegs);
		event.getRegistry().register(itemScubaBoots);
		
		event.getRegistry().register(itemArmorplate);
		
		event.getRegistry().register(itemPowerunitSmall);
		event.getRegistry().register(itemPowerunitMedium);
		event.getRegistry().register(itemPowerunitLarge);
		event.getRegistry().register(itemPowerunitCreative);
		
		event.getRegistry().register(itemInsulationHyper);
		event.getRegistry().register(itemInsulationHypo);
		
		OreDictionary.registerOre("plateHammer", new ItemStack(itemHammerIron, 1, 0));
		OreDictionary.registerOre("plateHammer", new ItemStack(itemHammerSteel, 1, 0));
		OreDictionary.registerOre("ingotSteel", createItemStack(ModItems.itemComponents, "steelingot", 1));
		OreDictionary.registerOre("materialRubber", createItemStack(ModItems.itemComponents, "rubber", 1));
		OreDictionary.registerOre("itemRubber", createItemStack(ModItems.itemComponents, "rubber", 1));
		OreDictionary.registerOre("plateIron", createItemStack(ModItems.itemComponents, "ironplate", 1));
		OreDictionary.registerOre("plateSteel", createItemStack(ModItems.itemComponents, "steelplate", 1));
		OreDictionary.registerOre("platePlasteel", createItemStack(ModItems.itemComponents, "plasteelplate", 1));
		OreDictionary.registerOre("plateDenseIron", createItemStack(ModItems.itemComponents, "heavyironplate", 1));
		OreDictionary.registerOre("plateDenseSteel", createItemStack(ModItems.itemComponents, "heavysteelplate", 1));
		OreDictionary.registerOre("plateDensePlasteel", createItemStack(ModItems.itemComponents, "heavyplasteelplate", 1));
		
		MOTORBOAT_UPGRADES.add(createItemStack(ModItems.itemSimpleUpgrades, "paddles", 1));
		MOTORBOAT_UPGRADES.add(createItemStack(ModItems.itemSimpleUpgrades, "storagebox", 1));
		MOTORBOAT_UPGRADES.add(createItemStack(ModItems.itemSimpleUpgrades, "boatbooster", 1));
		
		SCUBAMASK_UPGRADES.add(createItemStack(ModItems.itemSimpleUpgrades, "lightfilter", 1));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemAirTankSmall));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemAirTankLarge));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemAirTankHighPressure));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemAirTankCreative));
		SCUBALEGS_UPGRADES.add(createItemStack(ModItems.itemSimpleUpgrades, "amplifier", 1));
		SCUBABOOTS_UPGRADES.add(new ItemStack(itemFlippers));
		
		SCUBAMASK_UPGRADES.add(new ItemStack(itemArmorplate));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemArmorplate));
		SCUBALEGS_UPGRADES.add(new ItemStack(itemArmorplate));
		SCUBABOOTS_UPGRADES.add(new ItemStack(itemArmorplate));
		
		SCUBACHEST_UPGRADES.add(new ItemStack(itemPowerunitSmall));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemPowerunitMedium));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemPowerunitLarge));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemPowerunitCreative));

		SCUBAMASK_UPGRADES.add(new ItemStack(itemInsulationHyper));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemInsulationHyper));
		SCUBALEGS_UPGRADES.add(new ItemStack(itemInsulationHyper));
		SCUBABOOTS_UPGRADES.add(new ItemStack(itemInsulationHyper));
		SCUBAMASK_UPGRADES.add(new ItemStack(itemInsulationHypo));
		SCUBACHEST_UPGRADES.add(new ItemStack(itemInsulationHypo));
		SCUBALEGS_UPGRADES.add(new ItemStack(itemInsulationHypo));
		SCUBABOOTS_UPGRADES.add(new ItemStack(itemInsulationHypo));
		
		MOTORBOAT_UPGRADES_NBT.add("PADDLE");
		MOTORBOAT_UPGRADES_NBT.add("STORAGE");
		MOTORBOAT_UPGRADES_NBT.add("BOOSTER");
		SCUBAMASK_UPGRADES_NBT.add("LIGHTFILTER");
		SCUBACHEST_UPGRADES_NBT.add("AIRTANK");
		SCUBALEGS_UPGRADES_NBT.add("AMPLIFIER");
		SCUBABOOTS_UPGRADES_NBT.add("FLIPPERS");
		SCUBACHEST_UPGRADES_NBT.add("POWERUNIT");
		
		SCUBAMASK_UPGRADES_NBT.add("ARMORPLATE");
		SCUBACHEST_UPGRADES_NBT.add("ARMORPLATE");
		SCUBALEGS_UPGRADES_NBT.add("ARMORPLATE");
		SCUBABOOTS_UPGRADES_NBT.add("ARMORPLATE");
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		registerRender(itemIcon);
		registerRender(itemDivingGoggles);
		registerRender(itemDinghy);
		registerRender(itemShaderCore);
		registerRender(itemShader);
		registerRender(itemHammerIron);
		registerRender(itemHammerSteel);
		registerRender(itemHeavyBoots);

		registerRender(itemPowerunitSmall);
		registerRender(itemPowerunitMedium);
		registerRender(itemPowerunitLarge);
		registerRender(itemPowerunitCreative);
		
		registerRender(itemInsulationHyper);
		registerRender(itemInsulationHypo);
		
		registerMetaRender(itemFlippers, 16, false);
		registerMetaRender(itemComponents, COMPONETNS.length, false);
		registerMetaRender(itemSimpleUpgrades, UPGRADES.length, false);
		registerMetaRender(itemArmorplate, ARMORPLATES.length, false);
		
		registerRenderMesh(itemAirTankSmall, new CustomMeshDefinitionShadeable("airtanksmall"));
		registerRenderMesh(itemAirTankLarge, new CustomMeshDefinitionShadeable("airtanklarge"));
		registerRenderMesh(itemAirTankHighPressure, new CustomMeshDefinitionShadeable("airtanklarge"));
		registerRenderMesh(itemAirTankCreative, new CustomMeshDefinitionShadeable("airtanklarge"));
		registerRenderMesh(itemMotorboat, new CustomMeshDefinitionShadeable("boat"));
		registerRenderMesh(itemScubaMask, new CustomMeshDefinitionShadeable("scubamask"));
		registerRenderMesh(itemScubaChest, new CustomMeshDefinitionShadeable("scubachest"));
		registerRenderMesh(itemScubaLegs, new CustomMeshDefinitionShadeable("scubalegs"));
		registerRenderMesh(itemScubaBoots, new CustomMeshDefinitionShadeable("scubaboots"));
		
		//Registering the models used by the shaders.
		if (!ShaderRegistry.isEmpty())
		{
			for(Entry<String, Shader> entry : ShaderRegistry.SHADERS.entrySet())
			{
				Shader shader = entry.getValue();
				if (shader.isItemCompatible(itemMotorboat))
				{
					ModelLoader.registerItemVariants(itemMotorboat, shader.getResourceLocation("boat"));
				}
				if (shader.isItemCompatible(itemAirTankSmall))
				{
					ModelLoader.registerItemVariants(itemAirTankSmall, shader.getResourceLocation("airtanksmall"));
				}
				if (shader.isItemCompatible(itemAirTankLarge) || shader.isItemCompatible(itemAirTankHighPressure) || shader.isItemCompatible(itemAirTankCreative))
				{
					ModelLoader.registerItemVariants(itemAirTankLarge, shader.getResourceLocation("airtanklarge"));
					ModelLoader.registerItemVariants(itemAirTankHighPressure, shader.getResourceLocation("airtanklarge"));
					ModelLoader.registerItemVariants(itemAirTankCreative, shader.getResourceLocation("airtanklarge"));
				}
				if (shader.isItemCompatible(itemScubaMask))
				{
					ModelLoader.registerItemVariants(itemScubaMask, shader.getResourceLocation("scubamask"));
				}
				if (shader.isItemCompatible(itemScubaChest))
				{
					ModelLoader.registerItemVariants(itemScubaChest, shader.getResourceLocation("scubachest"));
				}
				if (shader.isItemCompatible(itemScubaLegs))
				{
					ModelLoader.registerItemVariants(itemScubaLegs, shader.getResourceLocation("scubalegs"));
				}
				if (shader.isItemCompatible(itemScubaBoots))
				{
					ModelLoader.registerItemVariants(itemScubaBoots, shader.getResourceLocation("scubaboots"));
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderData()
	{
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ItemColorHandler.INSTANCE, itemShader);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderMesh(Item item, ItemMeshDefinition mesh)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		ModelLoader.setCustomMeshDefinition(item, mesh);
		ModelLoader.registerItemVariants(item, item.getRegistryName());
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerMetaRender(Item item, int loop, boolean single)
	{
		for (int i = 0; i < loop; ++i)
		{
			String meta = "";
			if (i > 0 && !single) meta = "_" + i;
			ModelLoader.registerItemVariants(item, new ResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath() + meta));
		}
		registerRenderMesh(item, new CustomMeshDefinitionMetaItem(single));
	}
	
	public static ItemStack createItemStack(Item item, String name, int amount)
	{
		if (item instanceof ItemMeta)
		{
			ItemMeta metaItem = (ItemMeta) item;
			for (int meta = 0; meta < metaItem.names.length; ++meta)
			{
				if (metaItem.names[meta].equals(name)) return new ItemStack(item, amount, meta);
			}
		}
		else if (item instanceof ItemShader)
		{
			ItemStack shader = new ItemStack(item, amount);
			ItemNBTHelper.setString(shader, "SHADERS", "SHADER", name);
			return shader;
		}
		return new ItemStack(item, amount);
	}
}