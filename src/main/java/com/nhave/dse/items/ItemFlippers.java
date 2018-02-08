package com.nhave.dse.items;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.dse.Reference;
import com.nhave.dse.api.items.IFlippers;
import com.nhave.dse.api.items.IItemUpgrade;
import com.nhave.dse.client.models.ModelFlippers;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFlippers extends ItemArmorBase implements IFlippers, IItemUpgrade
{
	private static final String[] COLORS = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "lightGray", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	
	public ItemFlippers(String name, ArmorMaterial materialIn)
	{
		super(name, materialIn, 0, EntityEquipmentSlot.FEET);
		this.setQualityColor(StringUtils.LIGHT_BLUE);
		this.setHasSubtypes(true);
		this.setNoRepair();
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		int meta = Math.min(COLORS.length - 1, Math.max(0, stack.getItemDamage()));
		return StringUtils.localize("color.dse." + COLORS[meta]) + " " + super.getItemStackDisplayName(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item." + this.getItemName(stack)), ";", StringUtils.GRAY);
			//tooltip.add(StringUtils.localize("tooltip.dse.mod.canuse") + ":");
			//tooltip.add("  " + StringUtils.format(StringUtils.localize("item.dse.scubaboots.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		return HashMultimap.<String, AttributeModifier>create();
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
        {
			for (int i = 0; i < 16; ++i)
			{
				items.add(new ItemStack(this, 1, i));
			}
        }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot equipmentSlot, ModelBiped modelBiped)
	{
		return ModelFlippers.INSTANCE;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot equipmentSlot, String armorTexture)
	{
		return Reference.MODID + ":textures/models/armor/" + getItemName(stack) + "_" + stack.getMetadata() + ".png";
	}
	
	@Override
	public boolean isFlippersActive(EntityPlayer player, ItemStack stack)
	{
		return true;
	}

	@Override
	public boolean canApplyUpgrade(ItemStack upgradeable, ItemStack upgrade)
	{
		//return upgradeable.getItem() == ModItems.itemScubaBoots;
		return false;
	}

	@Override
	public String getUpgradeNBT(ItemStack upgradeable, ItemStack upgrade)
	{
		return "FLIPPERS";
	}
}