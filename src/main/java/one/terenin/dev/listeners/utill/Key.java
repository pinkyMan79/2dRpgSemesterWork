package one.terenin.dev.listeners.utill;

import lombok.Data;

@Data
public class Key {

    private boolean isPressed = false;

    public void toggle(boolean isPressed){
        this.isPressed = isPressed;
    }

}
