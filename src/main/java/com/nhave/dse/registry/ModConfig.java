package com.nhave.dse.registry;

import java.io.File;

import com.nhave.dse.Reference;
import com.nhave.nhc.client.util.RenderUtils.HUDPositions;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModConfig
{
	public static boolean isClientConfig;
	public static Configuration config;
	
	//Energy Config
	public static String energyUnit = Defaults.energyUnit;
	public static int boatPowerCapacity = Defaults.boatPowerCapacity;
    public static int boatPowerUsage = Defaults.boatPowerUsage;
    public static int boatPowerTransfer = Defaults.boatPowerTransfer;
    public static int boatBoostModifier = Defaults.boatBoostModifier;
	public static int powercellCapacitySmall = Defaults.powercellCapacitySmall;
	public static int powercellCapacityMedium = Defaults.powercellCapacityMedium;
	public static int powercellCapacityLarge = Defaults.powercellCapacityLarge;
	
	//Machine Config
	public static int machinePowerCapacity = Defaults.machinePowerCapacity;
    //public static int machinePowerTransfer = Defaults.machinePowerTransfer;
    public static int compressorPowerUsage = Defaults.compressorPowerUsage;
    public static int compressorAirGeneration = Defaults.compressorAirGeneration;
	
	//Oxygen Config
	public static int airtankCapacitySmall = Defaults.airtankCapacitySmall;
	public static int airtankCapacityMedium = Defaults.airtankCapacityMedium;
	public static int airtankCapacityLarge = Defaults.airtankCapacityLarge;
	
	//Crafting
	public static boolean allowPlateCrafting = Defaults.allowPlateCrafting;
	public static boolean allowRubberCrafting = Defaults.allowRubberCrafting;
	public static boolean allowSteelCrafting = Defaults.allowSteelCrafting;
	
	//tools
	public static int hammerUsesIron = Defaults.hammerUsesIron;
	public static int hammerUsesSteel = Defaults.hammerUsesSteel;
    
    //Render Config
    public static int powerItemDurablityColor = Defaults.powerItemDurablityColor;
    public static String powerItemDurablityType = Defaults.powerItemDurablityType;
    public static int oxygenItemDurablityColor = Defaults.oxygenItemDurablityColor;
    public static String oxygenItemDurablityType = Defaults.oxygenItemDurablityType;
    public static String scubaDurablityPriority = Defaults.scubaDurablityPriority;
    
    //Hud Overlay Config
    public static String hudPosition = Defaults.hudPosition;
    public static int hudOffsetX = Defaults.hudOffsetX;
    public static int hudOffsetY = Defaults.hudOffsetY;
    public static double hudScale = Defaults.hudScale;
    public static boolean enableHud = Defaults.enableHud;
	
    public ModConfig(boolean isClient)
	{
		this.isClientConfig = isClient;
	}
	
	public static void init(File configFile)
	{
		config = new Configuration(configFile);
		loadConfig(false);
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.getModID().equalsIgnoreCase(Reference.MODID))
		{
			loadConfig(false);
		}
	}
	
	public static void loadConfig(boolean load)
	{
		loadCommonConfig();
		if (isClientConfig) loadClientConfig();
		
		if (!config.hasChanged()) return;
		config.save();
	}
	
	private static void loadCommonConfig()
	{
		config.setCategoryComment("common", "Configuration for all Common configs");
		//Energy Config
		config.setCategoryComment("common.energy", "Configuration for all Power related configs");
		energyUnit = config.get("common.energy", "EnergyUnit", Defaults.energyUnit, "Sets the Power Unit to display").setValidValues(Defaults.energyUnits).getString();
		machinePowerCapacity = config.get("common.energy", "MachinePowerCapacity", Defaults.machinePowerCapacity, "Machine energy capacity.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.machinePowerCapacity);
		//machinePowerTransfer = config.get("common.energy", "MachinePowerTransfer", Defaults.machinePowerTransfer, "Machine energy transfer pr. tick.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.machinePowerTransfer);
		boatPowerCapacity = config.get("common.energy", "BoatPowerCapacity", Defaults.boatPowerCapacity, "Motorboat energy capacity.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.boatPowerCapacity);
		boatPowerUsage = config.get("common.energy", "BoatPowerUsage", Defaults.boatPowerUsage, "Motorboat energy usage pr. tick.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.boatPowerUsage);
		boatPowerTransfer = config.get("common.energy", "BoatPowerTransfer", Defaults.boatPowerTransfer, "Motorboat energy transfer pr. tick.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.boatPowerTransfer);
		boatBoostModifier = config.get("common.energy", "BoatBoostModifier", Defaults.boatBoostModifier, "Motorboat energy usage modifier when boosting.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.boatBoostModifier);
		//powercellCapacitySmall = config.get("common.energy", "PowercellCapacitySmall", Defaults.powercellCapacitySmall, "Small Powercell energy capacity.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.powercellCapacitySmall);
		//powercellCapacityMedium = config.get("common.energy", "PowercellCapacityMedium", Defaults.powercellCapacityMedium, "Medium Powercell energy capacity.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.powercellCapacityMedium);
		//powercellCapacityLarge = config.get("common.energy", "PowercellCapacityLarge", Defaults.powercellCapacityLarge, "Large Powercell energy capacity.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.powercellCapacityLarge);
		//Oxygen Config
		config.setCategoryComment("common.oxygen", "Configuration for all Oxygen related configs");
		airtankCapacitySmall = config.get("common.oxygen", "AirtankCapacitySmall", Defaults.airtankCapacitySmall, "Small Airtank capacity in ticks.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.airtankCapacitySmall);
		airtankCapacityMedium = config.get("common.oxygen", "AirtankCapacityMedium", Defaults.airtankCapacityMedium, "Medium Airtank capacity in ticks.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.airtankCapacityMedium);
		airtankCapacityLarge = config.get("common.oxygen", "AirtankCapacityLarge", Defaults.airtankCapacityLarge, "Large Airtank capacity in ticks.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.airtankCapacityLarge);
		
		//Machine Config
		config.setCategoryComment("common.machines", "Configuration for all Machine related configs");
		compressorPowerUsage = config.get("common.machines", "CompressorPowerUsage", Defaults.compressorPowerUsage, "Compressor energy usage pr. tick.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.compressorPowerUsage);
		compressorAirGeneration = config.get("common.machines", "CompressorAirGeneration", Defaults.compressorAirGeneration, "Compressor Oxygen Generation pr. tick.").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.compressorAirGeneration);

		//Crafting Config
		config.setCategoryComment("common.crafting", "Configuration for all Crafting related configs");
		allowPlateCrafting = config.get("common.crafting", "AllowPlateCrafting", Defaults.allowPlateCrafting, "Enables crafting of plates and hammers").setRequiresMcRestart(true).getBoolean(Defaults.allowPlateCrafting);
		allowRubberCrafting = config.get("common.crafting", "AllowRubberCrafting", Defaults.allowRubberCrafting, "Enables crafting of rubber compound and rubber").setRequiresMcRestart(true).getBoolean(Defaults.allowRubberCrafting);
		allowSteelCrafting = config.get("common.crafting", "AllowSteelCrafting", Defaults.allowSteelCrafting, "Enables smelting iron ingots into steel ingots").setRequiresMcRestart(true).getBoolean(Defaults.allowSteelCrafting);

		//Tools Config
		config.setCategoryComment("common.tools", "Configuration for all Tools related configs");
		hammerUsesIron = config.get("common.tools", "HammerUsesIron", Defaults.hammerUsesIron, "How many time the Iron Hammer can be used without breaking").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.hammerUsesIron);
		hammerUsesSteel = config.get("common.tools", "HammerUsesSteel", Defaults.hammerUsesSteel, "How many time the Steel Hammer can be used without breaking").setRequiresMcRestart(true).setMinValue(1).getInt(Defaults.hammerUsesSteel);
	}
	
	private static void loadClientConfig()
	{
		config.setCategoryComment("client", "Configuration for all Client configs");
		//Render Config
		config.setCategoryComment("client.render", "Configuration for all Rendering related configs");
		powerItemDurablityColor = config.get("client.render", "PowerItemDurablityColor", Defaults.powerItemDurablityColor, "Sets the color of powered items durablity bar. Calculation: (Red*256*256)+(Green*256)+Blue").setMinValue(0).setMaxValue(16777215).getInt(Defaults.powerItemDurablityColor);
		powerItemDurablityType = config.get("client.render", "PowerItemDurablityType", Defaults.powerItemDurablityType, "Sets the style to display the durablity bar of powered items.").setValidValues(Defaults.itemDurablityTypes).getString();
		oxygenItemDurablityColor = config.get("client.render", "OxygenItemDurablityColor", Defaults.oxygenItemDurablityColor, "Sets the color of oxygen tanks durablity bar. Calculation: (Red*256*256)+(Green*256)+Blue").setMinValue(0).setMaxValue(16777215).getInt(Defaults.oxygenItemDurablityColor);
		oxygenItemDurablityType = config.get("client.render", "OxygenItemDurablityType", Defaults.oxygenItemDurablityType, "Sets the style to display the durablity bar of oxygen tanks.").setValidValues(Defaults.itemDurablityTypes).getString();
		scubaDurablityPriority = config.get("client.render", "ScubaDurablityPriority", Defaults.scubaDurablityPriority, "Sets the prioritized durability type to display on scuba armor.").setValidValues(Defaults.scubaDurablityPriorities).getString();
		
		//Hud Overlay Config
		hudPosition = config.get("client.hud", "HudBasePosition", Defaults.hudPosition, "Set the base position of the Hud on the screen.").setValidValues(HUDPositions.getStringValues()).getString();
        hudOffsetX = config.get("client.hud", "HudOffset-X", Defaults.hudOffsetX, "The Hud display will be shifted horizontally by this value. This value may be negative.").getInt(Defaults.hudOffsetX);
        hudOffsetY = config.get("client.hud", "HudOffset-Y", Defaults.hudOffsetY, "The Hud display will be shifted vertically by this value. This value may be negative.").getInt(Defaults.hudOffsetY);
        hudScale = Math.abs(config.get("client.hud", "HudScale", Defaults.hudScale, "How large the Hud will be rendered. Default is 1.0, can be bigger or smaller").setMinValue(0.001D).getDouble(Defaults.hudScale));
        enableHud = config.get("client.hud", "EnableHud", Defaults.enableHud, "Enables the Hud overlay").getBoolean(Defaults.enableHud);
	}
}