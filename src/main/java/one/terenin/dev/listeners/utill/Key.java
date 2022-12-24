package one.terenin.dev.listeners.utill;

import lombok.Data;

@Data
public class Key {

    private boolean isPressed = false;
    private int countOfPressTimes = 0;

    public void toggle(boolean isPressed){
        this.isPressed = isPressed;
        if (isPressed) countOfPressTimes++;
    }

}
