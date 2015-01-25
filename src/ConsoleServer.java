/**
 * Created by AmierNaji on 1/17/15.
 */

import com.esotericsoftware.kryonet.*;

public class ConsoleServer extends Listener {

    public static Server server;
    public static int tcpPort = 59403;
    public static boolean connected = false;

    public static void main(String[] args) throws Exception {

        server = new Server();
        server.bind(tcpPort, tcpPort);

        server.getKryo().register(PacketMessage.class);

        server.start();

        server.addListener(new ConsoleServer());

        System.out.println("Server Works.");
    }

    public void connected(Connection c) {

        System.out.println("Connection from "+c.getRemoteAddressTCP().getHostString()+" received.");

        PacketMessage pcm = new PacketMessage();
        pcm.message = "Connected.";

        c.sendTCP(pcm);
    }

    public void received(Connection j, Object p) {

        if (p instanceof PacketMessage) {

            PacketMessage pm = (PacketMessage) p;
            System.out.println(pm.message);

            if (connected) {

                PacketMessage sendBack = new PacketMessage();
                sendBack.message = TerminalOutput.executeString(pm.message);
                j.sendTCP(sendBack);
            }

        }

        connected = true;
    }

    public void disconnected(Connection c) {

        System.out.println("Client disconnected.");
    }
}
