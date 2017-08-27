package com.nhave.dse.client.eventhandlers;

import com.nhave.dse.api.items.IDivingHelmet;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientEventHandler
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(FogDensity event)
    {
		if (event.getEntity().isInsideOfMaterial(Material.WATER))
		{
			//ItemStack goggles = ItemHelper.getCurrentItemOrArmor(mc.thePlayer, 4);
			ItemStack goggles = mc.player.inventory.armorItemInSlot(3);
			if (goggles != null && goggles.getItem() instanceof IDivingHelmet && ((IDivingHelmet)goggles.getItem()).isHelmetActive(mc.player, goggles))
            {
				event.setDensity(0.1F);
				event.setCanceled(true);
            }
		} 		
    }
}