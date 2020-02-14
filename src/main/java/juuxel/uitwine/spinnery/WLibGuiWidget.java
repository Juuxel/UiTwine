package juuxel.uitwine.spinnery;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WPosition;
import spinnery.widget.WSize;

/**
 * A LibGui -> Spinnery wrapper.
 */
public class WLibGuiWidget extends spinnery.widget.WWidget {
	private final WWidget widget;
	private int renderMouseX, renderMouseY; // :irritatered:

	public WLibGuiWidget(WPosition position, WWidget widget, WInterface linkedInterface) {
		this.linkedInterface = linkedInterface;
		this.widget = widget;

		setPosition(position);
		setTheme("light");
		setSize(WSize.of(widget.getWidth(), widget.getHeight()));
	}

	@Override
	public void setSize(WSize size) {
		super.setSize(size);
		widget.setSize(size.getX(), size.getY());
	}

	@Override
	public void onCharTyped(char character) {
		super.onCharTyped(character);
		widget.onCharTyped(character);
	}

	@Override
	public void onKeyPressed(int keyPressed, int character, int keyModifier) {
		super.onKeyPressed(keyPressed, character, keyModifier);
		widget.onKeyPressed(character, keyPressed, keyModifier);
	}

	@Override
	public void onKeyReleased(int keyReleased) {
		super.onKeyReleased(keyReleased);
		widget.onKeyReleased(0, keyReleased, 0);
	}

	@Override
	public void onMouseReleased(double mouseX, double mouseY, int mouseButton) {
		super.onMouseReleased(mouseX, mouseY, mouseButton);
		int mx = (int) (mouseX - getX());
		int my = (int) (mouseY - getY());
        // note: not using hit because the screens use lastResponder
		widget.onMouseUp(mx, my, mouseButton);
		widget.onClick(mx, my, mouseButton);
	}

	@Override
	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.onMouseClicked(mouseX, mouseY, mouseButton);
		int mx = mouseX - getX();
		int my = mouseY - getY();
        // note: not using hit because the screens use lastResponder
		widget.onMouseDown(mx, my, mouseButton);
	}

	@Override
	public void onMouseDragged(int mouseX, int mouseY, int mouseButton, double dragOffsetX, double dragOffsetY) {
		super.onMouseDragged(mouseX, mouseY, mouseButton, dragOffsetX, dragOffsetY);
		int mx = mouseX - getX();
		int my = mouseY - getY();
        // note: not using hit because the screens use lastResponder
		widget.onMouseDrag(mx, my, mouseButton, dragOffsetX, dragOffsetY);
	}

    @Override
    public boolean scanFocus(int mouseX, int mouseY) {
	    this.renderMouseX = mouseX;
	    this.renderMouseY = mouseY;
        return super.scanFocus(mouseX, mouseY);
    }

    @Override
	public void onMouseMoved(double mouseX, double mouseY) {
		super.onMouseMoved(mouseX, mouseY);
		int mx = (int) (mouseX - getX());
		int my = (int) (mouseY - getY());
		WWidget child = widget.hit(mx, my);
		child.onMouseMove(mx - child.getAbsoluteX(), my - child.getAbsoluteY());
	}

	@Override
	public void onMouseScrolled(int mouseX, int mouseY, double amount) {
		super.onMouseScrolled(mouseX, mouseY, amount);
		int mx = mouseX - getX();
		int my = mouseY - getY();
        WWidget child = widget.hit(mx, my);
		child.onMouseScroll(mx - child.getAbsoluteX(), my - child.getAbsoluteY(), amount);
	}

	@Override
	public void tick() {
		widget.tick();
	}

	@Override
	public boolean isWithinBounds(int mouseX, int mouseY) {
		return widget.isWithinBounds(mouseX - getX(), mouseY - getY());
	}

	@Override
	public void draw() {
		widget.paintBackground(
				getX(), getY(), renderMouseX - getX(), renderMouseY - getY()
		);
	}
}
