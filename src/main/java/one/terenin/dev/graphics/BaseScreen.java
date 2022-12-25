package one.terenin.dev.graphics;

import lombok.Data;

@Data
public class BaseScreen {

    public static final int M_WIDTH = 64;
    public static final int M_WIDTH_MASK = M_WIDTH - 1;

    //private int[] tiles = new int[(int) Math.pow(M_WIDTH, 2)];
    //private int[] colours = new int[(int) Math.pow(M_WIDTH, 2) * 4]; // because we have got 3 base colors plus alpha and our tile need to have an information 4 bits of coded colour
    public int[] pixels;

    public int xOffset = 0; // camera offsets
    public int yOffset = 0;

    private int width;
    private int height;

    private SpriteSheet spriteSheet;

    public BaseScreen(int width, int height, SpriteSheet spriteSheet) {
        this.width = width;
        this.height = height;
        this.spriteSheet = spriteSheet;

        // here is the base colour binding
        /*for (int i = 0; i < M_WIDTH * M_WIDTH; i++) {

            colours[(i * 4)] = 0xFF00FF;
            colours[(i * 4)+1] = 0x00FFFF;
            colours[(i * 4)+2] = 0xFFFF00;
            colours[(i * 4)+3] = 0xFFFFFF;
        }*/

        pixels = new int[width*height];
    }

    /*// v1 render
    public void render(int[] pxData, int offset, int row) {

        // <<4 can be explain like a moving a point for 4 bits 0010 << 4 == 0010_0000  -> yOffset / 2^4
        for (int yt = yOffset >> 4; yt <= (yOffset + height) >> 4; yt++) {
            int yMin = (yt * 16) - yOffset;
            int yMax = yMin + 16;
            if (yMin < 0) yMin = 0;
            if (yMax > height) yMax = height;

            for (int xt = xOffset >> 4; xt <= (xOffset + width) >> 4; xt++) {
                int xMin = (xt * 16) - xOffset;
                int xMax = xMin + 16;
                if (xMin < 0) xMin = 0;
                if (xMax > width) xMax = width;

                int tileIndex = (xt & M_WIDTH_MASK) + (yt & M_WIDTH_MASK) * M_WIDTH;

                for (int y = yMin; y < yMax; y++) {
                    // 16 here is like base num mask from 16(10000) -> 15(01111)
                    int sheetPixel = ((y + yOffset) & (16 - 1)) * this.spriteSheet.getWidth()
                            + ((xMin + xOffset) & (16 - 1));
                    int tilePixel = offset + xMin + (y * row);
                    for (int x = xMin; x < xMax; x++) {
                        int colour = tileIndex * 4 + spriteSheet.getPixelData()[sheetPixel++];
                        pxData[tilePixel++] = colours[colour];
                    }
                }

            }
        }

    }
*/
    // version 2 render
    public void render(int x, int y, int tileIndex, int colour, boolean xmirroroing, boolean ymirroring){
        x -= xOffset;
        y -= yOffset;
        int xTileIndex = tileIndex % 64; // % 64 for cutting off by x
        int yTileIndex = tileIndex / 64; // / 64 for cutting off by y
        int tileOffset = (xTileIndex << 4) + (yTileIndex << 4) * spriteSheet.getWidth();
        // ahh shit... here we go again
        for (int yc = 0; yc < 16; yc++) {
            int ySheet = yc;
            if (ymirroring) ySheet = 15 - yc; // inverse image here
            if (yc + y < 0 || yc + y >= height) continue;
            for (int xc = 0; xc < 16; xc++) {
                int xSheet = xc;
                if (xmirroroing) xSheet = 15 - xc;
                if (xc + x < 0 || xc + x >= width) continue;
                int colourBit = (colour >> (spriteSheet.getPixelData()[xSheet + ySheet * spriteSheet.getWidth() + tileOffset] * 8)) & 255;
                if (colourBit < 255){
                    pixels[xc + x + (yc + y) * width] = colourBit;
                }
            }
        }
    }

    public void fontRender(int x, int y, int tileIndex, int colour, boolean xmirroroing, boolean ymirroring){
        x -= xOffset;
        y -= yOffset;
        int xTileIndex = tileIndex % 32; // % 64 for cutting off by x
        int yTileIndex = tileIndex / 32; // / 64 for cutting off by y
        int tileOffset = (xTileIndex << 3) + (yTileIndex << 3) * spriteSheet.getWidth();
        // ahh shit... here we go again
        for (int yc = 0; yc < 8; yc++) {
            int ySheet = yc;
            if (ymirroring) ySheet = 7 - yc; // inverse image here
            if (yc + y < 0 || yc + y >= height) continue;
            for (int xc = 0; xc < 8; xc++) {
                int xSheet = xc;
                if (xmirroroing) xSheet = 7 - xc;
                if (xc + x < 0 || xc + x >= width) continue;
                int colourBit = (colour >> (spriteSheet.getPixelData()[xSheet + ySheet * spriteSheet.getWidth() + tileOffset] * 8)) & 255;
                if (colourBit < 255){
                    pixels[xc + x + (yc + y) * width] = colourBit;
                }
            }
        }
    }

}
