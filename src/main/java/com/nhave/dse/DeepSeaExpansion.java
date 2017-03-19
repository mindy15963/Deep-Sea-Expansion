package com.nhave.dse;

import org.apache.logging.log4j.Logger;

import com.nhave.dse.proxy.CommonProxy;
import com.nhave.dse.registry.ModBlocks;
import com.nhave.dse.registry.ModCrafting;
import com.nhave.dse.registry.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSIONS)//, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUIFACTORY)
public class DeepSeaExpansion
{
    public static Logger logger;
    
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
    
    @Mod.Instance(Reference.MODID)
	public static DeepSeaExpansion instance = new DeepSeaExpansion();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
		proxy.setupConfig(event.getSuggestedConfigurationFile());

    	ModItems.init();
    	ModItems.register();
    	ModBlocks.init();
    	ModBlocks.register();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.registerEventHandlers();
    	ModCrafting.init();
    	//ModIntegration.postInit();
    }
    
    public static final CreativeTabs CREATIVETABBLOCKS = new CreativeTabs("dse.blocks")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModBlocks.blockOyster);
		}
		
		public String getBackgroundImageName()
		{
			return "dse.png";
		};
	};
    
    public static final CreativeTabs CREATIVETABITEMS = new CreativeTabs("dse.items")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModItems.itemComp, 1, 1);
		}
		
		public String getBackgroundImageName()
		{
			return "dse.png";
		};
	};
    
    public static final CreativeTabs CREATIVETABTOOLS = new CreativeTabs("dse.tools")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModItems.itemHammerIron);
		}
		
		public String getBackgroundImageName()
		{
			return "dse.png";
		};
	};
}