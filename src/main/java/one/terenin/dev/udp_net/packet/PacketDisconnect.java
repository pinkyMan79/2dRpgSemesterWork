package one.terenin.dev.udp_net.packet;

import one.terenin.dev.udp_net.client.MyGameClient;
import one.terenin.dev.udp_net.server.MyGameServer;

public class PacketDisconnect extends Packet {

    private String username;

    public PacketDisconnect(byte[] data) {
        super(01);
        this.username = readData(data);
    }

    public PacketDisconnect(String username) {
        super(01);
        this.username = username;
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
        return ("01" + this.username).getBytes();
    }

    public String getUsername() {
        return username;
    }

}
