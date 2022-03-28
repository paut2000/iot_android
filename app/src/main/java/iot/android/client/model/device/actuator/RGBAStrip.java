package iot.android.client.model.device.actuator;

import iot.android.client.callback.OnSuccessCallback;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.device.data.RGBAStripData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RGBAStrip extends AbstractActuator {

    public RGBAStrip() {
        this.data = new RGBAStripData();
    }

    @Override
    public RGBAStripData getData() {
        return (RGBAStripData) super.getData();
    }

    public void changeRGBA(RGBAStripData data, OnSuccessCallback onSuccessCallback) {
        actuatorApi.controlRgba(this.serialNumber, data).enqueue(
                (Callback<AbstractDevice>) this.<RGBAStrip>createSimpleCallBack(onSuccessCallback)
        );
    }

    public void changeRGBAWithOutSave(RGBAStripData data, OnSuccessCallback onSuccessCallback) {
        actuatorApi.controlRgbaNoSave(this.serialNumber, data).enqueue(
                (Callback<AbstractDevice>) this.<RGBAStrip>createSimpleCallBack(onSuccessCallback)
        );
    }

}
