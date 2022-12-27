package one.terenin.dev.udp_net.server;

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
import java.util.ArrayList;
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
                this.addConnection(player, (PacketLogin) packet);
                break;
            case DISCONNECT:
                packet = new PacketDisconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((PacketDisconnect) packet).getUsername() + " has left...");
                this.removeConnection((PacketDisconnect) packet);
                break;
            case MOVE:
                packet = new PacketMove(data);
                System.out.println(((PacketMove) packet).getUsername() + " has moved to "
                        + ((PacketMove) packet).getX() + ", " + ((PacketMove) packet).getY());
                this.movingNet(((PacketMove) packet));
                break;
        }
    }

    private void movingNet(PacketMove packet) {

        if (getPlayerMP(packet.getUsername()) != null){
            int index = getMultiPlayerByIndex(packet.getUsername());
            this.availablePlayers.get(index).x =packet.getX();
            this.availablePlayers.get(index).y =packet.getY();
            packet.writeData(this);
        }

    }

    public void removeConnection(PacketDisconnect packet) {
        this.availablePlayers.remove(getMultiPlayerByIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public MultiPlayer getPlayerMP(String username) {
        for (MultiPlayer player : this.availablePlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public int getMultiPlayerByIndex(String username) {
        int index = 0;
        for (MultiPlayer player : this.availablePlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void addConnection(MultiPlayer player, PacketLogin login) {

        boolean isConnected = false;
        for (MultiPlayer p: this.availablePlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ip == null) {
                    p.ip = player.ip;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                isConnected = true;
            } else {
                // relay to the current connected player that there is a new
                // player
                sendData(login.getData(), p.ip, p.port);

                // relay to the new player that the currently connect player
                // exists
                login = new PacketLogin(p.getUsername());
                sendData(login.getData(), player.ip, player.port);
            }
        }
        if (!isConnected) {
            this.availablePlayers.add(player);
        }
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
