package com.nhave.dse;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.nhave.dse.network.DSEPacketHandler;
import com.nhave.dse.proxy.CommonProxy;
import com.nhave.dse.registry.ModEntities;
import com.nhave.dse.registry.ModIntegration;
import com.nhave.dse.registry.ModItems;
import com.nhave.dse.registry.RegistryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUIFACTORY)
public class DeepSeaExpansion
{
	public static Logger logger;
	
	//Mod Integration
    public static boolean redstoneflux = false;
    
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
    
    @Mod.Instance(Reference.MODID)
	public static DeepSeaExpansion instance = new DeepSeaExpansion();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
		proxy.setupConfig(new File(event.getModConfigurationDirectory(), "deepseaexpansion.cfg"));
		
		MinecraftForge.EVENT_BUS.register(new RegistryHandler());
		
		ModEntities.init();
    	proxy.preInit(event);
    	
    	ModIntegration.preInit();
    	
    	DSEPacketHandler.preInit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    	
    	if (Loader.isModLoaded("redstoneflux"))
    	{
            redstoneflux = true;
            logger.info("RedstoneFlux found: enabling integration");
        }
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.registerEventHandlers();
    	
    	ModIntegration.postInit();
    }
    
    public static final CreativeTabs CREATIVETAB = new CreativeTabs(Reference.MODID)
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModItems.itemIcon);
		}
	};
}