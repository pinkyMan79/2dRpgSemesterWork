package one.terenin.dev.graphics.util;

import one.terenin.dev.graphics.BaseScreen;

public class Fonts {

    public static String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789-#!@";

    public static void render(String msg, BaseScreen screen, int x, int y, int colour, int scale){
        msg = msg.toUpperCase();
        for (int i = 0; i < msg.length(); i++) {
            int charIndex = allChars.indexOf(msg.charAt(i));
            if (charIndex >= 0){
                screen.render(x + (i * 8), y, charIndex + 30 * 32, colour, 0, scale);
            }
        }
    }

}
