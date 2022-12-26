package one.terenin.dev.entities;

import one.terenin.dev.levels.BaseLevel;
import one.terenin.dev.levels.Tile;

public abstract class Mob extends Entity{

    protected String name;
    protected int speed;
    protected int numStep;
    protected boolean isMoving;
    protected int movingDirection = 1; // 0 - up, 1 - down, 2 - left, 3 - right
    protected int scale = 1;

    public Mob(BaseLevel level, String name, int x, int y, int speed) {
        super(level);
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    // xa -> (-1;1) and ya -> (-1:1)
    public void move(int xa, int ya){
        if (xa != 0 && ya != 0){
            move(xa, 0);
            move(ya, 0);
            numStep --;
        }
        numStep++;
        if (!hasCollided(xa, ya)){
            if (ya < 0) movingDirection = 0;
            if (ya > 0) movingDirection = 1;
            if (xa < 0) movingDirection = 2;
            if (xa > 0) movingDirection = 3;
        }
        x += xa * speed;
        y += ya * speed;
    }

    protected boolean isSolidTile(int xa, int ya,int x, int y){
        if (level == null){
            return false;
        }
        Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
        if (!lastTile.equals(newTile) && newTile.isSolid()){
            return true;
        }
        return false;
    }

    public abstract boolean hasCollided(int xa, int ya);

}
