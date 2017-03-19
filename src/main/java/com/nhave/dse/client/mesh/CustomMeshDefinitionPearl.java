package com.nhave.dse.client.mesh;

import com.nhave.dse.Reference;
import com.nhave.dse.items.ItemPearl;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CustomMeshDefinitionPearl implements ItemMeshDefinition
{
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		ItemPearl pearl = (ItemPearl) stack.getItem();
		String name = (pearl.getSize(stack) >= pearl.maxPearlSize * 0.66 ? (pearl.isBlack(stack) ? "small_blackpearl" : "small_pearl") : (pearl.getSize(stack) >= pearl.maxPearlSize * 0.33 ? "tiny_pearl" : "micro_pearl"));
		return new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":" + name), "inventory");
	}
}
