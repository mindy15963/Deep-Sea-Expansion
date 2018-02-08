package com.nhave.dse.api.items;

import com.nhave.dse.shaders.Shader;

import net.minecraft.item.ItemStack;

public interface IShaderItem
{
	public boolean hasShader(ItemStack stack);
	
	public Shader getShader(ItemStack stack);
}