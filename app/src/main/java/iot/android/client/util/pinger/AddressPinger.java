package iot.android.client.util.pinger;

import androidx.lifecycle.MutableLiveData;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class AddressPinger extends Thread {

    private final byte[] hostAddress;
    private final int port;
    private final int timeout;
    private final MutableLiveData<byte[]> liveData;

    public AddressPinger(byte[] hostAddress, int port, int timeout, MutableLiveData<byte[]> liveData) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.timeout = timeout;
        this.liveData = liveData;
    }

    @Override
    public void run() {
        if (isHostAvailable(hostAddress, port, timeout)){
            liveData.postValue(hostAddress);
        }
    }

    public boolean isHostAvailable(final byte[] hostAddress, final int port, final int timeout) {
        try (final Socket socket = new Socket()) {
            final InetAddress inetAddress = InetAddress.getByAddress(hostAddress);
            final InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, port);

            socket.connect(inetSocketAddress, timeout);
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }

}
