package com.nhave.dse.client.renders;

import org.lwjgl.opengl.GL11;

import com.nhave.dse.tiles.TileEntityCompressor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderTileCompressor extends TileEntitySpecialRenderer
{
	private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float arg4, int paramInt)
	{
		TileEntityCompressor tileEntity = (TileEntityCompressor) tile;
		if (tileEntity.getItemStack() != null)
		{
			int meta = tileEntity.getBlockMetadata();
			ItemStack stack = tileEntity.getItemStack();
			//EntityItem entItem = new EntityItem(tileEntity.getWorld(), x, y, z, stack);
			
			GL11.glPushMatrix();
			//entItem.hoverStart = 0.0F;
			
			if (stack.getItem() instanceof ItemBlock)
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 1.12F, (float)z + 0.5F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);

				if (meta == 5) GL11.glRotatef(270, 0, 1, 0);
				else if (meta == 4) GL11.glRotatef(90, 0, 1, 0);
				else if (meta == 3) GL11.glRotatef(180, 0, 1, 0);
			}
			else
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 1.02F, (float)z + 0.5F);
				GL11.glScalef(0.8F, 0.8F, 0.8F);

				if (meta == 4)
				{
					GL11.glRotatef(90, 0, 1, 0);
				}
				else if (meta == 5)
				{
					GL11.glRotatef(270, 0, 1, 0);
				}
				else if (meta == 3)
				{
					GL11.glRotatef(180, 0, 1, 0);
				}
				
				GL11.glRotatef(90, 1, 0, 0);
			}
			
			this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
			GL11.glPopMatrix();
		}
	}
}
