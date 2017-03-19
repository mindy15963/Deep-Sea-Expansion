package com.nhave.dse.client.renders;

import org.lwjgl.opengl.GL11;

import com.nhave.dse.tiles.TileEntityOyster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderTileOyster extends TileEntitySpecialRenderer
{
	private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float arg4, int paramInt)
	{
		TileEntityOyster tileEntity = (TileEntityOyster) tile;
		if (tileEntity.getItemStack() != null)
		{
			int meta = tileEntity.getBlockMetadata();
			ItemStack stack = tileEntity.getItemStack();
			EntityItem entItem = new EntityItem(tileEntity.getWorld(), x, y, z, stack);
			GL11.glPushMatrix();
			//GlStateManager.disableLighting();
				entItem.hoverStart = 0.0F;
				//RenderItem.renderInFrame = true;
				
				if (stack.getItem() instanceof ItemBlock)
				{
					GL11.glTranslatef((float)x + 0.5F, (float)y + 0.17F, (float)z + 0.5F);
					GL11.glScalef(0.3F, 0.3F, 0.3F);

					if (meta == 5) GL11.glRotatef(270, 0, 1, 0);
					else if (meta == 4) GL11.glRotatef(90, 0, 1, 0);
					else if (meta == 3) GL11.glRotatef(180, 0, 1, 0);
				}
				else
				{
					GL11.glTranslatef((float)x + 0.5F, (float)y + 0.1F, (float)z + 0.5F);
					GL11.glScalef(0.3F, 0.3F, 0.3F);

					if (meta == 2)
					{
						//GL11.glTranslatef(0.0F, 0F, 0F);
						GL11.glRotatef(90, 0, 1, 0);
					}
					else if (meta == 3)
					{
						//GL11.glTranslatef(-0.2F, 0F, 0F);
						GL11.glRotatef(270, 0, 1, 0);
					}
					else if (meta == 5)
					{
						//GL11.glTranslatef(0F, 0F, 0.2F);
					}
					else if (meta == 4)
					{
						//GL11.glTranslatef(0F, 0F, -0.2F);
						GL11.glRotatef(180, 0, 1, 0);
					}
					
					GL11.glRotatef(-90, 1, 0, 0);
				}
				//RenderManager.instance.renderEntityWithPosYaw(entItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				this.itemRenderer.renderItem(entItem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
				//RenderItem.renderInFrame = false;
			//GlStateManager.enableLighting();
			GL11.glPopMatrix();
		}
	}
}