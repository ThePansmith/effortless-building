package nl.requios.effortlessbuilding.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class GuiCollapsibleScrollEntry implements GuiScrollPane.IScrollEntry {

	public GuiScrollPane scrollPane;
	protected Font font;
	protected Minecraft mc;

	protected boolean isCollapsed = true;
	protected int left, right, top, bottom;

	public GuiCollapsibleScrollEntry(GuiScrollPane scrollPane) {
		this.scrollPane = scrollPane;
		this.font = scrollPane.font;
		this.mc = Minecraft.getInstance();
	}

	@Override
	public void init(List<Renderable> renderables) {
		left = scrollPane.getWidth() / 2 - 140;
		right = scrollPane.getWidth() / 2 + 140;
		top = scrollPane.getHeight() / 2 - 100;
		bottom = scrollPane.getHeight() / 2 + 100;
	}

	@Override
	public void updateScreen() {
	}

	@Override
	public void drawTooltip(GuiGraphics guiGraphics, Screen guiScreen, int mouseX, int mouseY) {
	}

	@Override
	public void updatePosition(int slotIndex, int x, int y, float partialTicks) {

	}

	@Override
	public boolean charTyped(char eventChar, int eventKey) {
		return false;
	}

	@Override
	public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
		return false;
	}

	@Override
	public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

	}

	@Override
	public void onGuiClosed() {
	}

	@Override
	public int getHeight() {
		return isCollapsed ? getCollapsedHeight() : getExpandedHeight();
	}

	public void setCollapsed(boolean collapsed) {
		this.isCollapsed = collapsed;
	}

	protected String getName() {
		return "Collapsible scroll entry";
	}

	protected int getCollapsedHeight() {
		return 24;
	}

	protected int getExpandedHeight() {
		return 100;
	}
}
