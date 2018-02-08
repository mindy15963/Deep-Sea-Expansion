package com.nhave.dse.client.render;

import com.nhave.dse.items.ItemShader;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorHandler implements IItemColor
{
	public static final ItemColorHandler INSTANCE = new ItemColorHandler();
	
	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex)
	{
		if (stack.getItem() instanceof ItemShader)
		{
			ItemShader shader = (ItemShader) stack.getItem();
			return tintIndex > 0 && shader.getShader(stack) != null ? shader.getShader(stack).getShaderColor(tintIndex-1) : 16777215;
		}
		else return 16777215;
	}
}