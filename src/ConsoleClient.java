/**
 * Created by AmierNaji on 1/17/15.
 */

import com.esotericsoftware.kryonet.*;
import java.util.Scanner;

public class ConsoleClient extends Listener {

    static Client client;

    static String ip = " ";
    static int ipPort = 59403;

    static boolean messageReceived = false;
    static boolean running = true;
    static Connection conn;


    public static void main(String[] args) throws Exception {

        System.out.println("Connect to the Following: \n");
        Scanner sc = new Scanner(System.in);

        ip = sc.nextLine();

        if (!successfulConnection(sc)) {

            System.out.println("Unable to Connect. Exiting.");
            System.exit(0);
        }


    }

    public static boolean successfulConnection(Scanner sc) throws Exception {

        client = new Client();

        client.getKryo().register(PacketMessage.class);

        client.start();

        client.connect(5000, ip, ipPort, ipPort);

        client.addListener(new ConsoleClient());

        while(!messageReceived) {

            Thread.sleep(3000);

        }

        if (!messageReceived) { return false; }

        running = true;

        while (running) {

            PacketMessage pm = new PacketMessage();
            pm.message = sc.nextLine();
            conn.sendTCP(pm);
        }
        return true;
    }

    public void received(Connection j, Object p) {

        if (p instanceof PacketMessage) {

            PacketMessage pm = (PacketMessage) p;

            if (messageReceived == false) {

                System.out.println("Successfully Connected to : "+j.getRemoteAddressTCP());
                conn = j;
            }

            else {

                System.out.println(pm.message);
            }

            messageReceived = true;
        }
        /*PacketMessage send = new PacketMessage();
        send.message = "LUL";
        j.sendTCP(send);*/

    }
}
