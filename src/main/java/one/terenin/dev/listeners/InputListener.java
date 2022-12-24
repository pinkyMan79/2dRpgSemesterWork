package one.terenin.dev.listeners;

import one.terenin.dev.MyGame;
import one.terenin.dev.listeners.utill.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputListener implements KeyListener{

    List<Key> keys = new ArrayList<>();

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    public InputListener(MyGame game){
        game.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e.getKeyCode(), false);
    }

    public void toggle(int keycode, boolean isPressed){
        if (keycode == KeyEvent.VK_W){
            up.toggle(isPressed);
        }else if(keycode == KeyEvent.VK_D){
            right.toggle(isPressed);
        } else if (keycode == KeyEvent.VK_A) {
            left.toggle(isPressed);
        } else if (keycode == KeyEvent.VK_S) {
            down.toggle(isPressed);
        }
    }

}
