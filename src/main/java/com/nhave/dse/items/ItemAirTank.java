package com.nhave.dse.items;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.dse.Reference;
import com.nhave.dse.api.items.IAirTankItem;
import com.nhave.dse.api.items.IItemUpgrade;
import com.nhave.dse.api.items.IShaderItem;
import com.nhave.dse.client.models.ModelScubaTankLarge;
import com.nhave.dse.client.models.ModelScubaTankSmall;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.shaders.Shader;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAirtank extends ItemArmorBase implements IAirTankItem, IShaderItem, IItemUpgrade
{
	public boolean isCreative = false;
	private boolean isDualTank;
	private int maxAir;
	
	public ItemAirtank(String name, ArmorMaterial material, int maxAir, boolean dual)
	{
		super(name, material, 0, EntityEquipmentSlot.CHEST);
		this.isDualTank = dual;
		this.maxAir = maxAir;
		if (maxAir <= 0) this.isCreative = true;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item.airtank" + (this.isCreative ? ".creative" : "")), ";", StringUtils.GRAY);
			
			String shaderName = StringUtils.localize("tooltip.dse.shader.none");
			String color = StringUtils.YELLOW;
			if (hasShader(stack))
			{
				shaderName = ItemUtil.getItemFromStack(stack, "SHADER").getDisplayName();
				color = getShader(stack).getNamePrefix();
			}
			tooltip.add(StringUtils.localize("tooltip.dse.shader.current") + ": " + StringUtils.format(shaderName, color, StringUtils.ITALIC));
			
			tooltip.add(StringUtils.localize("tooltip.dse.oxygen") + ": " + StringUtils.format(getOxygenInfo(stack), StringUtils.YELLOW, StringUtils.ITALIC));
			
			//tooltip.add(StringUtils.localize("tooltip.dse.mod.canuse") + ":");
			//tooltip.add("  " + StringUtils.format(StringUtils.localize("item.dse.scubachest.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	public String getOxygenInfo(ItemStack itemStack)
	{
		double time = getOxygenStored(itemStack)/20D;
		double minutes = Math.floor(time / 60);
		double seconds = Math.floor(time - minutes * 60);
		boolean showMinutes = (int)minutes > 0;
		return (this.isCreative ? "∞" : (showMinutes ? (int)minutes + "m:" : "") + (int)seconds + "s");
	}
	
	@Override
	public boolean hasShader(ItemStack stack)
	{
		ItemStack stackShader = ItemUtil.getItemFromStack(stack, "SHADER");
		return stackShader != null && stackShader.getItem() instanceof ItemShader && ((ItemShader) stackShader.getItem()).getShader(stackShader) != null;
	}
	
	@Override
	public Shader getShader(ItemStack stack)
	{
		if (hasShader(stack))
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(stack, "SHADER");
			return ((ItemShader) stackShader.getItem()).getShader(stackShader);
		}
		else return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot equipmentSlot, ModelBiped modelBiped)
	{
		ModelBiped model = null;
		if (this.isDualTank) model = ModelScubaTankLarge.NOARMOR;
		else model = ModelScubaTankSmall.NOARMOR;
		return model;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot equipmentSlot, String armorTexture)
	{
		if (this.hasShader(stack))
		{
			return this.getShader(stack).getResourceLocation("airtank", "textures/models/armor/", ".png").toString();
		}
		return Reference.MODID + ":textures/models/armor/" + getItemName(stack) + ".png";
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return !this.isCreative && !ModConfig.oxygenItemDurablityType.equals("HIDDEN");
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
	    double maxAmount = this.getMaxOxygen(stack);
	    double dif = maxAmount-this.getOxygenStored(stack);
	    return dif/maxAmount;
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
	{
		if (ModConfig.oxygenItemDurablityType.equals("SOLID")) return ModConfig.oxygenItemDurablityColor;
		else return super.getRGBDurabilityForDisplay(stack);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		return HashMultimap.<String, AttributeModifier>create();
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (!player.capabilities.isCreativeMode && player.isInsideOfMaterial(Material.WATER))
		{
			if (getOxygenStored(stack) > 0 || this.isCreative)
			{
				player.setAir(player.getAir()+1);
				setOxygen(stack, getOxygenStored(stack) - 1);
			}
		}
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
        {
			ItemStack noO2 = new ItemStack(this);
			items.add(noO2);
			
			if (!this.isCreative)
			{
				ItemStack maxO2 = new ItemStack(this);
				setOxygen(maxO2, getMaxOxygen(maxO2));
				items.add(maxO2);
			}
        }
	}
	
	@Override
	public int getOxygenStored(ItemStack itemStack)
	{
		if (this.isCreative) return 0;
		if(itemStack.getTagCompound() == null)
		{
			return 0;
		}
		
		int o2Stored = Math.min(itemStack.getTagCompound().getInteger("o2"), this.maxAir);
		
		return o2Stored;
	}
	
	@Override
	public void setOxygen(ItemStack itemStack, int amount)
	{
		if (this.isCreative) return;
		if(itemStack.getTagCompound() == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
		
		int o2Stored = Math.max(Math.min(amount, getMaxOxygen(itemStack)), 0);
		itemStack.getTagCompound().setDouble("o2", o2Stored);
	}
	
	@Override
	public int getMaxOxygen(ItemStack itemStack)
	{
		return this.maxAir;
	}
	
	@Override
	public boolean isDualTank(ItemStack itemStack)
	{
		return this.isDualTank;
	}

	@Override
	public boolean canApplyUpgrade(ItemStack upgradeable, ItemStack upgrade)
	{
		//return upgradeable.getItem() == ModItems.itemScubaChest;
		return false;
	}

	@Override
	public String getUpgradeNBT(ItemStack upgradeable, ItemStack upgrade)
	{
		return "AIRTANK";
	}
}