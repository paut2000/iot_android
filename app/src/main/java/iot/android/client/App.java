package iot.android.client;

import android.app.Application;
import iot.android.client.di.component.*;
import iot.android.client.di.module.ApiModule;
import iot.android.client.di.module.HouseModule;
import lombok.Getter;

public class App extends Application {

    @Getter
    private static DeviceComponent deviceComponent;

    @Getter
    private static HouseComponent houseComponent;

    @Getter
    private static FragmentComponent fragmentComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiModule apiModule = new ApiModule();
        deviceComponent = DaggerDeviceComponent.builder().apiModule(apiModule).build();
        houseComponent = DaggerHouseComponent.builder().apiModule(apiModule).build();
        HouseModule houseModule = new HouseModule();
        fragmentComponent = DaggerFragmentComponent.builder().houseModule(houseModule).build();
    }

}
