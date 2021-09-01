package com.halotroop.fabric.impl.translations.gui.widget;

import com.halotroop.fabric.api.registry.Identifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

/**
 * TODO this belongs in a different module! Which one though?
 */
public class IconButtonWidget extends ButtonWidget {
	public int disabledColor = 0xffa0a0a0;
	public int enabledColor = 0xe0e0e0;
	public int hoveredColor = 0xffffa0;

	public int iconId = -1;
	private Identifier icon;

	public boolean enableBackground = true;

	public IconButtonWidget(int id, int x, int y, int width, int height,
	                        int disabledColor, int enabledColor, int hoverColor,
	                        Identifier icon, String text) {
		super(id, x, y, width, height, text);
		this.disabledColor = disabledColor;
		this.enabledColor = enabledColor;
		this.hoveredColor = hoverColor;
		this.icon = icon;
	}

	public IconButtonWidget(int id, int x, int y, int width, int height, Identifier icon, String text) {
		this(id, x, y, width, height, 0xffa0a0a0, 0xe0e0e0, 0xffffa0, icon, text);
	}

	@Override
	public void render(Minecraft client, int mouseX, int mouseY) {
		if (!this.visible) return;

		final TextRenderer textRenderer = client.textRenderer;
		final TextureManager textureManager = client.textureManager;
		final boolean hovered = isMouseOver(client, mouseX, mouseY);
		final int hoverState = getHoverState(hovered);

		if (this.enableBackground) {
			this.drawCenteredTexture(textureManager.getTextureId("/gui/gui.png"), 0, 46 + (hoverState * 20));
		}

		postRender(client, mouseX, mouseY);

		if (iconId > -1) {
			this.drawCenteredTexture(textureManager.getTextureId("/textures/gui/accessibility.png"), 0, 0, 20, 20);
		} else {
			int textX = this.x + (this.width / 2);
			int textY = this.y + (this.height / 3);

			switch (hoverState) {
				case 0:
					drawTextWithShadowCentred(textRenderer, this.text, textX, textY, this.disabledColor);
					break;
				default:
				case 1:
					drawTextWithShadowCentred(textRenderer, this.text, textX, textY, this.enabledColor);
					break;
				case 2:
					drawTextWithShadowCentred(textRenderer, this.text, textX, textY, this.hoveredColor);
					break;
			}
		}
	}

	protected void drawCenteredTexture(int textureId, int u, int v) {
		this.drawCenteredTexture(textureId, u, v, this.width / 2, this.height);
	}

	protected void drawCenteredTexture(int textureId, int u, int v, int width, int height) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		this.blit(this.x, this.y, u, v, width, height);
	}
}
