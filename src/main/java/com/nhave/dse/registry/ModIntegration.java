package com.nhave.dse.registry;

import com.nhave.dse.compat.TOPCompatibility;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public class ModIntegration
{
	public static void preInit()
	{
		if (Loader.isModLoaded("theoneprobe"))
		{
            TOPCompatibility.register();
        }
	}
	
	public static void postInit()
	{
		if (Loader.isModLoaded("theoneprobe"))
		{
            ModItems.SCUBAMASK_UPGRADES.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("theoneprobe", "probe"))));
            ModItems.SCUBAMASK_UPGRADES_NBT.add("TOP");
        }
	}
}