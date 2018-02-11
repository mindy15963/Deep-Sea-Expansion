package com.nhave.dse.network;

import com.nhave.dse.Reference;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class DSEPacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	public static void preInit()
	{
		INSTANCE.registerMessage(ConsumeBoatPowerPacket.Handler.class, ConsumeBoatPowerPacket.class, 3, Side.SERVER);
	}
}