package one.terenin.dev.udp_net.client;

import lombok.Data;
import lombok.SneakyThrows;
import one.terenin.dev.MyGame;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
            System.out.println("Seeer" + Arrays.toString(packet.getData()));
        }
    }

    @SneakyThrows
    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, 1331);
        this.socket.send(packet);
    }

}
