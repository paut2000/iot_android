package iot.android.client.model.device.actuator;

import iot.android.client.App;
import iot.android.client.api.iapi.IActuatorApi;
import iot.android.client.callback.AbstractCallbackDefaultOnFailure;
import iot.android.client.callback.OnSuccessCallback;
import iot.android.client.model.device.AbstractDevice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

public abstract class AbstractActuator extends AbstractDevice {

    @Inject
    IActuatorApi actuatorApi;

    public AbstractActuator() {
        App.getDeviceComponent().inject(this);
    }

    public void enable(OnSuccessCallback onSuccessCallback) {
        actuatorApi.enable(serialNumber).enqueue(
                (Callback<AbstractDevice>) this.<AbstractDevice>createSimpleCallBack(onSuccessCallback)
        );
    }

    public void disable(OnSuccessCallback onSuccessCallback) {
        actuatorApi.disable(serialNumber).enqueue(
                (Callback<AbstractDevice>) this.<AbstractDevice>createSimpleCallBack(onSuccessCallback)
        );
    }

    protected <T extends AbstractDevice> Callback<? extends AbstractDevice> createSimpleCallBack(OnSuccessCallback onSuccessCallback) {
        return new AbstractCallbackDefaultOnFailure<AbstractDevice>() {
            @Override
            public void onResponse(Call<AbstractDevice> call, Response<AbstractDevice> response) {
                if (response.isSuccessful()) {
                    data = response.body().getData();
                    onSuccessCallback.onSuccess();
                }
            }
        };
    }

}
