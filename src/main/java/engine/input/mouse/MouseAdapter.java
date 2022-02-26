package engine.input.mouse;


import engine.basictypes.Vector2;
import engine.input.interfaces.Clickable;

public class MouseAdapter extends java.awt.event.MouseAdapter {
    Clickable clickable;

    public MouseAdapter(Clickable clickable) {
        this.clickable = clickable;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        super.mouseClicked(e);
        MouseEvent event = new MouseEvent(new Vector2(e.getX(), e.getY()));
        this.clickable.processMouseInput(event);
    }
}
