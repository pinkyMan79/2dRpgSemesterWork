package one.terenin.dev.udp_net.server;

import lombok.SneakyThrows;
import one.terenin.dev.MyGame;
import one.terenin.dev.entities.MultiPlayer;
import one.terenin.dev.udp_net.packet.Packet;
import one.terenin.dev.udp_net.packet.PacketLogin;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyGameServer extends Thread{

    private DatagramSocket socket;
    private MyGame game;

    private List<MultiPlayer> availablePlayers = new ArrayList<>();

    @SneakyThrows
    public MyGameServer(MyGame game) {
        this.game = game;
        this.socket = new DatagramSocket(1331);
    }

    // so, i love the datagramPacket just because i dont need a packet) i ve got a resive and send methods
    @SneakyThrows
    @Override
    public void run() {
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
            String msg = Arrays.toString(packet.getData());
            System.out.println("Ser" + Arrays.toString(packet.getData()));
            if (msg.trim().equals("pppp")) {
                sendData("oooo".getBytes(StandardCharsets.UTF_8), packet.getAddress(), packet.getPort());
            }else {
                sendData("sdfe5rt".getBytes(StandardCharsets.UTF_8), packet.getAddress(), packet.getPort());
            }
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
                System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
                        + " has connected..." + packet.getUsername());
                MultiPlayer player = new MultiPlayer(game.level, 100, 100, packet.getUsername(), address, port);
                this.addConnection(player);
                break;
            case DISCONNECT:
                break;
        }
    }

    private void addConnection(MultiPlayer player) {
    }


    @SneakyThrows
    public void sendData(byte[] data, InetAddress ip, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
        this.socket.send(packet);
    }

    public void sendDataToAllClients(byte[] data){
        for (MultiPlayer player: availablePlayers) {
            sendData(data, player.ip, player.port);
        }
    }
}
