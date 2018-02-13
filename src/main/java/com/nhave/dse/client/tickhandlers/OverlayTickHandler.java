package com.nhave.dse.client.tickhandlers;

import org.lwjgl.opengl.GL11;

import com.nhave.dse.api.items.IAirTankItem;
import com.nhave.dse.entity.EntityMotorboat;
import com.nhave.dse.registry.ModConfig;
import com.nhave.dse.utils.NumberUtils;
import com.nhave.nhc.client.util.RenderUtils.HUDPositions;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class OverlayTickHandler
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static ResourceLocation WIDGET_RESOURCE = new ResourceLocation("dse", "textures/misc/widget.png");
    
    private static void tickEnd()
    {
        if ((mc.currentScreen == null) && !mc.gameSettings.hideGUI && !mc.gameSettings.showDebugInfo)
        {
        	EntityPlayer player = mc.player;
        	
            ItemStack chest = player.inventory.armorItemInSlot(2);
        	boolean isBoatActive = (player.isRiding() && player.getRidingEntity() instanceof EntityMotorboat);
        	boolean isTankActive = (chest != null && !chest.isEmpty() && chest.getItem() instanceof IAirTankItem && ((IAirTankItem) chest.getItem()).getMaxOxygen(chest) != 0);
        	
        	int widgetHeight = 0;
        	int widgetTotal = 0;
        	if (isBoatActive)
        	{
        		widgetHeight += 28;
            	++widgetTotal;
        	}
        	if (isTankActive)
        	{
        		widgetHeight += 28;
            	++widgetTotal;
        	}
        	if (widgetTotal > 1) widgetHeight += (widgetTotal * 2) - 2;
        	
        	double scale = 0.8D;
    		ScaledResolution res = new ScaledResolution(mc);
    		int screenWidth = res.getScaledWidth();
            screenWidth /= scale;
            int screenHeight = res.getScaledHeight();
            screenHeight /= scale;
            
            int x = 0;
            int y = 0;
            int nextY = 30;
            
            HUDPositions position = HUDPositions.values()[HUDPositions.getFromString(ModConfig.hudPosition)];
            
            switch (position)
            {
    	        case TOP_LEFT:
					x = ModConfig.hudOffsetX;
					y = ModConfig.hudOffsetY;
    	            break;
				case TOP_CENTER:
					x = (screenWidth / 2) - 50;
					y = ModConfig.hudOffsetY;
					break;
				case TOP_RIGHT:
					x = screenWidth - 100 - ModConfig.hudOffsetX;
					y = ModConfig.hudOffsetY;
					break;
				case LEFT:
					x = ModConfig.hudOffsetX;
					y = (screenHeight / 2) - (widgetHeight / 2);
					break;
				case RIGHT:
					x = screenWidth - 100 - ModConfig.hudOffsetX;
					y = (screenHeight / 2) - (widgetHeight / 2);
					break;
				case BOTTOM_LEFT:
					x = ModConfig.hudOffsetX;
					//y = screenHeight - 28 - ModConfig.hudOffsetY;
					y = screenHeight - widgetHeight - ModConfig.hudOffsetY;
					//nextY = -30;
					break;
				case BOTTOM_RIGHT:
					x = screenWidth - 100 - ModConfig.hudOffsetX;
					//y = screenHeight - 28 - ModConfig.hudOffsetY;
					y = screenHeight - widgetHeight - ModConfig.hudOffsetY;
					//nextY = -30;
					break;
            }
            
            if (isTankActive)
        	{
            	IAirTankItem tank = (IAirTankItem) chest.getItem();
				String name = chest.getDisplayName();
				
				double time = tank.getOxygenStored(chest)/20D;
				double minutes = Math.floor(time / 60);
				double seconds = Math.floor(time - minutes * 60);
				boolean showMinutes = (int)minutes > 0;
				String info = (tank.getMaxOxygen(chest) <= 0 ? "∞" : (showMinutes ? (int)minutes + "m:" : "") + (int)seconds + "s");
				
        		drawPowerwidget(x, y, (tank.getMaxOxygen(chest) <= 0 ? 1 : tank.getOxygenStored(chest)), (tank.getMaxOxygen(chest) <= 0 ? 1 : tank.getMaxOxygen(chest)), name, info, scale, true);
        		y += nextY;
        	}
            if (isBoatActive)
        	{
        		EntityMotorboat boat = (EntityMotorboat) player.getRidingEntity();
				String name = StringUtils.localize("entity.dse.motorboat.name");
				String info = NumberUtils.getDisplayShort(boat.getPowerStored()) + " " + ModConfig.energyUnit;
        		drawPowerwidget(x, y, boat.getPowerStored(), boat.getMaxPowerStored(), name, info, scale, false);
        		y += nextY;
        	}
        }
    }
    
    public static void drawPowerwidget(int x, int y, int current, int max, String name, String info, double scale, boolean oxygen)
    {
    	GL11.glPushMatrix();
        GL11.glScaled(scale, scale, 1.0D);
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1F, 1F, 1F);
        
		mc.getTextureManager().bindTexture(WIDGET_RESOURCE);
		Gui.drawModalRectWithCustomSizedTexture(x, y + 8, 0, 0, 100, 20, 256, 256);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 102, 0, 100, 12, 256, 256);
		
		int powerStart = (oxygen ? 248 : 240);
		int powerWidth = getPercentage(88, current, max);
		int powerHeight = 8;
		
		if (powerWidth > 0) Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 14, 0, powerStart, powerWidth, powerHeight, 256, 256);
		
		drawStringCenter(StringUtils.limitString(name, 16), mc.fontRenderer, x + 50, y + 4, 0x404040, false);
		drawStringCenter(info, mc.fontRenderer, x + 50, y + 14, 0xeeeeee, true);

		GlStateManager.color(1F, 1F, 1F);
		GlStateManager.disableRescaleNormal();
        GL11.glPopMatrix();
    }
    
	@SubscribeEvent
    public void onRenderTick(RenderTickEvent evt)
    {
    	if (mc.player != null && ModConfig.enableHud)
        {
	    	if (evt.phase == Phase.END)
	        {
	            tickEnd();
	        }
        }
    }
	
	private static int getPercentage(int draw, int current, int max)
	{
		double percentage = ((double) current / (double) max) * 100D;
		double result = Math.floor(((double) draw / 100D) * percentage);
		
		return (int) (result == 0 && current > 0 ? 1 : result);
	}
	
	public static void drawStringCenter(String string, FontRenderer fontRenderer, int x, int y, int color, boolean shadow)
    {
        fontRenderer.drawString(string, x - fontRenderer.getStringWidth(string) / 2, y, color, shadow);
    }
}