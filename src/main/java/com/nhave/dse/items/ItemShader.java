package com.nhave.dse.items;

import java.util.List;
import java.util.Map.Entry;

import com.nhave.dse.registry.ModItems;
import com.nhave.dse.shaders.Shader;
import com.nhave.dse.shaders.ShaderRegistry;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemShader extends ItemBase
{
	public ItemShader(String name)
	{
		super(name);
		this.setHasSubtypes(true);
		this.setQualityColor(StringUtils.PURPLE);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		if (getShader(stack) != null) return StringUtils.localize("shader.dse." + ItemNBTHelper.getString(stack, "SHADERS", "SHADER"));
		else return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (getShader(stack) == null)
		{
			tooltip.add(StringUtils.RED + StringUtils.localize("tooltip.nhc.error.missingnbt"));
			return;
		}
		else if (StringUtils.isShiftKeyDown())
		{
			tooltip.add(StringUtils.format(StringUtils.localize("tooltip.dse.shader"), StringUtils.GREEN, StringUtils.ITALIC));
			
			TooltipHelper.addHiddenTooltip(tooltip, "tooltip.dse.shader." + ItemNBTHelper.getString(stack, "SHADERS", "SHADER"), ";", StringUtils.GRAY);
			
			Shader shader = getShader(stack);
			tooltip.add(StringUtils.localize("tooltip.dse.mod.appliesto") + ":");
			
			boolean allTanks = false;
			if (shader.isItemCompatible(ModItems.itemAirTankSmall) && shader.isItemCompatible(ModItems.itemAirTankLarge) && shader.isItemCompatible(ModItems.itemAirTankHighPressure) && shader.isItemCompatible(ModItems.itemAirTankCreative))
			{
				tooltip.add("  " + StringUtils.format(StringUtils.localize("tooltip.dse.shader.airtank"), StringUtils.YELLOW, StringUtils.ITALIC));
				allTanks = true;
			}
			/*boolean allScuba = false;
			if (shader.isItemCompatible(ModItems.itemScubaMask) && shader.isItemCompatible(ModItems.itemScubaChest) && shader.isItemCompatible(ModItems.itemScubaLegs) && shader.isItemCompatible(ModItems.itemScubaBoots))
			{
				tooltip.add("  " + StringUtils.format(StringUtils.localize("tooltip.dse.shader.scuba"), StringUtils.YELLOW, StringUtils.ITALIC));
				allScuba = true;
			}*/
			
			List<Item> shaderItems = shader.getCompatibleItems();
			for (int i = 0; i < shaderItems.size(); ++i)
			{
				if (allTanks && (shader.isItemCompatible(ModItems.itemAirTankSmall) || shader.isItemCompatible(ModItems.itemAirTankLarge) || shader.isItemCompatible(ModItems.itemAirTankHighPressure) || shader.isItemCompatible(ModItems.itemAirTankCreative)))
				{
					continue;
				}
				/*if (allScuba && (shader.isItemCompatible(ModItems.itemScubaMask) || shader.isItemCompatible(ModItems.itemScubaChest) || shader.isItemCompatible(ModItems.itemScubaLegs) || shader.isItemCompatible(ModItems.itemScubaBoots)))
				{
					continue;
				}*/
				tooltip.add("  " + StringUtils.format(StringUtils.localize(shaderItems.get(i).getUnlocalizedName() + ".name"), StringUtils.YELLOW, StringUtils.ITALIC));
			}
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	public Shader getShader(ItemStack stack)
	{
		String name = ItemNBTHelper.getString(stack, "SHADERS", "SHADER");
		if (name != null && ShaderRegistry.getShader(name) != null) return ShaderRegistry.getShader(name);
		return null;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
        {
			if (!ShaderRegistry.isEmpty())
			{
				for(Entry<String, Shader> entry : ShaderRegistry.SHADERS.entrySet())
				{
					String key = entry.getKey();
					items.add(ItemNBTHelper.setString(new ItemStack(this), "SHADERS", "SHADER", key));
				}
			}
			else items.add(new ItemStack(this));
        }
	}
	
	@Override
	public String getQualityColor(ItemStack stack)
	{
		Shader shader = this.getShader(stack);
		if (shader != null)
		{
			return shader.getNamePrefix();
		}
		return super.getQualityColor(stack);
	}
}