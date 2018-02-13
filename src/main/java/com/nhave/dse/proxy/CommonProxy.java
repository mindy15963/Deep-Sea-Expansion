package com.nhave.dse.proxy;

import java.io.File;

import com.nhave.dse.eventhandlers.BlockBreakSpeedEventHandler;
import com.nhave.dse.eventhandlers.ToolStationEventHandler;
import com.nhave.dse.registry.ModConfig;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void setupConfig(File configFile)
	{
		MinecraftForge.EVENT_BUS.register(new ModConfig(false));
		ModConfig.init(configFile);
	}
	
	public void preInit(FMLPreInitializationEvent event) {}
	
	public void registerRenders() {}
	
	public void registerEventHandlers()
	{
    	MinecraftForge.EVENT_BUS.register(new ToolStationEventHandler());
    	MinecraftForge.EVENT_BUS.register(new BlockBreakSpeedEventHandler());
	}
}