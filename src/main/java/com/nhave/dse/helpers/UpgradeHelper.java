package com.nhave.dse.helpers;

import java.util.List;

import com.google.common.collect.Multimap;
import com.nhave.dse.entity.EntityMotorboat;
import com.nhave.dse.items.ItemArmorScuba;
import com.nhave.dse.items.ItemArmorplate;
import com.nhave.dse.items.ItemShader;
import com.nhave.dse.items.ItemSimpleUpgrades;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.registry.ModItems;
import com.nhave.nhc.api.items.INHWrench;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UpgradeHelper
{
    @SideOnly(Side.CLIENT)
	public static void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
	{
		if (stack == null || stack.isEmpty()) return;
		
		int meta = stack.getItemDamage();
		if (stack.getItem() == ModItems.itemSimpleUpgrades)
		{
			ItemSimpleUpgrades item = ((ItemSimpleUpgrades) stack.getItem());
			String name = item.names[Math.max(0, Math.min(item.names.length - 1, meta))];
			
			if (name.equals("boatbooster"))
			{
				tooltip.add(StringUtils.format(ModConfig.boatPowerUsage * ModConfig.boatBoostModifier + " " + ModConfig.energyUnit + "/t " + StringUtils.localize("tooltip.dse.charge.boatboost"), StringUtils.ORANGE));
			}
			else if (name.equals("lightfilter"))
			{
				//tooltip.add(StringUtils.format(400 + " " + ModConfig.energyUnit + " " + StringUtils.localize("tooltip.dse.charge.tick"), StringUtils.ORANGE));
			}
		}
		else if (stack.getItem() instanceof ItemArmorScuba)
		{
			if (stack.getItem() == ModItems.itemScubaMask)
			{
				if (ItemUtil.getItemFromStack(stack, "LIGHTFILTER") != null)
				{
					//tooltip.add(StringUtils.format(400 + " " + ModConfig.energyUnit + " " + StringUtils.localize("tooltip.dse.charge.tick"), StringUtils.ORANGE));
				}
			}
		}
	}
    
    public static void addAttributeModifiers(Multimap<String, AttributeModifier> multimap, ItemStack stack)
    {
    	if (stack.getItem() instanceof ItemArmorScuba)
		{
			ItemStack armorplate = ItemUtil.getItemFromStack(stack, "ARMORPLATE");
			if (armorplate != null && armorplate.getItem() == ModItems.itemArmorplate)
			{
				ItemArmorplate item = ((ItemArmorplate) armorplate.getItem());
				int meta = armorplate.getItemDamage();
				String name = item.names[Math.max(0, Math.min(item.names.length - 1, meta))];

				System.out.println(name);
				if (name.equals("hyperthermic"))
				{
					multimap.put("dse.hyperthermic", new AttributeModifier("dse.hyperthermic", 1D, 0));
				}
				else if (name.equals("hypothermic"))
				{
					multimap.put("dse.hypothermic", new AttributeModifier("dse.hypothermic", 1D, 0));
				}
			}
		}
    }
    
    public static boolean handleEntityUpgrade(World world, BlockPos pos, IBlockState state, Entity entity, EntityPlayer player)
    {
    	ItemStack stack = player.getHeldItemMainhand();
		int meta = stack.getItemDamage();
    	if (entity instanceof EntityMotorboat)
    	{
    		EntityMotorboat boat = (EntityMotorboat) entity;
    		if (stack.getItem() == ModItems.itemShader)
    		{
    			ItemStack boatShader = boat.getShader();
    			ItemShader shader = (ItemShader) stack.getItem();
    			if (boatShader != null && !boatShader.isEmpty() && boatShader.getItem() == ModItems.itemShader)
				{
					ItemShader itemShader = (ItemShader) boatShader.getItem();
					if (itemShader.getShader(boatShader) == itemShader.getShader(stack)) return false;
				}
    			
    			if (shader.getShader(stack).isItemCompatible(ModItems.itemMotorboat))
    			{
	    			boat.setShader(stack.copy());
	        		player.swingArm(EnumHand.MAIN_HAND);
	    			return true;
    			}
    		}
    		else if (stack.getItem() == ModItems.itemShaderCore && boat.getShader() != null && !boat.getShader().isEmpty())
    		{
    			boat.setShader(ItemStack.EMPTY.copy());
        		player.swingArm(EnumHand.MAIN_HAND);
    			return true;
    		}
    		else if (stack.getItem() == ModItems.itemSimpleUpgrades)
    		{
    			ItemSimpleUpgrades item = ((ItemSimpleUpgrades) stack.getItem());
    			String name = item.names[Math.max(0, Math.min(item.names.length - 1, meta))];
    			
    			if (name.equals("paddles") && !boat.hasPaddles())
    			{
    				boat.setHasPaddles(true);
    				player.getHeldItemMainhand().shrink(1);
            		player.swingArm(EnumHand.MAIN_HAND);
        			return true;
    			}
    			else if (name.equals("storagebox") && !boat.hasStorage())
    			{
    				boat.setHasStorage(true);
    				player.getHeldItemMainhand().shrink(1);
            		player.swingArm(EnumHand.MAIN_HAND);
        			return true;
    			}
    			else if (name.equals("boatbooster") && !boat.hasBooster())
    			{
    				boat.setHasBooster(true);
    				player.getHeldItemMainhand().shrink(1);
            		player.swingArm(EnumHand.MAIN_HAND);
        			return true;
    			}
    		}
    		else if (stack.getItem() instanceof INHWrench)
    		{
    			boolean changed = false;
    			if (boat.hasPaddles())
    			{
    				ItemHelper.addItemToPlayer(player, ModItems.createItemStack(ModItems.itemSimpleUpgrades, "paddles", 1));
    				boat.setHasPaddles(false);
    				changed = true;
    			}
    			if (boat.hasStorage())
    			{
    				ItemHelper.addItemToPlayer(player, ModItems.createItemStack(ModItems.itemSimpleUpgrades, "storagebox", 1));
    				boat.setHasStorage(false);
    				changed = true;
    			}
    			if (boat.hasBooster())
    			{
    				ItemHelper.addItemToPlayer(player, ModItems.createItemStack(ModItems.itemSimpleUpgrades, "boatbooster", 1));
    				boat.setHasBooster(false);
    				changed = true;
    			}
    			if (changed)
    			{
            		player.swingArm(EnumHand.MAIN_HAND);
        			return true;
    			}
    		}
    	}
    	return false;
    }
}