package io.github.hw9636.neverenderore.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.hw9636.neverenderore.NeverEnderOreMod;
import io.github.hw9636.neverenderore.common.config.NEOConfig;
import io.github.hw9636.neverenderore.common.oreextractor.OreExtractorContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OreExtractorScreen extends AbstractContainerScreen<OreExtractorContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(NeverEnderOreMod.MODID, "textures/gui/ore_extractor.png");

    public OreExtractorScreen(OreExtractorContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);

        int i = this.getGuiLeft();
        int j = this.getGuiTop();
        if (isIn(pMouseX, pMouseY, i + 165, j + 9, i + 168, j + 80)) {
            int energy = (menu.data.get(0) << 16) | menu.data.get(1);
            renderComponentTooltip(pPoseStack, List.of(Component.literal(energy + "/" + NEOConfig.COMMON.maxEnergyStored.get() + " FE")),
                    pMouseX, pMouseY);
        }
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pPoseStack);

        int i = this.getGuiLeft();
        int j = this.getGuiTop();

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        int progress = mapNum(menu.data.get(2), menu.data.get(4), 25);
        this.blit(pPoseStack, i + 76, j + 46, 193, 9, progress, 3);

        int energy = (menu.data.get(0) << 16) | menu.data.get(1);
        int feProgress = mapNum(energy, NEOConfig.COMMON.maxEnergyStored.get(), 71);

        this.blit(pPoseStack, i + 165, j + 79 - feProgress, 185, 79 - feProgress, 4, feProgress);
    }


    private int mapNum(int toMap, int maxToMap, int maxMapped) {
        if (maxToMap == 0) return maxMapped;
        return (int)(toMap / (double)maxToMap * maxMapped + 0.5);
    }

    private boolean isIn(int x, int y, int minX, int minY, int maxX, int maxY) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
