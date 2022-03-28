package iot.android.client.model.device.actuator;

import iot.android.client.model.device.data.RelayData;

public class Relay extends AbstractActuator {

    public Relay() {
        this.data = new RelayData();
    }

    @Override
    public RelayData getData() {
        return (RelayData) super.getData();
    }

}
