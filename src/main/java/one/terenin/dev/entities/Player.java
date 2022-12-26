package one.terenin.dev.entities;

import one.terenin.dev.graphics.BaseScreen;
import one.terenin.dev.graphics.ColourClass;
import one.terenin.dev.levels.BaseLevel;
import one.terenin.dev.listeners.InputListener;

public class Player extends Mob{

    InputListener input;

    int colour = ColourClass.get(-1, 111 ,145 ,543);

    private int scale = 1;

    public Player(BaseLevel level, int x, int y, InputListener input) {
        super(level, "Vasily", x, y, 1);
        this.input = input;
    }

    // so, i ve got 8 by 8 render arean and to render the full sprite wich is 16 on 16 and thats because i render 4 times
    @Override
    public void render(BaseScreen screen) {
        int xTile = 0;
        int yTile = 28;

        // ang here is variables for animation
        int walkingSpeed = 4;
        int flipTop = (numStep >> walkingSpeed) & 1; 
        int flipBottom = (numStep >> walkingSpeed) & 1;
        
        if (movingDirection  == 1){
            xTile += 2;
        } else if (movingDirection > 1) {
            xTile += 4 + ((numStep >> walkingSpeed) & 1) * 2;
            flipTop = (movingDirection - 1) % 2;
        }



        int multiplier = 8 * scale;
        int xOffset = x - multiplier/2;
        int yOffset = y - multiplier/2 - 4;
        screen.render(xOffset + (multiplier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop, scale );
        screen.render(xOffset + multiplier - (multiplier * flipTop), yOffset, 1 +xTile + yTile * 32, colour, flipTop, scale );
        screen.render(xOffset + (multiplier * flipBottom), yOffset + multiplier, xTile + (yTile + 1) * 32, colour, flipBottom, scale );
        screen.render(xOffset + multiplier - (multiplier * flipBottom), yOffset + multiplier, (xTile + 1) + (yTile + 1) * 32, colour, flipBottom, scale );
    }

    // by tick i got the playerr moving on the frame
    @Override
    public void tick() {
        int xa = 0;
        int ya = 0;
        if (input.up.isPressed()){ya -- ;}
        if (input.down.isPressed()){ya ++ ;}
        if (input.left.isPressed()){xa -- ;}
        if (input.right.isPressed()){xa ++ ;}

        if ((xa != 0 || ya != 0)  && !hasCollided(xa, ya)){
            move(xa, ya);
            isMoving = true;
        }else {
            isMoving = false;
        }

    }

    @Override
    public boolean hasCollided(int xa, int ya) {

        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;

        // здесь я просто обрисовываю блок коллизии
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMin)) return true;
        }
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMax)) return true;
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMin, y)) return true;
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMax, y)) return true;
        }
        return false;
    }
}
