package iot.android.client.api.iapi;

import iot.android.client.api.message.DeviceDataSampleMessage;
import iot.android.client.model.device.data.AbstractData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.sql.Timestamp;

public interface IDataApi {

    @GET("/api/statistic/{serialNumber}")
    Call<DeviceDataSampleMessage> getSample(
            @Path("serialNumber") String serialNumber
    );

    @GET("/api/statistic/period/{serialNumber}")
    Call<DeviceDataSampleMessage> getSampleForPeriod(
            @Path("serialNumber") String serialNumber,
            @Query("start") Timestamp start,
            @Query("end") Timestamp end
            );

}
