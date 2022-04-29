package iot.android.client.model.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import iot.android.client.App;
import iot.android.client.api.iapi.IDataApi;
import iot.android.client.api.message.DeviceDataSampleMessage;
import iot.android.client.callback.AbstractCallbackDefaultOnFailure;
import iot.android.client.callback.OnGetDataCallback;
import iot.android.client.callback.OnSuccessCallback;
import iot.android.client.model.device.data.AbstractData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;

@Getter
@Setter
public abstract class AbstractDevice {

    @Inject
    IDataApi dataApi;

    protected String serialNumber;

    protected String type;

    protected boolean alive;

    protected AbstractData data;

    @JsonIgnore
    protected String name = "Default name";

    public AbstractDevice() {
        App.getDeviceComponent().inject(this);
    }

    @JsonIgnore
    public void requestSample(OnGetDataCallback onGetDataCallback) {
        dataApi.getSample(serialNumber).enqueue(new AbstractCallbackDefaultOnFailure<DeviceDataSampleMessage>() {
            @Override
            public void onResponse(Call<DeviceDataSampleMessage> call, Response<DeviceDataSampleMessage> response) {
                if (response.isSuccessful()) {
                    onGetDataCallback.onGetData(response.body());
                }
            }
        });
    }

    @JsonIgnore
    public void requestSampleForPeriod(Timestamp start, Timestamp end, OnGetDataCallback onGetDataCallback) {
        dataApi.getSampleForPeriod(serialNumber, start, end).enqueue(new AbstractCallbackDefaultOnFailure<DeviceDataSampleMessage>() {
            @Override
            public void onResponse(Call<DeviceDataSampleMessage> call, Response<DeviceDataSampleMessage> response) {
                if (response.isSuccessful()) {
                    onGetDataCallback.onGetData(response.body());
                }
            }
        });
    }

}
