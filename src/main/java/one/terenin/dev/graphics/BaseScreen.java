package one.terenin.dev.graphics;

import lombok.Data;

@Data
public class BaseScreen {

    public static final int M_WIDTH = 64;
    public static final int M_WIDTH_MASK = M_WIDTH - 1;

    private int[] tiles = new int[(int) Math.pow(M_WIDTH, 2)];
    private int[] colours = new int[(int) Math.pow(M_WIDTH, 2) * 4]; // because we have got 3 base colors plus alpha and our tile need to have an information 4 bits of coded colour

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
        for (int i = 0; i < M_WIDTH * M_WIDTH; i++) {

            colours[(i * 4)] = 0xFF00FF;
            colours[(i * 4)+1] = 0x00FFFF;
            colours[(i * 4)+2] = 0xFFFF00;
            colours[(i * 4)+3] = 0xFFFFFF;
        }
    }

    public void render(int[] pxData, int offset, int row) {

        // <<4 can be explain like a moving a point for 4 bits 0010 << 4 == 0010_0000  -> yOffset / 2^4
        for (int yt = yOffset >> 4; yt <= (yOffset + height) >> 4; yt++) {
            int yMin = (yt * 16) - yOffset;
            int yMax = yMin + 16;
            if (yMin < 0) yMin = 0;
            if (yMax > height) yMax = height;

            for (int xt = yOffset >> 4; xt <= (xOffset + width) >> 4; xt++) {
                int xMin = (xt * 16) - yOffset;
                int xMax = xMin + 16;
                if (xMin < 0) xMin = 0;
                if (xMax > width) xMax = width;

                int tileIndex = (xt & M_WIDTH_MASK) + (yt & M_WIDTH_MASK) * M_WIDTH;

                for (int y = yMin; y < yMax; y++) {
                    // 7 here is like base num mask from 8(1000) -> 7(0111)
                    int sheetPixel = ((y + yOffset) & (16 - 1)) * this.spriteSheet.getWidth()
                            + ((xMin + xOffset) & (16 - 1));
                    int tilePixel = xOffset + xMin + (y * row);
                    for (int x = xMin; x < xMax; x++) {
                        int colour = tileIndex * 4 + spriteSheet.getPixelData()[sheetPixel++];
                        pxData[tilePixel++] = colours[colour];
                    }
                }

            }
        }

    }

}
