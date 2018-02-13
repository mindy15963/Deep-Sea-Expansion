package com.nhave.dse.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.dse.Reference;
import com.nhave.dse.api.items.IAirTankItem;
import com.nhave.dse.api.items.IDivingHelmet;
import com.nhave.dse.api.items.IFlippers;
import com.nhave.dse.api.items.IItemUpgrade;
import com.nhave.dse.api.items.IItemUpgradeAdvanced;
import com.nhave.dse.api.items.IShaderItem;
import com.nhave.dse.client.models.ModelFlippers;
import com.nhave.dse.client.models.ModelScubaTankLarge;
import com.nhave.dse.client.models.ModelScubaTankSmall;
import com.nhave.dse.helpers.UpgradeHelper;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.registry.ModItems;
import com.nhave.dse.shaders.Shader;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorScuba extends ItemArmorBase implements IShaderItem, IFlippers, IDivingHelmet, IAirTankItem
{
    private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    
	public ItemArmorScuba(String name, ArmorMaterial materialIn, EntityEquipmentSlot slot)
	{
		super(name, materialIn, 0, slot);
		this.setMaxDamage(0);
		this.setQualityColor(StringUtils.ORANGE);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced)
	{
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item." + getItemName(stack)), ";", StringUtils.GRAY);
			
			String shaderName = StringUtils.localize("tooltip.dse.shader.none");
			String color = StringUtils.YELLOW;
			if (hasShader(stack))
			{
				shaderName = ItemUtil.getItemFromStack(stack, "SHADER").getDisplayName();
				color = getShader(stack).getNamePrefix();
			}
			tooltip.add(StringUtils.localize("tooltip.dse.shader.current") + ": " + StringUtils.format(shaderName, color, StringUtils.ITALIC));
			
			if (hasAirTank(stack)) tooltip.add(StringUtils.localize("tooltip.dse.oxygen") + ": " + StringUtils.format(getOxygenInfo(stack), StringUtils.YELLOW, StringUtils.ITALIC));
			
			UpgradeHelper.addInformation(stack, worldIn, tooltip, advanced);
		}
		if (StringUtils.isControlKeyDown())
		{
			tooltip.add(StringUtils.localize("tooltip.dse.mod.all") + ":");
			
			List<ItemStack> upgrades = new ArrayList<ItemStack>();
			List<String> upgrades_nbt = new ArrayList<String>();
			
			if (this.armorType == armorType.HEAD)
			{
				upgrades = ModItems.SCUBAMASK_UPGRADES;
				upgrades_nbt = ModItems.SCUBAMASK_UPGRADES_NBT; 
			}
			else if (this.armorType == armorType.CHEST)
			{
				upgrades = ModItems.SCUBACHEST_UPGRADES;
				upgrades_nbt = ModItems.SCUBACHEST_UPGRADES_NBT; 
			}
			else if (this.armorType == armorType.LEGS)
			{
				upgrades = ModItems.SCUBALEGS_UPGRADES;
				upgrades_nbt = ModItems.SCUBALEGS_UPGRADES_NBT; 
			}
			else if (this.armorType == armorType.FEET)
			{
				upgrades = ModItems.SCUBABOOTS_UPGRADES;
				upgrades_nbt = ModItems.SCUBABOOTS_UPGRADES_NBT; 
			}
			
			for (int i = 0; i < upgrades.size(); ++i)
			{
				ItemStack mod = upgrades.get(i);
				String color = StringUtils.RED;
				
				for (int j = 0; j < upgrades_nbt.size(); ++j)
				{
					ItemStack installed = ItemUtil.getItemFromStack(stack, upgrades_nbt.get(j));
					
					if (installed != null && installed.getItem() == mod.getItem() && ((mod.getItem() instanceof IItemUpgradeAdvanced && ((IItemUpgradeAdvanced) mod.getItem()).ignoreMeta(mod)) || (installed.getItemDamage() == mod.getItemDamage())))
					{
						color = StringUtils.BRIGHT_GREEN;
					}
				}
				
				if ((mod.getItem() instanceof IItemUpgrade && ItemUtil.getItemFromStack(stack, ((IItemUpgrade) mod.getItem()).getUpgradeNBT(stack, mod)) != null))
				{
					if (color == StringUtils.BRIGHT_GREEN) tooltip.add("  " + StringUtils.format(ItemUtil.getItemFromStack(stack, ((IItemUpgrade) mod.getItem()).getUpgradeNBT(stack, mod)).getDisplayName(), color, StringUtils.ITALIC));
					continue;
				}
				
				if (mod.getItem() instanceof IItemUpgradeAdvanced) tooltip.add("  " + StringUtils.format(((IItemUpgradeAdvanced) mod.getItem()).getUpgradeName(mod), color, StringUtils.ITALIC));
				else tooltip.add("  " + StringUtils.format(mod.getDisplayName(), color, StringUtils.ITALIC));
			}
			
			if (upgrades.isEmpty()) tooltip.add("  " + StringUtils.format(StringUtils.localize("tooltip.dse.mod.none"), StringUtils.RED, StringUtils.ITALIC));
		}
		if (!StringUtils.isControlKeyDown() && !StringUtils.isShiftKeyDown())
		{
			tooltip.add(StringUtils.shiftForInfo);
			tooltip.add(StringUtils.localize("§7" + StringUtils.localize("tooltip.nhc.details.ctrl1") + " §e§o" + StringUtils.localize("tooltip.nhc.details.ctrl2") + " §r§7" + StringUtils.localize("tooltip.dse.mod.ctrlinfo") + "§r"));
		}
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
		
		if (slot == this.armorType)
        {
			double damageReduceAmount = (double)this.damageReduceAmount;
			double toughness = (double)this.toughness;
			
			ItemStack armorplate = ItemUtil.getItemFromStack(stack, "ARMORPLATE");
			if (armorplate != null && armorplate.getItem() instanceof ItemArmorplate)
			{
				ItemArmorplate plate = (ItemArmorplate) armorplate.getItem();
				damageReduceAmount = plate.getDamageReduction(armorplate, slot);
				//System.out.println("test");
			}
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", damageReduceAmount, 0));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", toughness, 0));
        }

        return multimap;
	}
	
	public String getOxygenInfo(ItemStack itemStack)
	{
		double time = getOxygenStored(itemStack)/20D;
		double minutes = Math.floor(time / 60);
		double seconds = Math.floor(time - minutes * 60);
		boolean showMinutes = (int)minutes > 0;
		return (getMaxOxygen(itemStack) < 0 ? "∞" : (showMinutes ? (int)minutes + "m:" : "") + (int)seconds + "s");
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot equipmentSlot, String armorTexture)
	{
		String suffix = (equipmentSlot == EntityEquipmentSlot.HEAD ? (entity.isInsideOfMaterial(Material.WATER) ? "_water" : "_land") : (equipmentSlot == EntityEquipmentSlot.LEGS ? "_2" : "_1"));
		if (this.hasShader(stack))
		{
			return this.getShader(stack).getResourceLocation("scuba", "textures/models/armor/", suffix + ".png").toString();
		}
		return Reference.MODID + ":textures/models/armor/scuba" + suffix + ".png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot equipmentSlot, ModelBiped modelBiped)
	{
		ModelBiped model = null;
		if (this.armorType == EntityEquipmentSlot.CHEST && hasAirTank(itemStack))
		{
			if (isDualTank(itemStack)) model = ModelScubaTankLarge.ARMOR;
			else model = ModelScubaTankSmall.ARMOR;
		}
		else if (this.armorType == EntityEquipmentSlot.FEET && hasFlippers(itemStack))
		{
			model = ModelFlippers.INSTANCE;
		}
		return model;
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
	public boolean showDurabilityBar(ItemStack stack)
	{
		boolean airTank = hasAirTank(stack) && getMaxOxygen(stack) > 0;
		boolean energyCell = false;//hasEnergyCell(stack);
		if (this.armorType == EntityEquipmentSlot.CHEST)
		{
			if (airTank && energyCell) return (ModConfig.scubaDurablityPriority.equals("ENERGY") ? !ModConfig.powerItemDurablityType.equals("HIDDEN") : !ModConfig.oxygenItemDurablityType.equals("HIDDEN"));
			else if (airTank) return !ModConfig.oxygenItemDurablityType.equals("HIDDEN");
			else if (energyCell) return !ModConfig.powerItemDurablityType.equals("HIDDEN");
		}
		return false;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		boolean airTank = hasAirTank(stack) && getMaxOxygen(stack) > 0;
		boolean energyCell = false;//hasEnergyCell(stack);
		
	    double maxAmount = this.getMaxOxygen(stack);
	    double dif = maxAmount-this.getOxygenStored(stack);
	    return dif/maxAmount;
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
	{
		boolean airTank = hasAirTank(stack) && getMaxOxygen(stack) > 0;
		boolean energyCell = false;//hasEnergyCell(stack);
		
		if (this.armorType == EntityEquipmentSlot.CHEST)
		{
			if (airTank && energyCell) return (ModConfig.scubaDurablityPriority.equals("ENERGY") ? (ModConfig.powerItemDurablityType.equals("SOLID") ? ModConfig.powerItemDurablityColor : super.getRGBDurabilityForDisplay(stack)) : (ModConfig.oxygenItemDurablityType.equals("SOLID") ? ModConfig.oxygenItemDurablityColor : super.getRGBDurabilityForDisplay(stack)));
			else if (airTank) return (ModConfig.oxygenItemDurablityType.equals("SOLID") ? ModConfig.oxygenItemDurablityColor : super.getRGBDurabilityForDisplay(stack));
			else if (airTank) return (ModConfig.powerItemDurablityType.equals("SOLID") ? ModConfig.powerItemDurablityColor : super.getRGBDurabilityForDisplay(stack));
		}
		return super.getRGBDurabilityForDisplay(stack);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (!player.capabilities.isCreativeMode && isSetComplete(player) && !player.isPotionActive(MobEffects.WATER_BREATHING) && player.isInsideOfMaterial(Material.WATER))
		{
			if (hasAirTank(stack))
			{
				player.setAir(player.getAir()+1);
				setOxygen(stack, getOxygenStored(stack) - 1);
			}
		}
	}
	
	public static boolean isSetComplete(EntityPlayer player)
	{
		return (checkArmor(player, 5, ModItems.itemScubaMask) && checkArmor(player, 4, ModItems.itemScubaChest) && checkArmor(player, 3, ModItems.itemScubaLegs) && checkArmor(player, 2, ModItems.itemScubaBoots));
	}
	
	public static boolean checkArmor(EntityPlayer player, int slot, Item item)
	{
		return (ItemHelper.getCurrentItemOrArmor(player, slot) != null && ItemHelper.getCurrentItemOrArmor(player, slot).getItem() == item);
	}
	
	public boolean hasAirTank(ItemStack stack)
	{
		ItemStack airTank = ItemUtil.getItemFromStack(stack, "AIRTANK");
		return airTank != null && airTank.getItem() instanceof IAirTankItem;
	}
	
	public boolean hasFlippers(ItemStack stack)
	{
		ItemStack flippers = ItemUtil.getItemFromStack(stack, "FLIPPERS");
		return flippers != null && flippers.getItem() instanceof IFlippers;
	}
	
	@Override
	public boolean isHelmetActive(EntityPlayer player, ItemStack stack)
	{
		return this.armorType == EntityEquipmentSlot.HEAD;
	}
	
	@Override
	public boolean isFlippersActive(EntityPlayer player, ItemStack stack)
	{
		return this.armorType == EntityEquipmentSlot.FEET && isSetComplete(player) && hasFlippers(stack) && player.isInWater();
	}
	
	@Override
	public int getOxygenStored(ItemStack itemStack)
	{
		ItemStack airTank = ItemUtil.getItemFromStack(itemStack, "AIRTANK");
		return (this.hasAirTank(itemStack) ? ((IAirTankItem) airTank.getItem()).getOxygenStored(airTank) : 0);
	}
	
	@Override
	public void setOxygen(ItemStack itemStack, int amount)
	{
		ItemStack airTank = ItemUtil.getItemFromStack(itemStack, "AIRTANK");
		if (this.hasAirTank(itemStack)) ((IAirTankItem) airTank.getItem()).setOxygen(airTank, amount);
		ItemUtil.addItemToStack(itemStack, airTank, "AIRTANK");
	}
	
	@Override
	public int getMaxOxygen(ItemStack itemStack)
	{
		ItemStack airTank = ItemUtil.getItemFromStack(itemStack, "AIRTANK");
		return (this.hasAirTank(itemStack) ? ((IAirTankItem)airTank.getItem()).getMaxOxygen(airTank) : 0);
	}
	
	@Override
	public boolean isDualTank(ItemStack itemStack)
	{
		ItemStack airTank = ItemUtil.getItemFromStack(itemStack, "AIRTANK");
		return (this.hasAirTank(itemStack) ? ((IAirTankItem)airTank.getItem()).isDualTank(airTank) : false);
	}
}