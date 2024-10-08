package nl.requios.effortlessbuilding.gui.buildmodifier;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import nl.requios.effortlessbuilding.create.foundation.gui.TickableGuiEventListener;
import nl.requios.effortlessbuilding.create.foundation.gui.UIRenderHelper;
import nl.requios.effortlessbuilding.create.foundation.gui.widget.AbstractSimiWidget;
import nl.requios.effortlessbuilding.create.foundation.utility.Color;
import nl.requios.effortlessbuilding.create.foundation.utility.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Based on Create's ConfigScreenList
public class ModifiersScreenList extends ObjectSelectionList<ModifiersScreenList.Entry> implements TickableGuiEventListener {

    public ModifiersScreenList(Minecraft mc, int width, int height, int y0, int y1, int itemHeight) {
        super(mc, width, height, y0, y1, itemHeight);
        setRenderBackground(false);
        setRenderTopAndBottom(false);
        setRenderSelection(false);
        headerHeight = 3;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Color c = new Color(0x60_000000);
        UIRenderHelper.angledGradient(guiGraphics, 90, x0 + width / 2, y0, width, 5, c, Color.TRANSPARENT_BLACK);
        UIRenderHelper.angledGradient(guiGraphics, -90, x0 + width / 2, y1, width, 5, c, Color.TRANSPARENT_BLACK);
        UIRenderHelper.angledGradient(guiGraphics, 0, x0, y0 + height / 2, height, 5, c, Color.TRANSPARENT_BLACK);
        UIRenderHelper.angledGradient(guiGraphics, 180, x1, y0 + height / 2, height, 5, c, Color.TRANSPARENT_BLACK);

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderList(GuiGraphics guiGraphics, int p_239229_, int p_239230_, float p_239231_) {
        Window window = minecraft.getWindow();
        double d0 = window.getGuiScale();
        RenderSystem.enableScissor((int) (this.x0 * d0), (int) (window.getHeight() - (this.y1 * d0)), (int) (this.width * d0), (int) (this.height * d0));
        super.renderList(guiGraphics, p_239229_, p_239230_, p_239231_);
        RenderSystem.disableScissor();
    }
    
    public void renderWindowForeground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderListForeground(guiGraphics, mouseX, mouseY, partialTicks);
    }
    
    protected void renderListForeground(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int i = this.getRowLeft();
        int j = this.getRowWidth();
        int k = this.itemHeight - 4;
        int l = this.getItemCount();
        
        for(int i1 = 0; i1 < l; ++i1) {
            int j1 = this.getRowTop(i1);
            int k1 = j1 + itemHeight;
            if (k1 >= this.y0 && j1 <= this.y1) {
                renderItemForeground(guiGraphics, pMouseX, pMouseY, pPartialTick, i1, i, j1, j, k);
            }
        }
    }
    
    protected void renderItemForeground(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick, int pIndex, int pLeft, int pTop, int pWidth, int pHeight) {
        Entry e = this.getEntry(pIndex);
        e.renderForeground(guiGraphics, pIndex, pTop, pLeft, pWidth, pHeight, pMouseX, pMouseY, Objects.equals(this.getHovered(), e), pPartialTick);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (children().stream().anyMatch(e -> e.mouseClicked(x, y, button)))
            return true;
        return super.mouseClicked(x, y, button);
    }
    
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (children().stream().anyMatch(e -> e.keyPressed(pKeyCode, pScanCode, pModifiers)))
            return true;
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
    
    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (children().stream().anyMatch(e -> e.charTyped(pCodePoint, pModifiers)))
            return true;
        return super.charTyped(pCodePoint, pModifiers);
    }
    
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (children().stream().anyMatch(e -> e.mouseScrolled(pMouseX, pMouseY, pDelta)))
            return true;
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }
    
    @Override
    public int getRowWidth() {
        return width - 16;
    }

    @Override
    protected int getScrollbarPosition() {
        return x0 + this.width - 6;
    }

    @Override
    public void tick() {
        children().forEach(Entry::tick);
    }

    public static abstract class Entry extends ObjectSelectionList.Entry<Entry> implements TickableGuiEventListener {
        protected final ModifiersScreen screen;
        protected List<GuiEventListener> listeners;

        protected Entry(ModifiersScreen screen) {
            this.screen = screen;
            listeners = new ArrayList<>();
        }

        @Override
        public boolean mouseClicked(double x, double y, int button) {
            return getGuiListeners().stream().anyMatch(l -> l.mouseClicked(x, y, button));
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            return getGuiListeners().stream().anyMatch(l -> l.keyPressed(keyCode, scanCode, modifiers));
        }

        @Override
        public boolean charTyped(char ch, int modifiers) {
            return getGuiListeners().stream().anyMatch(l -> l.charTyped(ch, modifiers));
        }
    
        @Override
        public boolean mouseScrolled(double x, double y, double delta) {
            return getGuiListeners().stream().anyMatch(l -> l.mouseScrolled(x, y, delta));
        }
    
        @Override
        public void render(GuiGraphics guiGraphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean p_230432_9_, float partialTicks) {
    
//            UIRenderHelper.streak(guiGraphics, 0, x - 10, y + height / 2, height - 6, width, 0xdd_000000);
//            UIRenderHelper.streak(guiGraphics, 180, x + (int) (width * 1.35f) + 10, y + height / 2, height - 6, width / 8 * 7, 0xdd_000000);
    
        }
        
        public void renderForeground(GuiGraphics guiGraphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean p_230432_9_, float partialTicks) {
    
            for (var listener : listeners) {
                if (listener instanceof AbstractSimiWidget simiWidget && simiWidget.isHoveredOrFocused()
                    && simiWidget.visible) {
                    List<Component> tooltip = simiWidget.getToolTip();
                    if (tooltip.isEmpty())
                        continue;
                    int ttx = simiWidget.lockedTooltipX == -1 ? mouseX : simiWidget.lockedTooltipX + simiWidget.getX();
                    int tty = simiWidget.lockedTooltipY == -1 ? mouseY : simiWidget.lockedTooltipY + simiWidget.getY();
                    guiGraphics.renderComponentTooltip(getFont(), tooltip, ttx, tty);
                }
            }
        }

        @Override
        public void tick() {}

        public List<GuiEventListener> getGuiListeners() {
            return listeners;
        }

        @Override
        public Component getNarration() {
            return Components.immutableEmpty();
        }
        
        public Font getFont() {
            return Minecraft.getInstance().font;
        }
    }
}
