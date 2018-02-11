package com.nhave.dse.compat;

import javax.annotation.Nullable;

import com.nhave.dse.DeepSeaExpansion;
import com.nhave.dse.Reference;
import com.nhave.dse.api.items.IAirTankItem;
import com.nhave.dse.entity.EntityMotorboat;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.tileentity.TileEntityCharger;
import com.nhave.dse.tileentity.TileEntityCompressor;
import com.nhave.dse.utils.NumberUtils;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TOPCompatibility
{
    private static boolean registered;
    
    public static void register()
    {
        if (registered) return;
        registered = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.nhave.dse.compat.TOPCompatibility$GetTheOneProbe");
    }
    
    public static class GetTheOneProbe implements com.google.common.base.Function<ITheOneProbe, Void>
    {
        public static ITheOneProbe probe;
        
        @Nullable
        @Override
        public Void apply(ITheOneProbe theOneProbe)
        {
            probe = theOneProbe;
            probe.registerEntityProvider(new IProbeInfoEntityProvider()
			{
				@Override
				public String getID()
				{
					return Reference.MODID + ":entity";
				}
				
				@Override
				public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data)
				{
					if (entity != null && entity instanceof EntityMotorboat)
					{
						EntityMotorboat boat = (EntityMotorboat) entity;
						
						probeInfo.progress(boat.getPowerStored(), boat.getMaxPowerStored(),
		                    probeInfo.defaultProgressStyle()
		                        .suffix(ModConfig.energyUnit)
		                        .filledColor(Config.rfbarFilledColor)
		                        .alternateFilledColor(Config.rfbarAlternateFilledColor)
		                        .borderColor(Config.rfbarBorderColor)
		                        .numberFormat(Config.rfFormat));
					}
				}
			});
            probe.registerProvider(new IProbeInfoProvider()
			{
				@Override
				public String getID()
				{
					return Reference.MODID + ":block";
				}
				
				@Override
				public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
				{
					TileEntity tile = world.getTileEntity(data.getPos());
					if (tile instanceof TileEntityCharger)
					{
						IProbeInfo horizontal = probeInfo.horizontal();
						TileEntityCharger machine = (TileEntityCharger) tile;
						
						if (machine.getItemStack() != null && !machine.getItemStack().isEmpty())
						{
							ItemStack stack = ((TileEntityCharger) tile).getItemStack().copy();
							
							String power = "";
							
							if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
							{
					            IEnergyStorage capability = stack.getCapability(CapabilityEnergy.ENERGY, null);
					            power += NumberUtils.getDisplayShort(capability.getEnergyStored()) + " / " + NumberUtils.getDisplayShort(capability.getMaxEnergyStored());
							}
							else if (DeepSeaExpansion.redstoneflux && RedstoneFluxCompatibility.isEnergyItem(stack.getItem()))
							{
					            power += NumberUtils.getDisplayShort(RedstoneFluxCompatibility.getEnergyStored(stack)) + " / " + NumberUtils.getDisplayShort(RedstoneFluxCompatibility.getMaxEnergyStored(stack));
							}
							
							horizontal.item(stack)
							.vertical(probeInfo.defaultLayoutStyle().spacing(0))
							.text(stack.getDisplayName())
							.text(power + " " + ModConfig.energyUnit);
						}
					}
					else if (tile instanceof TileEntityCompressor)
					{
						IProbeInfo horizontal = probeInfo.horizontal();
						TileEntityCompressor machine = (TileEntityCompressor) tile;
						
						if (machine.getItemStack() != null && !machine.getItemStack().isEmpty())
						{
							ItemStack stack = ((TileEntityCompressor) tile).getItemStack().copy();
							
							String air = "";
							
							if (stack.getItem() instanceof IAirTankItem)
							{
								IAirTankItem item = (IAirTankItem) stack.getItem();
								if (item.getMaxOxygen(stack) <= 0)
								{
									air += 100;
								}
								else
								{
									double percentage = ((double) item.getOxygenStored(stack) / (double) item.getMaxOxygen(stack)) * 100D;
									air += (int)percentage;
								}
							}
							
							horizontal.item(stack)
							.vertical(probeInfo.defaultLayoutStyle().spacing(0))
							.text(stack.getDisplayName())
							.text(air + "%");
						}
					}
				}
			});
            return null;
        }
    }
}