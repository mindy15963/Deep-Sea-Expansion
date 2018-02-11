package com.nhave.dse.registry;

import com.nhave.dse.compat.TOPCompatibility;

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
}