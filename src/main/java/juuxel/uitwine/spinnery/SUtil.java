package juuxel.uitwine.spinnery;

import spinnery.widget.WPosition;
import spinnery.widget.WSize;
import spinnery.widget.WType;
import spinnery.widget.WWidget;

public final class SUtil {
    private SUtil() {}

    public static WPosition anchored(WWidget anchor, int x, int y) {
        return WPosition.of(WType.ANCHORED, x, y, 0, anchor);
    }

    public static WPosition anchoredGrid(WWidget anchor, int x, int y) {
        return WPosition.of(WType.ANCHORED, x * 18, y * 18, 0, anchor);
    }

    public static WSize gridSize(int x, int y) {
        return WSize.of(x * 18, y * 18);
    }
}
