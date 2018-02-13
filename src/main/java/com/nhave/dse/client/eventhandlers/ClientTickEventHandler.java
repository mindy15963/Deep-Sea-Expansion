package com.nhave.dse.client.eventhandlers;

import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.util.ItemUtil;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ClientTickEventHandler
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final PotionEffect nightVisionEffect = new PotionEffect(MobEffects.NIGHT_VISION, 300, 1, false, false);
	
	public void tickStart(RenderTickEvent event)
	{
		EntityPlayer player = mc.player;
		if (player != null)
        {
			if (isSetComplete(player))
			{
				ItemStack mask = ItemHelper.getCurrentItemOrArmor(player, 5);
				ItemStack chest = ItemHelper.getCurrentItemOrArmor(player, 4);
				if(hasPower(player) && ItemUtil.getItemFromStack(mask, "LIGHTFILTER") != null && player.isInsideOfMaterial(Material.WATER))
				{
					nightVisionEffect.setPotionDurationMax(true);
					player.addPotionEffect(nightVisionEffect);
				}
			}
        }
	}
	
	public void tickEnd(RenderTickEvent event)
	{
		EntityPlayer player = mc.player;
		if (player != null)
		{
			PotionEffect eff = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
			if ((eff != null) && (eff.getAmplifier() == 1))
			{
				player.removePotionEffect(MobEffects.NIGHT_VISION);
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event)
	{
		if (event.phase == Phase.START)
		{
			tickStart(event);
		}
		else if (event.phase == Phase.END)
		{
			tickEnd(event);
		}
	}
	
	public boolean hasPower(EntityPlayer player)
	{
		/*ItemStack chest = ItemHelper.getCurrentItemOrArmor(player, 3);
		ItemScubaArmor item = ((ItemScubaArmor)chest.getItem());
		double powerUse = ModConfig.activeFilterUse * ((ItemScubaArmor)chest.getItem()).getEfficiencyModifier(chest) * ModConfig.FROM_TE;
		return (item.getEnergy(chest) > powerUse) || (item.hasPocketReactor(chest) && item.getReactorComp(player) <= item.getReactorCompCap(chest));*/
		return true;
	}
	
	public static boolean isSetComplete(EntityPlayer player)
	{
		return true;
		//return (checkArmor(player, 5, ModItems.itemScubaMask) && checkArmor(player, 4, ModItems.itemScubaChest) && checkArmor(player, 3, ModItems.itemScubaLegs) && checkArmor(player, 2, ModItems.itemScubaBoots));
	}
	
	public static boolean checkArmor(EntityPlayer player, int slot, Item item)
	{
		return (ItemHelper.getCurrentItemOrArmor(player, slot) != null && ItemHelper.getCurrentItemOrArmor(player, slot).getItem() == item);
	}
}