package com.nhave.dse.shaders;

import java.util.ArrayList;
import java.util.List;

import com.nhave.dse.Reference;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class Shader
{
	private String id;
	private int[] colors;
	private String prefix = "";
	private List<Item> compatibleItems = new ArrayList<Item>();
	
	public Shader(String id)
	{
		this(id, 16777215, 16777215);
	}
	
	public Shader(String id, int baseColor, int lightColor)
	{
		this.id = id;
		this.colors = new int[2];
		this.colors[0] = baseColor;
		this.colors[1] = lightColor;
	}
	
	public String getID()
	{
		return this.id;
	}
	
	public int getShaderColor(int layer)
	{
		int layer1 = Math.max(0, Math.min(1, layer));
		return this.colors[layer1];
	}
	
	public Shader setNamePrefix(String color)
	{
		this.prefix = color;
		return this;
	}
	
	public String getNamePrefix()
	{
		return this.prefix;
	}
	
	public ResourceLocation getResourceLocation(String type)
	{
		return getResourceLocation(type, "", "");
	}
	
	public ResourceLocation getResourceLocation(String type, String baseLocation, String suffix)
	{
		return new ResourceLocation(Reference.MODID, baseLocation + "shaders/" + type + "_" + this.id + suffix);
	}
	
	public Shader addCompatibleItems(Item... items)
	{
		for (int i = 0; i < items.length; ++i)
		{
			this.compatibleItems.add(items[i]);
		}
		return this;
	}
	
	public boolean isItemCompatible(Item item)
	{
		return item != null && compatibleItems.contains(item);
	}
	
	public List<Item> getCompatibleItems()
	{
		return this.compatibleItems;
	}
}