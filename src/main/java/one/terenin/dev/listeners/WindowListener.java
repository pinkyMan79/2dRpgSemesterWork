package one.terenin.dev.listeners;

import one.terenin.dev.MyGame;
import one.terenin.dev.udp_net.packet.PacketDisconnect;

import java.awt.event.WindowEvent;

public class WindowListener implements java.awt.event.WindowListener {
    private final MyGame game;

    public WindowListener(MyGame game) {
        this.game = game;
        this.game.getFrame().addWindowListener(this);
    }

    @Override
    public void windowActivated(WindowEvent event) {
    }

    @Override
    public void windowClosed(WindowEvent event) {
    }

    @Override
    public void windowClosing(WindowEvent event) {
        PacketDisconnect packet = new PacketDisconnect(this.game.player.getUsername());
        packet.writeData(this.game.getClient());
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
    }

    @Override
    public void windowIconified(WindowEvent event) {
    }

    @Override
    public void windowOpened(WindowEvent event) {
    }
}
