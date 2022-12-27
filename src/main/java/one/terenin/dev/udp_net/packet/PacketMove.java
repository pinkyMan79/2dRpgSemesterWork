package one.terenin.dev.udp_net.packet;

import one.terenin.dev.udp_net.client.MyGameClient;
import one.terenin.dev.udp_net.server.MyGameServer;

public class PacketMove extends Packet{


    private String username;

    private int x;
    private int y;

    public PacketMove(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
    }

    public PacketMove(String username, int x, int y) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    @Override
    public void writeData(MyGameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(MyGameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("02" + this.username+","+this.x +","+this.y).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
