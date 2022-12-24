package one.terenin.dev.graphics;

public class ColourClass {

    /**
     * Here is the similar logic from tick() in the MyGame class
     * for example
     * @param colour1 -> need for full white colour
     * @param colour2 -> need for full black
     * @param colour3 -> need for lighter than black but not white it is near the black
     * @param colour4  -> need for darker that whiter but not a black it is near the white
     * It is just EXAMPLE and you can paste every colour that you want
     * */

    public static int get(int colour1, int colour2, int colour3, int colour4){
        return (extract(colour4) << 24) + (extract(colour3) << 16) + (extract(colour2) << 8) + (extract(colour1));
    }

    public static int extract(int colour){
        if (colour < 0) return 255;
        /*int red = colour / 100;
        int green = (colour - red * 100) / 10;
        int blue = ((colour - (red * 100 + green * 10)));*/
        int red = colour / 100 % 10;
        int green = colour / 10 % 10;
        int blue = colour % 10;
        return red * 36 + green * 6 + blue;
    }

}
