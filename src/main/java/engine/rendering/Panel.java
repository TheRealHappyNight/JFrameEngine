package engine.rendering;

import engine.gamepropertiessingleton.GameProperties;
import engine.gamepropertiessingleton.GamePropertiesSingleton;
import engine.input.keyboard.KeyboardAdapter;
import engine.input.mouse.MouseAdapter;
import engine.rendering.interfaces.Drawable;
import engine.rendering.interfaces.Updateable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Panel extends JPanel implements ActionListener {
    private GameProperties gameProperties;
    private Timer timer;

    public Panel() {
        this.gameProperties = GamePropertiesSingleton.getInstance();

        this.setPreferredSize(new Dimension(gameProperties.getScreenWidth(), gameProperties.getScreenHeight()));
        this.setBackground(Color.black);
        this.setFocusable(true);

        var keyboard = gameProperties.getKeyboardListener();
        if (null != keyboard) {
            this.addKeyListener(new KeyboardAdapter(keyboard));
        }

        var mouse = gameProperties.getMouseListener();
        if (null != mouse) {
            this.addMouseListener(new MouseAdapter(mouse));
        }

        this.timer = new Timer(gameProperties.getGameSpeed(), this);
        this.timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g.create();
        for (var iterator : gameProperties.getDrawable().keySet() ) {
            ArrayList<Drawable> drawables = gameProperties.getDrawable(iterator);
            if(null == drawables) {
                return;
            }
            for (Drawable drawable : drawables) {
                drawable.draw(g2D);
            }
        }
        g2D.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int index = 0; index < gameProperties.getUpdateablesKeySize(); ++index) {
            ArrayList<Updateable> updateables = gameProperties.getUpdateable(index);
            for (Updateable updateable : updateables) {
                updateable.update();
            }
        }
        this.repaint();
    }
}
