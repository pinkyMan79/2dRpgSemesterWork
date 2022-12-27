package one.terenin.dev.udp_net.client;

import lombok.Data;
import lombok.SneakyThrows;
import one.terenin.dev.MyGame;
import one.terenin.dev.entities.MultiPlayer;
import one.terenin.dev.udp_net.packet.Packet;
import one.terenin.dev.udp_net.packet.PacketDisconnect;
import one.terenin.dev.udp_net.packet.PacketLogin;
import one.terenin.dev.udp_net.packet.PacketMove;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Data
public class MyGameClient extends Thread{

    private InetAddress ip;
    private DatagramSocket socket;
    private MyGame game;

    @SneakyThrows
    public MyGameClient(MyGame game, String ip) {
        this.game = game;
        this.socket = new DatagramSocket();
        this.ip = InetAddress.getByName(ip);
    }

    // so, i love the datagramPacket just because i dont need a packet) i ve got a resive and send methods
    @SneakyThrows
    @Override
    public void run() {
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
           this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new PacketLogin(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] " +((PacketLogin)packet).getUsername()
                        + " has connected..." + ((PacketLogin)packet).getUsername());
                MultiPlayer player = new MultiPlayer(game.level, 100, 100, ((PacketLogin)packet).getUsername(), address, port);
                game.level.addEntity(player);
                break;
            case DISCONNECT:
                packet = new PacketDisconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((PacketDisconnect) packet).getUsername() + " has left the world...");
                game.level.removeMultiPlayer(((PacketDisconnect) packet).getUsername());
                break;
            case MOVE:
                packet = new PacketMove(data);
                this.movingNet((PacketMove)packet);
        }
    }

    private void movingNet(PacketMove packet) {

        this.game.level.movePlayer(packet.getUsername(), packet.getX(), packet.getY());

    }

    @SneakyThrows
    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, 1331);
        this.socket.send(packet);
    }

}
