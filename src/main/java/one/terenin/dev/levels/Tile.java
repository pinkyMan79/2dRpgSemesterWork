package one.terenin.dev.levels;

import lombok.Data;
import one.terenin.dev.graphics.BaseScreen;
import one.terenin.dev.graphics.ColourClass;

//like a blue-print
@Data
public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];
    private int levelColour;
    public static final Tile VOID = new SolidTile(0, 0, 0, ColourClass.get(000, -1, -1 , -1), 0xFF000000);
    public static final Tile GRASS = new TileImpl(1, 1, 0, ColourClass.get(-1, 444, -1 , -1), 0xFF555555);
    public static final Tile STONE = new SolidTile(2, 2, 0, ColourClass.get(-1, 131, 141 , -1), 0xFF00FF00);
    public static final Tile WATER = new AnimatedTile(3, new int[][]{{0,5},{1,5},{2,5},{1,5}}, ColourClass.get(-1,004,115,-1), 0xFF0000FF, 1000);

    private byte id; // id
    protected boolean solid; // is solid
    protected boolean emitter; // is collision obj

    public Tile(int id, boolean solid, boolean emitter, int colour) {
        this.levelColour = colour;
        this.id = (byte) id;
        this.solid = solid;
        this.emitter = emitter;
        tiles[id] = this;
    }

    public abstract void tick();

    public abstract void render(BaseScreen screen, BaseLevel baseLevel, int x, int y);

}
