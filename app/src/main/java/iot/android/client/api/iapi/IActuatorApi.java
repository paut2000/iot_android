package iot.android.client.api.iapi;

import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.device.actuator.RGBAStrip;
import iot.android.client.model.device.data.RGBAStripData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IActuatorApi {

    @POST("/api/control/actuator/enable/{serialNumber}")
    Call<AbstractDevice> enable(
            @Path("serialNumber") String serialNumber
    );

    @POST("/api/control/actuator/disable/{serialNumber}")
    Call<AbstractDevice> disable(
            @Path("serialNumber") String serialNumber
    );

    @POST("/api/control/rgba/{serialNumber}")
    Call<AbstractDevice> controlRgba(
            @Path("serialNumber") String serialNumber,
            @Body RGBAStripData data
    );

    @POST("/api/control/rgba/nosave/{serialNumber}")
    Call<AbstractDevice> controlRgbaNoSave(
            @Path("serialNumber") String serialNumber,
            @Body RGBAStripData data
    );

}
