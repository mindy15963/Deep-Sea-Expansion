package com.nhave.dse.client.widget;

import com.nhave.dse.api.items.IInventoryItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.RenderTooltipEvent.PostText;

public class WidgetInventory extends WidgetBase
{
	public static ResourceLocation WIDGET_RESOURCE = new ResourceLocation("dse", "textures/misc/widget.png");
	
	public int getSizeX(RenderTooltipEvent.PostText event)
	{
		return 100;
	}
	
	public int getSizeY(RenderTooltipEvent.PostText event)
	{
		return 28;
	}
	
	public void drawWidget(RenderTooltipEvent.PostText event, int x, int y)
	{
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(WIDGET_RESOURCE);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 54, getSizeX(event), getSizeY(event), 256, 256);
	}

	@Override
	public boolean shouldDraw(PostText event)
	{
		return event.getStack().getItem() instanceof IInventoryItem;
	}
}