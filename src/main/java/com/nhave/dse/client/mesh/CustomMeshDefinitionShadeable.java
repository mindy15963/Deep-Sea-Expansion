package com.nhave.dse.client.mesh;

import com.nhave.dse.items.ItemShader;
import com.nhave.nhc.util.ItemUtil;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class CustomMeshDefinitionShadeable implements ItemMeshDefinition
{
	private String type;
	
	public CustomMeshDefinitionShadeable(String type)
	{
		this.type = type;
	}
	
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		ItemStack stackShader = ItemUtil.getItemFromStack(stack, "SHADER");
		if (stackShader != null && stackShader.getItem() instanceof ItemShader && ((ItemShader) stackShader.getItem()).getShader(stackShader) != null)
		{
			return new ModelResourceLocation(((ItemShader) stackShader.getItem()).getShader(stackShader).getResourceLocation(this.type), "inventory");
		}
		return new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory");
	}
}
