package one.terenin.dev.entities;

import one.terenin.dev.graphics.BaseScreen;
import one.terenin.dev.graphics.ColourClass;
import one.terenin.dev.levels.BaseLevel;
import one.terenin.dev.listeners.InputListener;

public class Player extends Mob{

    InputListener input;

    int colour = ColourClass.get(-1, 111 ,145 ,543);

    public Player(BaseLevel level, int x, int y, InputListener input) {
        super(level, "Vasily", x, y, 1);
        this.input = input;
    }

    @Override
    public void render(BaseScreen screen) {
        int xTile = 0;
        int yTile = 28;
        int multiplier = 8 * scale;
        int xOffset = x - multiplier/2;
        int yOffset = y - multiplier/2 - 4;
        screen.render(xOffset, yOffset, xTile + yTile * 32, colour, false, false );
        screen.render(xOffset + multiplier, yOffset, 1 +xTile + yTile * 32, colour, false, false );
        screen.render(xOffset, yOffset + multiplier, xTile + (yTile + 1) * 32, colour, false, false );
        screen.render(xOffset + multiplier, yOffset + multiplier, (xTile + 1) + (yTile + 1) * 32, colour, false, false );
    }

    @Override
    public void tick() {
        int xa = 0;
        int ya = 0;
        if (input.up.isPressed()){ya -- ;}
        if (input.down.isPressed()){ya ++ ;}
        if (input.left.isPressed()){xa -- ;}
        if (input.right.isPressed()){xa ++ ;}

        if (xa != 0 || ya != 0){
            move(xa, ya);
            isMoving = true;
        }else {
            isMoving = false;
        }

    }

    @Override
    public boolean hasCollided(int xa, int ya) {
        return false;
    }
}
