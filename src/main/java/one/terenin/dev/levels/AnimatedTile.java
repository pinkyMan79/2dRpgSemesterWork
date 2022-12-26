package one.terenin.dev.levels;

import one.terenin.dev.graphics.BaseScreen;

public class AnimatedTile extends TileImpl{

    private int [][] animTileCoords;
    private int currAnimIndex;
    private long lastIterTime;
    private int delayTimeInMillis;

    public AnimatedTile(int id, int[][] animTileCoords, int colour, int levelColour, int delay) {
        super(id, animTileCoords[0][0], animTileCoords[0][1], colour, levelColour);
        this.animTileCoords = animTileCoords;
        this.delayTimeInMillis = delay;
        this.currAnimIndex = 0;
        this.lastIterTime = System.currentTimeMillis();
    }


    public void tick() {
        if ((System.currentTimeMillis() - lastIterTime) >= delayTimeInMillis){
            lastIterTime = System.currentTimeMillis();
            currAnimIndex = (currAnimIndex + 1) % animTileCoords.length;
            this.tileId = (animTileCoords[currAnimIndex][0] + animTileCoords[currAnimIndex][1]*32);
        }
    }

    public void render(BaseScreen screen, BaseLevel baseLevel, int x, int y) {
        screen.render(x, y, tileId, tileColour, 0x00, 1);
    }
}
