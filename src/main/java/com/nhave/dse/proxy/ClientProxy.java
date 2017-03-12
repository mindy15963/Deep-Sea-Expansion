package com.nhave.dse.proxy;

import java.io.File;

import com.nhave.dse.client.eventhandlers.FogDensityEventHandler;
import com.nhave.dse.registry.ModItems;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void setupConfig(File configFile)
	{
		//FMLCommonHandler.instance().bus().register(new ModConfig(true));
		//ModConfig.init(configFile);
	}
	
	@Override
	public void registerRenders()
	{
		//FMLCommonHandler.instance().bus().register(new ClientTickEventHandler());
		ModItems.registerRenders();
	}
	
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
    	MinecraftForge.EVENT_BUS.register(new FogDensityEventHandler());
	}
}