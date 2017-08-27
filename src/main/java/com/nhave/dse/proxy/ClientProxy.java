package com.nhave.dse.proxy;

import java.io.File;

import com.nhave.dse.client.eventhandlers.ClientEventHandler;
import com.nhave.dse.registry.ModBlocks;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.registry.ModItems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void setupConfig(File configFile)
	{
		FMLCommonHandler.instance().bus().register(new ModConfig(true));
		ModConfig.init(configFile);
	}
	
	@Override
	public void registerRenders()
	{
		ModItems.registerRenders();
		ModItems.registerWidgets();
		ModBlocks.registerRenders();
	}
	
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
    	MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}
}