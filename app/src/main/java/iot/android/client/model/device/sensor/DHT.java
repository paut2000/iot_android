package iot.android.client.model.device.sensor;

import iot.android.client.model.device.data.DHTData;

public class DHT extends AbstractSensor {

    public DHT() {
        this.data = new DHTData();
    }

    @Override
    public DHTData getData() {
        return (DHTData) super.getData();
    }

    @Override
    public void changeUpdateFrequency(Integer milliSeconds) {

    }
}
