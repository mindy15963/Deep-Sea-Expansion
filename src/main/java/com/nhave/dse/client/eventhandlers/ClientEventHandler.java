package com.nhave.dse.client.eventhandlers;

import java.util.List;

import com.nhave.dse.api.items.IDivingHelmet;
import com.nhave.dse.api.items.IFlippers;
import com.nhave.dse.items.ItemHeavyBoots;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientEventHandler
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void makeTooltip(ItemTooltipEvent event)
	{
		if (event.getItemStack() == null || event.getItemStack().isEmpty()) return;
		List<String> tooltip = event.getToolTip();
		ItemStack stack = event.getItemStack();
		
		if(stack.getItem().getRegistryName().toString().equals("theoneprobe:probe"))
		{
			if (StringUtils.isShiftKeyDown())
			{
				TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item.theoneprobe"), ";", StringUtils.GRAY);
				tooltip.add("");
				tooltip.add(StringUtils.localize("tooltip.dse.mod.canuse") + ":");
				tooltip.add("  " + StringUtils.format(StringUtils.localize("item.dse.scubamask.name"), StringUtils.YELLOW, StringUtils.ITALIC));
			}
			else tooltip.add(StringUtils.shiftForInfo);
		}
	}
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onFogDensityEvent(FogDensity event)
    {
		if (event.getEntity().isInsideOfMaterial(Material.WATER))
		{
			ItemStack goggles = mc.player.inventory.armorItemInSlot(3);
			if (goggles != null && goggles.getItem() instanceof IDivingHelmet && ((IDivingHelmet)goggles.getItem()).isHelmetActive(mc.player, goggles))
            {
				event.setDensity(0.1F);
				event.setCanceled(true);
            }
		} 		
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			
			ItemStack stack = player.inventory.armorItemInSlot(0);
			
			if (!player.capabilities.isFlying)
			{
				if (stack != null && !stack.isEmpty())
				{
					if (stack.getItem() instanceof IFlippers && ((IFlippers)stack.getItem()).isFlippersActive(player, stack))
					{
						if (player.isInWater())
						{
							player.motionX *= 1.15D;
							player.motionZ *= 1.15D;
							if (player.isSneaking() && player.isInsideOfMaterial(Material.WATER)) player.motionY = 0.0D;
							else player.motionY *= 1.1D;
						}
						else if (player.motionY == -0.0784000015258789)
						{
							player.motionX *= 0.5D;
							player.motionZ *= 0.5D;
						}
					}
					else if (stack.getItem() instanceof ItemHeavyBoots)
					{
						if (player.isInWater() && player.motionY < -0.0784000015258789)
						{
							player.motionY *= 1.25D;
						}
					}
				}
			}
		}
	}
}