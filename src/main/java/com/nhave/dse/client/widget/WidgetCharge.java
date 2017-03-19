package com.nhave.dse.client.widget;

import com.nhave.dse.api.items.IAirTank;
import com.nhave.dse.api.items.IPowerItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.RenderTooltipEvent.PostText;

public class WidgetCharge extends WidgetBase
{
	public static ResourceLocation WIDGET_RESOURCE = new ResourceLocation("dse", "textures/misc/widget.png");
	
	public int getSizeX(RenderTooltipEvent.PostText event)
	{
		return 100;
	}
	
	public int getSizeY(RenderTooltipEvent.PostText event)
	{
		ItemStack stack = event.getStack();
		boolean power = event.getStack().getItem() instanceof IPowerItem && ((IPowerItem) stack.getItem()).getMaxPower(stack) > 0;
		boolean air = event.getStack().getItem() instanceof IAirTank && ((IAirTank) stack.getItem()).getMaxO2(stack) > 0;
		return (air && power) ? 30 : 20;
	}
	
	public void drawWidget(RenderTooltipEvent.PostText event, int x, int y)
	{
		ItemStack stack = event.getStack();
		boolean power = event.getStack().getItem() instanceof IPowerItem && ((IPowerItem) stack.getItem()).getMaxPower(stack) > 0;
		boolean air = event.getStack().getItem() instanceof IAirTank && ((IAirTank) stack.getItem()).getMaxO2(stack) > 0;
		
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(WIDGET_RESOURCE);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0, (air && power) ? 22 : 0, getSizeX(event), getSizeY(event), 256, 256);
		
		int overlayY = y+6;
		if (air)
		{
			IAirTank airTank = (IAirTank) stack.getItem();
			
			int airStart = 248;
			int airWidth = getPercentage(88, airTank.getO2(stack), airTank.getMaxO2(stack));
			int airHeight = 8;
			
			mc.getTextureManager().bindTexture(WIDGET_RESOURCE);
			if (airWidth > 0) Gui.drawModalRectWithCustomSizedTexture(x+6, overlayY, 0, airStart, airWidth, airHeight, 256, 256);
			
			String info = airTank.getO2Info(stack);
			float center = x+(getSizeX(event)/2);
			mc.fontRendererObj.drawString(info, center-(info.length()*2.85F), (float) overlayY, 0xeeeeee, true);
			overlayY += 10;
		}
		if (power)
		{
			IPowerItem powerCell = (IPowerItem) stack.getItem();
			
			int powerStart = 240;
			int powerWidth = getPercentage(88, powerCell.getPower(stack), powerCell.getMaxPower(stack));
			int powerHeight = 8;
			
			mc.getTextureManager().bindTexture(WIDGET_RESOURCE);
			if (powerWidth > 0) Gui.drawModalRectWithCustomSizedTexture(x+6, overlayY, 0, powerStart, powerWidth, powerHeight, 256, 256);
			
			String info = powerCell.getPowerInfo(stack);
			float center = x+(getSizeX(event)/2);
			mc.fontRendererObj.drawString(info, center-(info.length()*2.85F), (float) overlayY, 0xeeeeee, true);
		}
	}

	@Override
	public boolean shouldDraw(PostText event)
	{
		ItemStack stack = event.getStack();
		boolean power = event.getStack().getItem() instanceof IPowerItem && ((IPowerItem) stack.getItem()).getMaxPower(stack) > 0;
		boolean air = event.getStack().getItem() instanceof IAirTank && ((IAirTank) stack.getItem()).getMaxO2(stack) > 0;
		return air || power;
	}
	
	private int getPercentage(int draw, int current, int max)
	{
		double percentage = ((double) current / (double) max) * 100;
		double result = ((double) draw / 100) * percentage;
		return (int) result;
	}
}