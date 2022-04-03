package iot.android.client.ui.factory;

import android.content.Context;
import android.view.View;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.device.actuator.RGBAStrip;
import iot.android.client.model.device.actuator.Relay;
import iot.android.client.model.device.sensor.DHT;
import iot.android.client.ui.view.device.dht.DHTView;
import iot.android.client.ui.view.device.rgba.RGBAStripView;
import iot.android.client.ui.view.device.relay.RelayView;

public class DeviceViewFactory {

    public static View createDeviceView(AbstractDevice device, Context context) {
        switch (device.getType()) {
            case "Relay": {
                return new RelayView(context, (Relay) device);
            }
            case "RGBAStrip": {
                return new RGBAStripView(context, (RGBAStrip) device);
            }
            case "DHT": {
                return new DHTView(context, (DHT) device);
            }
        }
        throw new RuntimeException("Нет такого представления девайса");
    }

}
