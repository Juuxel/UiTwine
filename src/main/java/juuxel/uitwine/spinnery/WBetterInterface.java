package juuxel.uitwine.spinnery;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import spinnery.client.BaseRenderer;
import spinnery.common.BaseContainer;
import spinnery.registry.ThemeRegistry;
import spinnery.widget.*;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * A Spinnery {@link WInterface} that supports LibGui {@linkplain BackgroundPainter painters}.
 */
public class WBetterInterface extends WInterface {
	@Environment(EnvType.CLIENT)
	@Nullable
	private BackgroundPainter backgroundPainter = null;

	private final WSpinneryWidget paintingDelegate = new WSpinneryWidget(this);

	public WBetterInterface(BaseContainer linkedContainer) {
		super(linkedContainer);
	}

	public WBetterInterface(WPosition position) {
		super(position);
	}

	public WBetterInterface(WPosition position, WSize size) {
		super(position, size);
	}

	public WBetterInterface(WPosition position, WSize size, BaseContainer linkedContainer) {
		super(position, size, linkedContainer);
	}

	@Environment(EnvType.CLIENT)
	@Nullable
	public BackgroundPainter getBackgroundPainter() {
		return backgroundPainter;
	}

	@Environment(EnvType.CLIENT)
	public void setBackgroundPainter(@Nullable BackgroundPainter backgroundPainter) {
		this.backgroundPainter = backgroundPainter;
	}

	public void setBackgroundPainter(Supplier<BackgroundPainter> painterSupplier) {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			setBackgroundPainter(painterSupplier.get());
		}
	}

	// FIXME: Apparently this is fixed in the upcoming release
    @Override
    public WColor getColor(int number) {
        return ThemeRegistry.get(getTheme(), WInterface.class).get(number);
    }

    @Override
	public void draw() {
	    if (isHidden()) return;

	    if (isDrawable()) {
            if (backgroundPainter != null) {
                backgroundPainter.paintBackground(getX(), getY(), paintingDelegate);
            }

            int x = getX();
            int y = getY();
            int z = getZ();
            int w = getWidth();

            if (hasLabel()) {
                BaseRenderer.drawText(isLabelShadowed(), getLabel().asFormattedString(), x + w / 2 - BaseRenderer.getTextRenderer().getStringWidth(getLabel().asFormattedString()) / 2, y + 6, getColor(LABEL).RGB);
                BaseRenderer.drawRectangle(x, y + 16, z, w, 1, getColor(OUTLINE));
                BaseRenderer.drawRectangle(x + 1, y + 17, z, w - 2, 0.75, getColor(SHADOW));
            }
        }

		for (WWidget widget : getWidgets()) {
			widget.draw();
		}
	}
}
