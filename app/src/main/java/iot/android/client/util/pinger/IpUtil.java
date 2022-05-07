package iot.android.client.util.pinger;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

public class IpUtil {

    public static byte[] getSubnet(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wm.getDhcpInfo();
        byte[] netmask = intIpToByteArrayIp(dhcpInfo.netmask);
        byte[] clientIp = intIpToByteArrayIp(dhcpInfo.ipAddress);
        return calculateSubnet(netmask, clientIp);
    }

    private static byte[] calculateSubnet(byte[] netmask, byte[] ip) {
        return new byte[] {
                (byte) (netmask[0] & ip[0]),
                (byte) (netmask[1] & ip[1]),
                (byte) (netmask[2] & ip[2]),
                (byte) (netmask[3] & ip[3]),
        };
    }

    private static byte[] intIpToByteArrayIp(int address) {
        return new byte[] {
                (byte) (address & 0xFF),
                (byte) ((address >>>= 8) & 0xFF),
                (byte) ((address >>>= 8) & 0xFF),
                (byte) ((address >>>= 8) & 0xFF)
        };
    }

    public static String byteArrayIpToStringIp(byte[] ip) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(ip[i] >= 0 ? ip[i] : ip[i] + 256).append('.');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
