package Utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

// TODO: See if we can fix this.
public class RobotNetwork {
    public static String getLocalHost() {
        Enumeration<NetworkInterface> ni_enumeration = null;
        try {
             ni_enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (ni_enumeration.hasMoreElements()) {

            int i = 0;

            NetworkInterface networkInterface = ni_enumeration.nextElement();
            Enumeration<InetAddress> inet_enumeration = networkInterface.getInetAddresses();
            while (inet_enumeration.hasMoreElements()) {
                if (i == 0) {
                    //i++;
                    //continue;
                }
                InetAddress address = inet_enumeration.nextElement();
                System.out.println(address);
                // return address.getHostAddress();
            }
        }
        return null;
    }
}
