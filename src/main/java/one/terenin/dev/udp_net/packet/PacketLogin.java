package one.terenin.dev.udp_net.packet;

import lombok.Data;
import one.terenin.dev.udp_net.client.MyGameClient;
import one.terenin.dev.udp_net.server.MyGameServer;

public class PacketLogin extends Packet{


    private String username;

    public PacketLogin(byte[] data) {
        super(00);
        this.username = readData(data);
    }

    public PacketLogin(String username) {
        super(00);
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
        return ("00" + this.username).getBytes();
    }

    public String getUsername() {
        return username;
    }
}
