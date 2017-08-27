package com.nhave.dse.recipe;

import com.nhave.dse.api.items.IAirTank;
import com.nhave.dse.api.items.IPowerItem;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedNBTRecipe extends ShapedOreRecipe implements IRecipe
{
	public ShapedNBTRecipe(Block result, Object... recipe) { this(new ItemStack(result), recipe); }
	public ShapedNBTRecipe(Item result, Object... recipe) { this(new ItemStack(result), recipe); }
	
	public ShapedNBTRecipe(ItemStack result, Object[] recipe)
	{
		super(result, recipe);
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1)
	{
		ItemStack result = super.getCraftingResult(var1);
		if (result.getItem() instanceof IAirTank)
		{
			int air = 0;
			for (int i = 0; i < var1.getSizeInventory(); ++i)
			{
				ItemStack slot = var1.getStackInSlot(i);
				if (slot != null && slot.getItem() instanceof IAirTank) air += ((IAirTank)slot.getItem()).getO2(slot);
			}
			if (air > 0) ((IAirTank)result.getItem()).setO2(result, air);
		}
		if (result.getItem() instanceof IPowerItem)
		{
			int power  = 0;
			for (int i = 0; i < var1.getSizeInventory(); ++i)
			{
				ItemStack slot = var1.getStackInSlot(i);
				if (slot != null && slot.getItem() instanceof IPowerItem) power += ((IPowerItem)slot.getItem()).getPower(slot);
			}
			if (power > 0) ((IPowerItem)result.getItem()).setPower(result, power);
		}
		return result;
	}
}