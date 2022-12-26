package one.terenin.dev.levels;

import one.terenin.dev.graphics.BaseScreen;

public class SolidTile extends TileImpl{

    public SolidTile(int id, int x, int y, int colour) {
        super(id, x, y, colour);
        this.solid = true;
    }

}
