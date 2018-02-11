package com.nhave.dse.eventhandlers;

import java.util.List;

import com.nhave.dse.api.items.IItemUpgrade;
import com.nhave.dse.api.items.IShaderItem;
import com.nhave.dse.items.ItemMotorboat;
import com.nhave.dse.items.ItemShader;
import com.nhave.dse.registry.ModItems;
import com.nhave.dse.shaders.Shader;
import com.nhave.nhc.api.items.INHWrench;
import com.nhave.nhc.events.ToolStationCraftingEvent;
import com.nhave.nhc.events.ToolStationUpdateEvent;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.util.ItemUtil;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler
{
	@SubscribeEvent
	public void handleToolStationEvent(ToolStationUpdateEvent evt)
	{
		if (evt.input == null || evt.mod == null)
		{
			return;
		}
		/*if (evt.input.getItem() instanceof ItemMotorboat)
		{
			if (evt.mod.getItem() instanceof ItemSimpleUpgrades)
			{
				for (int i = 0; i < ModItems.MOTORBOAT_UPGRADES.size(); ++i)
				{
					ItemStack mod = ModItems.MOTORBOAT_UPGRADES.get(i);
					if (mod.getItem() == evt.mod.getItem() && mod.getItemDamage() == evt.mod.getItemDamage())
					{
						String nbt = ((ItemSimpleUpgrades) evt.mod.getItem()).upgradeNBT[evt.mod.getItemDamage()];
						if (ItemUtil.getItemFromStack(evt.input, nbt) != null) return;
						ItemStack output = evt.input.copy();
						ItemStack upgrade = evt.mod.copy();
						upgrade.setCount(1);
						ItemUtil.addItemToStack(output, upgrade, nbt);
						evt.materialCost=1;
						evt.output=output;
					}
				}
			}
		}
		else*/ if (evt.mod.getItem() instanceof IItemUpgrade && ((IItemUpgrade) evt.mod.getItem()).canApplyUpgrade(evt.input, evt.mod))
		{
			IItemUpgrade upgrade = (IItemUpgrade) evt.mod.getItem();
			String nbt = upgrade.getUpgradeNBT(evt.input, evt.mod);
			if (ItemUtil.getItemFromStack(evt.input, nbt) != null) return;
			ItemStack output = evt.input.copy();
			ItemStack mod = evt.mod.copy();
			mod.setCount(1);
			ItemUtil.addItemToStack(output, mod, nbt);
			evt.materialCost=1;
			evt.output=output;
		}
		else if (evt.input.getItem() instanceof IShaderItem && evt.mod.getItem() == ModItems.itemShader)
		{
			Shader mod = ((ItemShader) evt.mod.getItem()).getShader(evt.mod);
			if (mod != null && mod.isItemCompatible(evt.input.getItem()))
			{
				ItemStack stackShader = ItemUtil.getItemFromStack(evt.input, "SHADER");
				if (stackShader != null && !stackShader.isEmpty() && stackShader.getItem() == ModItems.itemShader)
				{
					ItemShader itemShader = (ItemShader) stackShader.getItem();
					if (itemShader.getShader(stackShader) == itemShader.getShader(evt.mod)) return;
				}
				
				ItemStack stack = evt.input.copy();
				ItemStack shader = evt.mod.copy();
				shader.setCount(1);
				ItemUtil.addItemToStack(stack, shader, "SHADER");
				evt.materialCost=0;
				evt.output=stack;
			}
		}
		else if (evt.input.getItem() instanceof IShaderItem && evt.mod.getItem() == ModItems.itemShaderCore)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.input, "SHADER");
			if (stackShader == null || stackShader.isEmpty()) return;
			
			ItemStack stack = evt.input.copy();
			ItemUtil.removeAllItemFromStack(stack, "SHADER");
			evt.materialCost=0;
			evt.output=stack;
		}
		else if (evt.mod.getItem() instanceof INHWrench)
		{
			List<String> upgrades = null;
			
			if (evt.input.getItem() instanceof ItemMotorboat) upgrades = ModItems.MOTORBOAT_UPGRADES_NBT;
			//else if (evt.input.getItem() == ModItems.itemScubaMask) upgrades = ModItems.SCUBAMASK_UPGRADES_NBT;
			//else if (evt.input.getItem() == ModItems.itemScubaChest) upgrades = ModItems.SCUBACHEST_UPGRADES_NBT;
			//else if (evt.input.getItem() == ModItems.itemScubaLegs) upgrades = ModItems.SCUBALEGS_UPGRADES_NBT;
			//else if (evt.input.getItem() == ModItems.itemScubaBoots) upgrades = ModItems.SCUBABOOTS_UPGRADES_NBT;
			
			if (upgrades == null) return;
			
			boolean changed = false;
			ItemStack output = evt.input.copy();
			for (int i = 0; i < upgrades.size(); ++i)
			{
				if (ItemUtil.getItemFromStack(evt.input, upgrades.get(i)) != null)
				{
					ItemUtil.removeAllItemFromStack(output, upgrades.get(i));
					changed = true;
				}
			}
			if (changed)
			{
				evt.materialCost=0;
				evt.output=output;
			}
			else return;
		}
	}
	
	@SubscribeEvent
	public void onToolStationCrafting(ToolStationCraftingEvent evt)
	{
		if (evt.input == null || evt.mod == null)
		{
			return;
		}
		else if (evt.mod.getItem() instanceof INHWrench)
		{
			List<String> upgrades = null;

			if (evt.input.getItem() instanceof ItemMotorboat) upgrades = ModItems.MOTORBOAT_UPGRADES_NBT;
			//else if (evt.input.getItem() == ModItems.itemScubaMask) upgrades = ModItems.SCUBAMASK_UPGRADES_NBT;
			//else if (evt.input.getItem() == ModItems.itemScubaChest) upgrades = ModItems.SCUBACHEST_UPGRADES_NBT;
			//else if (evt.input.getItem() == ModItems.itemScubaLegs) upgrades = ModItems.SCUBALEGS_UPGRADES_NBT;
			//else if (evt.input.getItem() == ModItems.itemScubaBoots) upgrades = ModItems.SCUBABOOTS_UPGRADES_NBT;
			
			if (upgrades != null)
			{
				for (int i = 0; i < upgrades.size(); ++i)
				{
					ItemStack mod = ItemUtil.getItemFromStack(evt.input, upgrades.get(i));
					if (mod != null)
					{
						ItemHelper.addItemToPlayer(evt.getEntityPlayer(), mod.copy());
					}
				}
			}
		}
	}
}