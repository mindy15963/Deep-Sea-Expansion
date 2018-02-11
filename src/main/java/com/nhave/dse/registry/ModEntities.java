package com.nhave.dse.registry;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;
import com.nhave.dse.client.render.RenderDinghy;
import com.nhave.dse.client.render.RenderMotorboat;
import com.nhave.dse.entity.EntityDinghy;
import com.nhave.dse.entity.EntityMotorboat;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities
{
	public static void init()
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "dinghy"), EntityDinghy.class, Reference.MODID + "." + "dinghy", 0, DeepSeaExpansion.instance, 80, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "motorboat"), EntityMotorboat.class, Reference.MODID + "." + "motorboat", 1, DeepSeaExpansion.instance, 80, 3, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityDinghy.class, RenderDinghy::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMotorboat.class, RenderMotorboat::new);
	}
}