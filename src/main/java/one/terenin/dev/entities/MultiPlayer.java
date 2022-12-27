package one.terenin.dev.entities;

import one.terenin.dev.levels.BaseLevel;
import one.terenin.dev.listeners.InputListener;

import java.net.InetAddress;

public class MultiPlayer extends Player{

    public InetAddress ip;
    public int port;

    public MultiPlayer(BaseLevel level, int x, int y, InputListener input, String username, InetAddress ip, int port) {
        super(level, x, y, input, username);
        this.port = port;
        this.ip = ip;
    }

    public MultiPlayer(BaseLevel level, int x, int y, String username, InetAddress ip, int port) {
        super(level, x, y, null, username);
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void tick() {
        super.tick();
    }
}
