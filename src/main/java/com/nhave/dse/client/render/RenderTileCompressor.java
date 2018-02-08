package com.nhave.dse.client.render;

import org.lwjgl.opengl.GL11;

import com.nhave.dse.tileentity.TileEntityCompressor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderTileCompressor extends TileEntitySpecialRenderer
{
	private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
	
	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		renderTileEntityAt(te, x, y, z);
	}
	
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z)
	{
		TileEntityCompressor tileEntity = (TileEntityCompressor) tile;
		if (tileEntity.getItemStack() != null)
		{
			int meta = tileEntity.getBlockMetadata();
			ItemStack stack = tileEntity.getItemStack();
			
			GL11.glPushMatrix();
			
			//GL11.glTranslatef((float)x + 0.5F, (float)y + 0.44F, (float)z + 0.185F);
			
			if (meta == 4)
			{
				GL11.glTranslatef((float)x + 0.19F, (float)y + 0.44F, (float)z + 0.5F);
				GL11.glRotatef(90, 0, 1, 0);
			}
			else if (meta == 5)
			{
				GL11.glTranslatef((float)x + 0.81F, (float)y + 0.44F, (float)z + 0.5F);
				GL11.glRotatef(270, 0, 1, 0);
			}
			else if (meta == 3)
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 0.44F, (float)z + 0.81F);
				GL11.glRotatef(180, 0, 1, 0);
			}
			else if (meta == 2)
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 0.44F, (float)z + 0.185F);
			}
			
			GL11.glScalef(0.58F, 0.58F, 0.58F);
			//GL11.glRotatef(50F, 0F, 1F, 0F);
			
			this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
			GL11.glPopMatrix();
		}
	}
}