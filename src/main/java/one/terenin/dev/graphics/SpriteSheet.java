package one.terenin.dev.graphics;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**(divide on 3 just because I want it and we gonna to black here)
 * So, in this block I gonna to explain how works the colors bits in my sprite realisation
 * in the start we take the FFFFFFF like a full black color on 10-numeric system it can be presented like 255
 * we can cut of the data of black color (just because it is simplified equal zero) and make division like this 255/3
 * after that we can take the median by multiply on fact coordinates like (255/3)*0 -> gonna to black base color or (255/3)*1 -> its gonna be middle color
 * and here is hoe color bits work */

@Data
public class SpriteSheet {

    private String path;
    private int width;
    private int height;

    private int[] pixelData;

    @SneakyThrows
    public SpriteSheet(String path) {

        BufferedImage image;
        image = ImageIO.read(Objects.requireNonNull(SpriteSheet.class.getResourceAsStream(path)));
        this.path = path;
        if (image == null) return;
        this.width = image.getWidth();
        this.height = image.getHeight();
        pixelData = image.getRGB(0, 0, width, height, null, 0, width);
        // for diff color data we use -> AARRGGBB a - alpha, r - red, g - green, b - blue it is can be interpreted  like a 0xFFFFFFFF
        for (int i = 0; i < pixelData.length; i++) {
            pixelData[i] = (pixelData[i] & 0xFF)/64; // cut off alpha prefix and 64 -> (255/4) ~ 64
        }
    }
}
