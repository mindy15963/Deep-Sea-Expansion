package com.nhave.dse.items;

import java.util.List;

import com.nhave.dse.api.items.IItemUpgrade;
import com.nhave.dse.api.items.IItemUpgradeAdvanced;
import com.nhave.dse.api.items.IShaderItem;
import com.nhave.dse.entity.EntityMotorboat;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.registry.ModItems;
import com.nhave.dse.shaders.Shader;
import com.nhave.dse.utils.NumberUtils;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemMotorboat extends ItemEnergyBase implements IShaderItem
{
    public int boatPowerUsage;
    public int boatBoostModifier;
	
	public ItemMotorboat(String name)
	{
		super(name, ModConfig.boatPowerCapacity, ModConfig.boatPowerTransfer);
		this.setQualityColor(StringUtils.PURPLE);
		this.setMaxStackSize(1);
		this.setNoExtract();
        
        this.boatPowerUsage = ModConfig.boatPowerUsage;
        this.boatBoostModifier = ModConfig.boatBoostModifier;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(StringUtils.format("[Work In Progress]", StringUtils.RED, StringUtils.BOLD));
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.dse.item." + this.getItemName(stack)), ";", StringUtils.GRAY);
			
			String shaderName = StringUtils.localize("tooltip.dse.shader.none");
			String color = StringUtils.YELLOW;
			if (hasShader(stack))
			{
				shaderName = ItemUtil.getItemFromStack(stack, "SHADER").getDisplayName();
				color = getShader(stack).getNamePrefix();
			}
			tooltip.add(StringUtils.localize("tooltip.dse.shader.current") + ": " + StringUtils.format(shaderName, color, StringUtils.ITALIC));
			
			tooltip.add(StringUtils.localize("tooltip.dse.charge") + ": " + NumberUtils.getDisplayShort(getEnergyStored(stack)) + " / " + NumberUtils.getDisplayShort(getMaxEnergyStored(stack)) + " " + ModConfig.energyUnit);
			tooltip.add(StringUtils.format(this.boatPowerUsage + " " + ModConfig.energyUnit + " " + StringUtils.localize("tooltip.dse.charge.tick"), StringUtils.ORANGE));
			if (ItemUtil.getItemFromStack(stack, "BOOSTER") != null) tooltip.add(StringUtils.format(this.boatPowerUsage * this.boatBoostModifier + " " + ModConfig.energyUnit + "/t " + StringUtils.localize("tooltip.dse.charge.boatboost"), StringUtils.ORANGE));
		}
		if (StringUtils.isControlKeyDown())
		{
			tooltip.add(StringUtils.localize("tooltip.dse.mod.all") + ":");
			
			/*for (int i = 0; i < ModItems.MOTORBOAT_UPGRADES.size(); ++i)
			{
				ItemStack mod = ModItems.MOTORBOAT_UPGRADES.get(i);
				String color = StringUtils.RED;
				
				for (int j = 0; j < ModItems.MOTORBOAT_UPGRADES_NBT.size(); ++j)
				{
					ItemStack installed = ItemUtil.getItemFromStack(stack, ModItems.MOTORBOAT_UPGRADES_NBT.get(j));
					
					if (installed != null && installed.getItem() == mod.getItem() && installed.getItemDamage() == mod.getItemDamage())
					{
						color = StringUtils.BRIGHT_GREEN;
					}
				}
				tooltip.add("  " + StringUtils.format(mod.getDisplayName(), color, StringUtils.ITALIC));
			}*/
			for (int i = 0; i < ModItems.MOTORBOAT_UPGRADES.size(); ++i)
			{
				ItemStack mod = ModItems.MOTORBOAT_UPGRADES.get(i);
				String color = StringUtils.RED;
				
				for (int j = 0; j < ModItems.MOTORBOAT_UPGRADES_NBT.size(); ++j)
				{
					ItemStack installed = ItemUtil.getItemFromStack(stack, ModItems.MOTORBOAT_UPGRADES_NBT.get(j));
					
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
			
			if (ModItems.MOTORBOAT_UPGRADES.isEmpty()) tooltip.add("  " + StringUtils.format(StringUtils.localize("tooltip.dse.mod.none"), StringUtils.RED, StringUtils.ITALIC));
		}
		if (!StringUtils.isControlKeyDown() && !StringUtils.isShiftKeyDown())
		{
			tooltip.add(StringUtils.shiftForInfo);
			tooltip.add(StringUtils.localize("§7" + StringUtils.localize("tooltip.nhc.details.ctrl1") + " §e§o" + StringUtils.localize("tooltip.nhc.details.ctrl2") + " §r§7" + StringUtils.localize("tooltip.dse.mod.ctrlinfo") + "§r"));
		}
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		float f = 1.0F;
		float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * 1.0F;
		float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * 1.0F;
		double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * 1.0D;
		double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * 1.0D + (double)playerIn.getEyeHeight();
		double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * 1.0D;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		Vec3d vec3d1 = vec3d.addVector((double)f7 * 5.0D, (double)f6 * 5.0D, (double)f8 * 5.0D);
		RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec3d, vec3d1, true);

		if (raytraceresult == null)
		{
			return new ActionResult(EnumActionResult.PASS, itemstack);
		}
		else
		{
			Vec3d vec3d2 = playerIn.getLook(1.0F);
			boolean flag = false;
			List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().expand(vec3d2.x * 5.0D, vec3d2.y * 5.0D, vec3d2.z * 5.0D).grow(1.0D));

			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity)list.get(i);
				
				if (entity.canBeCollidedWith())
				{
					AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow((double)entity.getCollisionBorderSize());

					if (axisalignedbb.contains(vec3d))
					{
						flag = true;
					}
				}
			}
			
			if (flag)
			{
				return new ActionResult(EnumActionResult.PASS, itemstack);
			}
			else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
			{
				return new ActionResult(EnumActionResult.PASS, itemstack);
			}
			else
			{
				Block block = worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock();
				boolean flag1 = block == Blocks.WATER || block == Blocks.FLOWING_WATER;
				EntityMotorboat entityboat = new EntityMotorboat(worldIn, raytraceresult.hitVec.x, flag1 ? raytraceresult.hitVec.y - 0.12D : raytraceresult.hitVec.y, raytraceresult.hitVec.z);
				
				entityboat.rotationYaw = playerIn.rotationYaw;
				
				if (hasShader(itemstack)) entityboat.setShader(ItemUtil.getItemFromStack(itemstack, "SHADER").copy());
				if (ItemUtil.getItemFromStack(itemstack, "PADDLE") != null) entityboat.setHasPaddles();
				if (ItemUtil.getItemFromStack(itemstack, "STORAGE") != null) entityboat.setHasStorage();
				if (ItemUtil.getItemFromStack(itemstack, "BOOSTER") != null) entityboat.setHasBooster();
				//if (ItemUtil.getItemFromStack(itemstack, "MAGNET") != null) entityboat.setHasMagnet();
				entityboat.setPowerStored(this.getEnergyStored(itemstack));
				
				if (!worldIn.getCollisionBoxes(entityboat, entityboat.getEntityBoundingBox().grow(-0.1D)).isEmpty())
				{
					return new ActionResult(EnumActionResult.FAIL, itemstack);
				}
				else
				{
					if (!worldIn.isRemote)
					{
						worldIn.spawnEntity(entityboat);
					}
					
					if (!playerIn.capabilities.isCreativeMode)
					{
						itemstack.shrink(1);
					}
					
					playerIn.addStat(StatList.getObjectUseStats(this));
					return new ActionResult(EnumActionResult.SUCCESS, itemstack);
				}
			}
		}
	}
}
