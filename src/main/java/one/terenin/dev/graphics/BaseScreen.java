package one.terenin.dev.graphics;

import lombok.Data;

@Data
public class BaseScreen {

    public static final int M_WIDTH = 64;
    public static final int M_WIDTH_MASK = M_WIDTH - 1;

    private int[] tiles = new int[(int) Math.pow(M_WIDTH, 2)];
    private int[] colours = new int[(int) Math.pow(M_WIDTH, 2) * 4]; // because we have got 3 base colors plus alpha and our tile need to have an information 4 bits of coded colour

    private int xOffset = 0; // camera offsets
    private int yOffset = 0;

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
}
