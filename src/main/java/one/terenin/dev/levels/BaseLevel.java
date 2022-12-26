package one.terenin.dev.levels;

import lombok.Data;
import one.terenin.dev.entities.Entity;
import one.terenin.dev.entities.Player;
import one.terenin.dev.graphics.BaseScreen;

import java.awt.desktop.ScreenSleepEvent;
import java.util.ArrayList;
import java.util.List;

@Data
public class BaseLevel {

    private int width;
    private int height;
    private byte[] tilemap;
    public List<Entity> entities = new ArrayList<>();

    public BaseLevel(int width, int height) {
        this.tilemap = new byte[width*height];
        this.width = width;
        this.height = height;
        generateLevel();
    }

    //
    public void generateLevel(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x * y % 10 < 5){
                    tilemap[x + y * width] = Tile.STONE.getId();
                }else {
                    tilemap[x + y * width] = Tile.GRASS.getId();
                }
            }
        }
    }

    public void tick(){
        for (Entity entity: entities) {
            entity.tick();
        }
    }

    // here in choosing the sprite by x,y offsets and render it from screen class to the frame
    public void renderTiles(BaseScreen screen, int xOffset, int yOffset){
        if (xOffset < 0) xOffset=0;
        if (xOffset > (width << 3)- screen.getWidth()) xOffset= (width << 3)- screen.getWidth();
        if (yOffset < 0) yOffset=0;
        if (yOffset >  (height << 3)- screen.getHeight()) yOffset= (height << 3)- screen.getHeight();
        screen.setXOffset(xOffset);
        screen.setYOffset(yOffset);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).render(screen, this, x << 3, y << 3);
            }
        }
    }

    public void renderEntities(BaseScreen screen){
        for (Entity entity: entities) {
            entity.render(screen);
        }
    }

    public Tile getTile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height){
            return Tile.VOID;
        }
        return Tile.tiles[tilemap[x + y * width]];
    }

    public void addEntity(Player player) {
        this.entities.add(player);
    }
}
