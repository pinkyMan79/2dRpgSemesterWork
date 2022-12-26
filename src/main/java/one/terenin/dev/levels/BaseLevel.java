package one.terenin.dev.levels;

import lombok.Data;
import lombok.SneakyThrows;
import one.terenin.dev.entities.Entity;
import one.terenin.dev.entities.Player;
import one.terenin.dev.graphics.BaseScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class BaseLevel {

    private int width;
    private int height;
    private byte[] tilemap; // this id present the id of tiles not the colors, and this is important to understand
    public List<Entity> entities = new ArrayList<>();
    private String imagePath;
    private BufferedImage image;

    public BaseLevel(String imagePath) {
        this.imagePath = imagePath;
        if (this. imagePath != null){
            this.loadLevelFromFile();
        }else {
            this.width = 64;
            this.height = 64;
            this.tilemap = new byte[width * height];
            generateLevel();
        }
    }

    @SneakyThrows
    private void loadLevelFromFile() {

        this.image = ImageIO.read(Objects.requireNonNull(BaseLevel.class.getResourceAsStream(this.imagePath)));
        this.width = image.getWidth();
        this.height = image.getHeight();
        tilemap = new byte[width*height];
        this.loadTiles();

    }

    private void loadTiles() {

        int[] tileColours = image.getRGB(0, 0, width, height, null, 0, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
               tc: for (Tile tile: Tile.tiles) {
                    if (tile != null && tile.getLevelColour() == tileColours[x+y*width]){
                        this.tilemap[x + y * width] = tile.getId();
                        break tc;
                    }
                }
            }
        }
    }

    @SneakyThrows
    private  void saveLevelToFile(){
        ImageIO.write(image, "png", new File(Objects.requireNonNull(BaseLevel.class.getResource(this.imagePath)).getFile()));
    }

    public void alterTile(int x, int y, Tile newTile){
        this.tilemap[x + y * width] = newTile.getId();
        image.setRGB(x, y, newTile.getLevelColour());
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

        for (Tile tile: Tile.tiles) {
            if (tile == null) break;
            tile.tick();
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
