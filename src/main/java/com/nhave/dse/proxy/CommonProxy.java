package com.nhave.dse.proxy;

import java.io.File;

import com.nhave.dse.eventhandlers.CommonEventHandler;
import com.nhave.dse.registry.ModConfig;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommonProxy
{
	public void setupConfig(File configFile)
	{
		FMLCommonHandler.instance().bus().register(new ModConfig(false));
		ModConfig.init(configFile);
	}
	
	public void registerRenders() {}
	
	public void registerEventHandlers()
	{
    	//MinecraftForge.EVENT_BUS.register(new ToolStationEventHandler());
    	MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    	//MinecraftForge.EVENT_BUS.register(new BlockBreakSpeedEventHandler());
    	//MinecraftForge.EVENT_BUS.register(new RebreatherEventHandler());
    	//MinecraftForge.EVENT_BUS.register(new ArmorsetScuba());
    	//MinecraftForge.EVENT_BUS.register(ModAchievementes.instance);
    	//FMLCommonHandler.instance().bus().register(ModAchievementes.instance);
	}
}