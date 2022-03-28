package iot.android.client.api.iapi;

import iot.android.client.model.House;
import retrofit2.Call;
import retrofit2.http.*;

public interface IHouseApi {

    @GET("/api/house")
    Call<House> getHouse();

}
