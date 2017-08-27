package com.nhave.dse.registry;

import java.io.File;

import com.nhave.dse.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModConfig
{
	public static boolean isClientConfig;
	
	public static Configuration config;
	
    //Pressure
    public static boolean displayPressure = Defaults.displayPressure;
    public static String pressureUnit = Defaults.pressureUnit;
    //Crafting
	public static boolean allowPlateCrafting = Defaults.allowPlateCrafting;
	public static boolean ironToSteel = Defaults.ironToSteel;
    public static int hammerDurabilityIron = Defaults.hammerDurabilityIron;
    public static int hammerDurabilitySteel = Defaults.hammerDurabilitySteel;
	
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
	
	public static void loadCommonConfig()
	{
		//core
		config.setCategoryComment("common", "Configuration for all Common configs");
		//Pressure
		config.setCategoryComment("common.pressure", "Pressure related configs");
		displayPressure = config.get("common.pressure", "DisplayPressure", Defaults.displayPressure, "Enable/Disable Pressure Display").getBoolean(Defaults.displayPressure);
		pressureUnit = config.get("common.pressure", "PressureUnit", Defaults.pressureUnit, "Sets the Pressure Unit to display").setValidValues(Defaults.pressureUnits).getString();
		//Crafting
		config.setCategoryComment("common.crafting", "Crafting related configs");
		allowPlateCrafting = config.get("common.crafting", "AllowPlateCrafting", Defaults.allowPlateCrafting, "Allow crafting of plates").setRequiresMcRestart(true).getBoolean(Defaults.allowPlateCrafting);
		ironToSteel = config.get("common.crafting", "IronToSteel", Defaults.ironToSteel, "Allow turning Iron into Steel in a furnace").setRequiresMcRestart(true).getBoolean(Defaults.ironToSteel);
		hammerDurabilityIron = config.get("common.crafting", "HammerDurabilityIron", Defaults.hammerDurabilityIron, "Sets the durability of the Iron Hammer").setRequiresMcRestart(true).getInt(Defaults.hammerDurabilityIron);
		hammerDurabilitySteel = config.get("common.crafting", "HammerDurabilitySteel", Defaults.hammerDurabilitySteel, "Sets the durability of the Steel Hammer").setRequiresMcRestart(true).getInt(Defaults.hammerDurabilitySteel);
	}
	
	public static void loadClientConfig() {}
}