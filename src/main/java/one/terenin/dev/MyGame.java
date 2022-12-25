package one.terenin.dev;

import lombok.Data;
import lombok.SneakyThrows;
import one.terenin.dev.graphics.BaseScreen;
import one.terenin.dev.graphics.ColourClass;
import one.terenin.dev.graphics.SpriteSheet;
import one.terenin.dev.graphics.util.Fonts;
import one.terenin.dev.listeners.InputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

@Data
public class MyGame extends Canvas implements Runnable{

    private static final Long serialUID = 1L;
    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH / 12*9;
    public static final int SCALE = 3;
    public static final  String NAME = "MySemWork";

    public boolean isRunning = false;

    private JFrame frame;

    //private SpriteSheet emptyBlock = new SpriteSheet("/");
    private BaseScreen screen;
    private int ticksCount = 0;

    private InputListener inputListener;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixelsBuffer = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colours = new int[216];


    public MyGame(){
        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack(); // for preferred size screen
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public void initScreen(){
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int red = r * 255 / 5;
                    int green = g * 255 / 5;
                    int blue = b * 255 / 5;
                    colours[index++] = red << 16 | green << 8 | blue;
                    /**
                     * explain of this part are pretty simple
                     * we've got 6 colors, but mask of this is 5 and to get the right color we take the base FFFFFF(255) and divide it by mask
                     * colours[index++] = r << 16 | g << 8 | b
                     * Here is we've got a 2 bits for every color rrggbb(2^4) and the rr -> (rr_gggbb, we make сдвиг on 2 last bits, it is similar for gg and bb) and to right divide the data we need to make сдвиг*/
                }
            }
        }

        screen = new BaseScreen(WIDTH, HEIGHT, new SpriteSheet("/img.png"));
        inputListener = new InputListener(this);
    }

    private synchronized void start() {
        isRunning = true;
        new Thread(this).start();
    }

    private synchronized void stop() {
        isRunning = false;
    }

    public void tickListener(){
        ticksCount++;
        if (inputListener.up.isPressed()){screen.yOffset -- ;}
        if (inputListener.down.isPressed()){screen.yOffset ++ ;}
        if (inputListener.left.isPressed()){screen.xOffset -- ;}
        if (inputListener.right.isPressed()){screen.xOffset ++ ;}
    }

    public void render(){
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null){
            createBufferStrategy(3);
            return;
        }
        //screen.render(pixelsBuffer, 0, WIDTH);

        /*for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                // take the pixel value by <<4
                screen.render(x << 4, y << 4, 0, ColourClass.get(555, 505, 055, 550), false, false);
            }
        }*/

        for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                // take the pixel value by <<4
                screen.render(x << 4, y << 4, 0, ColourClass.get(555, 505, 055, 550), false, false);
            }
        }

        String msg = "It is my Game Engine";
        Fonts.render(msg, screen, screen.xOffset + screen.getWidth() / 2 - msg.length()*4, screen.yOffset + screen.getHeight() / 2, ColourClass.get(000, -1, -1, 500));

        for (int y = 0; y < screen.getHeight(); y++) {
            for (int x = 0; x < screen.getWidth(); x++) {
                int colourCode = screen.pixels[x + y * screen.getWidth()];
                if (colourCode < 255) pixelsBuffer[x + y * WIDTH] = colours[colourCode];
            }
        }


        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0, getWidth(), getHeight());
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        graphics.dispose(); // clean resourses
        bufferStrategy.show();
    }

    @SneakyThrows
    @Override
    public void run() {
        // here is logic like thread sleep, but it without sleeping just stop the render by ticks, thread be available always
        long nanoTickTime = System.nanoTime();
        double nanoSecondsPerTick = 10000000D / (25D);
        int frames = 0;
        int ticks = 0;
        initScreen();
        long lastTimeInMillis = System.currentTimeMillis();
        double delta = 0;

        while (isRunning){
            long nowTime = System.nanoTime();
            delta = (nowTime - nanoTickTime) / nanoSecondsPerTick ;
            nanoTickTime = nowTime;
            boolean makeRender = true;
            while (delta >= 1){
                ticks++;
                tickListener();
                delta -= 1;
                makeRender = true;
            }
            if(makeRender){
                frames++;
                render();
            }
            if (System.currentTimeMillis() - lastTimeInMillis > 1000){
                lastTimeInMillis += 1000;
                System.out.println(frames + " " + ticks);
                frames = 0;
                ticks = 0;
            }
            //System.out.println(frames + " " + ticks); uncommenet to see how many frames we done per ticks(it s important to understand that wed just make updates by ticks)
        }
    }

    public static void main(String[] args) {
        new MyGame().start();
    }
}
