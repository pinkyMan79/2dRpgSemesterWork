package one.terenin.dev.udp_net.server;

import lombok.SneakyThrows;
import one.terenin.dev.MyGame;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyGameServer extends Thread{

    private DatagramSocket socket;
    private MyGame game;

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
            String msg = Arrays.toString(packet.getData());
            System.out.println("Ser" + Arrays.toString(packet.getData()));
            if (msg.trim().equals("pppp")) {
                sendData("oooo".getBytes(StandardCharsets.UTF_8), packet.getAddress(), packet.getPort());
            }else {
                sendData("sdfe5rt".getBytes(StandardCharsets.UTF_8), packet.getAddress(), packet.getPort());
            }
        }
    }

    @SneakyThrows
    public void sendData(byte[] data, InetAddress ip, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
        this.socket.send(packet);
    }

}
