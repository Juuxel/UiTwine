package juuxel.uitwine.test;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import net.minecraft.entity.player.PlayerEntity;

class LTestGui extends CottonCraftingController {
    private final PlayerEntity player;

    LTestGui(int syncId, PlayerEntity player) {
        super(null, syncId, player.inventory);
        this.player = player;
    }

    PlayerEntity getPlayer() {
        return player;
    }
}
