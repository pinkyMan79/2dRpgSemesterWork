package one.terenin.dev.levels;

import one.terenin.dev.graphics.BaseScreen;

public class TileImpl extends Tile {

    protected int tileId;
    protected final int tileColour;

    public TileImpl(int id, int x, int y, int colour, int levelColour) {
        super(id, false, false, levelColour);
        this.tileId = x + y * 32;
        this.tileColour = colour;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(BaseScreen screen, BaseLevel baseLevel, int x, int y) {
        screen.render(x, y, tileId, tileColour, 0, 1);
    }

    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    public int getTileColour() {
        return tileColour;
    }
}
