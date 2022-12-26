package one.terenin.dev.levels;

import one.terenin.dev.graphics.BaseScreen;

public class TileImpl extends Tile {

    private int tileId;
    private final int tileColour;

    public TileImpl(int id, int x, int y, int colour) {
        super(id, false, false);
        this.tileId = x + y;
        this.tileColour = colour;
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
