package com.nhave.dse.network;

import com.nhave.dse.entity.EntityMotorboat;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ConsumeBoatPowerPacket implements IMessage
{
	public ConsumeBoatPowerPacket() {}
	
	public int amount;
	
	public ConsumeBoatPowerPacket(int amount)
	{
		this.amount = amount;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(amount);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		amount = buf.readInt();
	}
	
	public static class Handler implements IMessageHandler<ConsumeBoatPowerPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ConsumeBoatPowerPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			DimensionManager.getWorld(player.world.provider.getDimension()).addScheduledTask(new DoSync(player, message.amount));
			
			return null;
		}
	}
	
	private static class DoSync implements Runnable
	{
		private EntityPlayer p;
		private int amount;

		public DoSync(EntityPlayer p, int amount)
		{
			this.p = p;
			this.amount = amount;
		}
		
		@Override
		public void run()
		{
			if (p != null)
			{
				Entity entity = p.getRidingEntity();
				
		        if (entity instanceof EntityMotorboat)
		        {
		        	EntityMotorboat boat = (EntityMotorboat) entity;
		        	
					boat.setPowerStored(boat.getPowerStored() - amount);
		        }
			}
		}
	}
}