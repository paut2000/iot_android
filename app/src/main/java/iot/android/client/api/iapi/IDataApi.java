package iot.android.client.api.iapi;

import iot.android.client.api.message.DeviceDataSampleMessage;
import iot.android.client.model.device.data.AbstractData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDataApi {

    @GET("/api/statistic/{serialNumber}")
    Call<DeviceDataSampleMessage> getSample(
            @Path("serialNumber") String serialNumber
    );

}
