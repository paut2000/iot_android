package iot.android.client.ui.factory;

import android.content.Context;
import android.view.View;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.device.actuator.RGBAStrip;
import iot.android.client.model.device.actuator.Relay;
import iot.android.client.model.device.sensor.DHT;
import iot.android.client.ui.view.device.dht.DHTStatisticView;
import iot.android.client.ui.view.device.relay.RelayStatisticView;
import iot.android.client.ui.view.device.rgba.RGBAStripStatisticView;

public class StatisticViewFactory {

    public static View createStatisticView(AbstractDevice device, Context context) {
        switch (device.getType()) {
            case "Relay": {
                return new RelayStatisticView(context, (Relay) device);
            }
            case "RGBAStrip": {
                return new RGBAStripStatisticView(context, (RGBAStrip) device);
            }
            case "DHT": {
                return new DHTStatisticView(context, (DHT) device);
            }
        }
        throw new RuntimeException("Нет такого представления статистики");
    }

}
