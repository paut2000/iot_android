package iot.android.client.api.iapi;

import iot.android.client.model.device.data.AbstractData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.ArrayList;

public interface IDataApi {

    @GET("/api/data/{serialNumber}")
    Call<AbstractData> getData(
            @Path("serialNumber") String serialNumber
    );

    @GET("/api/statistic/{serialNumber}")
    Call<ArrayList<? extends AbstractData>> getSample(
            @Path("serialNumber") String serialNumber
    );

}
