package com.nhave.dse.registry;

public class Defaults
{
	//Energy Config
    public static final String[] energyUnits = new String[] {"RF", "FE", "Tesla"};
	public static final String energyUnit = "FE";
	public static final int boatPowerCapacity = 1000000;
    public static final int boatPowerUsage = 100;
    public static final int boatPowerTransfer = 4000;
    public static final int boatBoostModifier = 3;
	public static final int powercellCapacitySmall = 1000000;
	public static final int powercellCapacityMedium = 4000000;
	public static final int powercellCapacityLarge = 16000000;
	
	//Machine Config
	public static final int machinePowerCapacity = 20000;
    public static final int machinePowerTransfer = 1000;
    public static final int compressorPowerUsage = 100;
    public static final int compressorAirGeneration = 20;
	
	//Oxygen Config
	public static final int airtankCapacitySmall = 12000;
	public static final int airtankCapacityMedium = 24000;
	public static final int airtankCapacityLarge = 48000;
	
	//Crafting
	public static final boolean allowPlateCrafting = true;
	public static final boolean allowRubberCrafting = true;
	public static final boolean allowSteelCrafting = true;
	
	//tools
	public static final int hammerUsesIron = 250;
	public static final int hammerUsesSteel = 600;
    
    //Render Config
    public static final String[] itemDurablityTypes = new String[] {"SOLID", "VANILLA", "HIDDEN"};
    public static final String[] scubaDurablityPriorities = new String[] {"ENERGY", "OXYGEN"};
    public static final int powerItemDurablityColor = 16711680;
    public static final String powerItemDurablityType = "SOLID";
    public static final int oxygenItemDurablityColor = 65535;
    public static final String oxygenItemDurablityType = "SOLID";
    public static final String scubaDurablityPriority = "ENERGY";
}