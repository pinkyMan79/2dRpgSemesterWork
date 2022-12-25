package one.terenin.dev.levels;

import lombok.Data;
import one.terenin.dev.graphics.BaseScreen;
import one.terenin.dev.graphics.ColourClass;

//like a blue-print
@Data
public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];
    public static final Tile VOID = new TileImpl(0, 0, 0, ColourClass.get(000, -1, -1 , -1));
    public static final Tile GRASS = new TileImpl(1, 1, 0, ColourClass.get(-1, 333, -1 , -1));
    public static final Tile STONE = new TileImpl(2, 2, 0, ColourClass.get(-1, 131, 141 , -1));

    private byte id; // id
    private boolean solid; // is solid
    private boolean emitter; // is collision obj

    public Tile(int id, boolean solid, boolean emitter) {
        this.id = (byte) id;
        this.solid = solid;
        this.emitter = emitter;
        tiles[id] = this;
    }

    public abstract void render(BaseScreen screen, BaseLevel baseLevel, int x, int y);

}
