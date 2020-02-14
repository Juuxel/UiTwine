package juuxel.uitwine.test;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import juuxel.uitwine.spinnery.SUtil;
import juuxel.uitwine.spinnery.WBetterInterface;
import juuxel.uitwine.spinnery.WLibGuiWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import spinnery.common.BaseContainer;
import spinnery.widget.*;

public class STestGui extends BaseContainer {
    public STestGui(int syncId, PlayerInventory playerInv) {
        super(syncId, playerInv);

        WBetterInterface iface = new WBetterInterface(WPosition.of(WType.FREE, 0, 0, 0), WSize.of(6*18, 10*18), this);
        getInterfaces().add(iface);
        iface.center();
        iface.setOnAlign(iface::center);
        iface.setBackgroundPainter(BackgroundPainter.VANILLA);

        WButton sButton = new WButton(SUtil.anchoredGrid(iface, 0, 1), SUtil.gridSize(5, 1), iface);
        sButton.setLabel(new LiteralText("wow bad button"));
        sButton.setOnMouseClicked(() -> {
            if (sButton.getFocus()) {
                sButton.setLabel(new LiteralText("kibby good"));
            }
        });

        WGridPanel panel = new WGridPanel();
        WWidget lButton = Util.make(new io.github.cottonmc.cotton.gui.widget.WButton(), b -> {
            b.setLabel(new LiteralText("wow good button"));
            b.setOnClick(() -> b.setLabel(new LiteralText("kibby better")));
        });
        panel.add(lButton, 0, 0, 5, 1);
        WLibGuiWidget wrapper = new WLibGuiWidget(SUtil.anchored(iface, 0, 20 + 18), panel, iface);

        iface.add(sButton, wrapper);
    }
}
