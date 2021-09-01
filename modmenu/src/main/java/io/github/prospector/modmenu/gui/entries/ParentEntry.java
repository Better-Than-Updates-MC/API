package io.github.prospector.modmenu.gui.entries;

import com.terraformersmc.modmenu.ModMenu;
import io.github.prospector.modmenu.gui.ModListEntry;
import io.github.prospector.modmenu.gui.ModListWidget;
import io.github.prospector.modmenu.util.ModListSearch;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParentEntry extends ModListEntry {
	private static final String PARENT_MOD_TEXTURE = "/assets/" + ModMenu.MOD_ID + "/textures/gui/parent_mod.png";
	protected List<ModContainer> children;
	protected ModListWidget list;
	protected boolean hoveringIcon = false;

	public ParentEntry(Minecraft mc, ModContainer parent, List<ModContainer> children, ModListWidget list) {
		super(mc, parent, list);
		this.children = children;
		this.list = list;
	}

	@Override
	public void render(int index, int y, int x, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
		super.render(index, y, x, rowWidth, rowHeight, mouseX, mouseY, isSelected, delta);
		TextRenderer font = client.textRenderer;
		int childrenBadgeHeight = 9;
		int childrenBadgeWidth = 9;
		int children = ModListSearch.search(list.getParent(), list.getParent().getSearchInput(), getChildren()).size();
		int childrenWidth = font.getTextWidth(Integer.toString(children)) - 1;
		if (childrenBadgeWidth < childrenWidth + 4) {
			childrenBadgeWidth = childrenWidth + 4;
		}
		int childrenBadgeX = x + 32 - childrenBadgeWidth;
		int childrenBadgeY = y + 32 - childrenBadgeHeight;
		int childrenOutlineColor = 0x8810d098;
		int childrenFillColor = 0x88046146;
		fill(childrenBadgeX + 1, childrenBadgeY, childrenBadgeX + childrenBadgeWidth - 1, childrenBadgeY + 1, childrenOutlineColor);
		fill(childrenBadgeX, childrenBadgeY + 1, childrenBadgeX + 1, childrenBadgeY + childrenBadgeHeight - 1, childrenOutlineColor);
		fill(childrenBadgeX + childrenBadgeWidth - 1, childrenBadgeY + 1, childrenBadgeX + childrenBadgeWidth, childrenBadgeY + childrenBadgeHeight - 1, childrenOutlineColor);
		fill(childrenBadgeX + 1, childrenBadgeY + 1, childrenBadgeX + childrenBadgeWidth - 1, childrenBadgeY + childrenBadgeHeight - 1, childrenFillColor);
		fill(childrenBadgeX + 1, childrenBadgeY + childrenBadgeHeight - 1, childrenBadgeX + childrenBadgeWidth - 1, childrenBadgeY + childrenBadgeHeight, childrenOutlineColor);
		font.drawText(Integer.toString(children), childrenBadgeX + childrenBadgeWidth / 2 - childrenWidth / 2, childrenBadgeY + 1, 0xCACACA);
		this.hoveringIcon = mouseX >= x - 1 && mouseX <= x - 1 + 32 && mouseY >= y - 1 && mouseY <= y - 1 + 32;
		if (isMouseOver(mouseX, mouseY)) {
			fill(x, y, x + 32, y + 32, 0xA0909090);
			this.client.textureManager.bindTexture(this.client.textureManager.getTextureId(PARENT_MOD_TEXTURE));
			int xOffset = list.getParent().showModChildren.contains(getMetadata().getId()) ? 32 : 0;
			int yOffset = hoveringIcon ? 32 : 0;
			GL11.glColor4f(1f, 1f, 1f, 1f);
			Tessellator tess = Tessellator.INSTANCE;
			tess.start();
			tess.vertex(x, y, 0, xOffset / 256f, yOffset / 256f);
			tess.vertex(x, y + 32, 0, xOffset / 256f, (yOffset + 32) / 256f);
			tess.vertex(x + 32, y + 32, 0, (xOffset + 32) / 256f, (yOffset + 32) / 256f);
			tess.vertex(x + 32, y, 0, (xOffset + 32) / 256f, yOffset / 256f);
			tess.draw();
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int i) {
		if (hoveringIcon) {
			String id = getMetadata().getId();
			if (list.getParent().showModChildren.contains(id)) {
				list.getParent().showModChildren.remove(id);
			} else {
				list.getParent().showModChildren.add(id);
			}
			list.filter(list.getParent().getSearchInput(), false);
		}
		return super.mouseClicked(mouseX, mouseY, i);
	}

	@Override
	public boolean keyPressed(int int_1, int int_2, int int_3) {
		if (int_1 == Keyboard.KEY_RETURN) {
			String id = getMetadata().getId();
			if (list.getParent().showModChildren.contains(id)) {
				list.getParent().showModChildren.remove(id);
			} else {
				list.getParent().showModChildren.add(id);
			}
			list.filter(list.getParent().getSearchInput(), false);
			return true;
		}
		return super.keyPressed(int_1, int_2, int_3);
	}

	public void setChildren(List<ModContainer> children) {
		this.children = children;
	}

	public void addChildren(List<ModContainer> children) {
		this.children.addAll(children);
	}

	public void addChildren(ModContainer... children) {
		this.children.addAll(Arrays.asList(children));
	}

	public List<ModContainer> getChildren() {
		return children;
	}

	public boolean isMouseOver(double double_1, double double_2) {
		return Objects.equals(this.list.getEntryAtPos(double_1, double_2), this);
	}
}
