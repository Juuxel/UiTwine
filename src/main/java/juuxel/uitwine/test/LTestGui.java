package juuxel.uitwine.test;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.Alignment;
import juuxel.uitwine.spinnery.WBetterInterface;
import juuxel.uitwine.spinnery.WSpinneryWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import spinnery.widget.WPosition;
import spinnery.widget.WSize;
import spinnery.widget.WType;

class LTestGui extends CottonCraftingController {
    private final PlayerEntity player;

    LTestGui(int syncId, PlayerEntity player) {
        super(null, syncId, player.inventory);
        this.player = player;

        WGridPanel root = new WGridPanel();

        WBetterInterface iface = new WBetterInterface(WPosition.of(WType.FREE, 0, 0, 0), WSize.of(6*18, 10*18));
        iface.setTheme("light");
        iface.setBackgroundPainter(() -> BackgroundPainter.VANILLA_9PATCH);
        iface.setLabel(new LiteralText("spinnie"));

        root.add(new WLabel(new LiteralText("libby"), WLabel.DEFAULT_TEXT_COLOR).setAlignment(Alignment.CENTER), 0, 0, 6, 1);
        root.add(new WSpinneryWidget(iface), 3, 1, 3, 5);

        setRootPanel(root);
        root.validate(this);
    }

    PlayerEntity getPlayer() {
        return player;
    }
}
