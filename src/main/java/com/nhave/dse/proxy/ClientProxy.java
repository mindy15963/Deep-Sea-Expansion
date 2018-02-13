package com.nhave.dse.proxy;

import java.io.File;

import com.nhave.dse.client.eventhandlers.ClientEventHandler;
import com.nhave.dse.client.eventhandlers.ClientTickEventHandler;
import com.nhave.dse.client.tickhandlers.OverlayTickHandler;
import com.nhave.dse.registry.ClientRegistryHandler;
import com.nhave.dse.registry.ModBlocks;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.registry.ModEntities;
import com.nhave.dse.registry.ModItems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void setupConfig(File configFile)
	{
		MinecraftForge.EVENT_BUS.register(new ModConfig(true));
		ModConfig.init(configFile);
	}
	
	@Override
    public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new ClientRegistryHandler());
		ModEntities.registerRenders();
    }
	
	@Override
	public void registerRenders()
	{
		FMLCommonHandler.instance().bus().register(new OverlayTickHandler());
		FMLCommonHandler.instance().bus().register(new ClientTickEventHandler());
        ModItems.registerRenderData();
		ModBlocks.registerRenderData();
	}
	
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}
}