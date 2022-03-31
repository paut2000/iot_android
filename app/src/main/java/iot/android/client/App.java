package iot.android.client;

import android.app.Application;
import iot.android.client.dao.DBHelper;
import iot.android.client.di.component.*;
import iot.android.client.di.module.ApiModule;
import iot.android.client.di.module.DaoModule;
import iot.android.client.di.module.HouseModule;
import lombok.Getter;

public class App extends Application {

    @Getter
    private static DeviceComponent deviceComponent;

    @Getter
    private static HouseComponent houseComponent;

    @Getter
    private static FragmentComponent fragmentComponent;

    @Getter
    private static DaoComponent daoComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        ApiModule apiModule = new ApiModule();
        HouseModule houseModule = new HouseModule();
        DaoModule daoModule = new DaoModule(new DBHelper(getBaseContext()));
        deviceComponent = DaggerDeviceComponent.builder()
                .apiModule(apiModule)
                .build();
        houseComponent = DaggerHouseComponent.builder()
                .apiModule(apiModule)
                .daoModule(daoModule)
                .build();
        fragmentComponent = DaggerFragmentComponent.builder()
                .houseModule(houseModule)
                .daoModule(daoModule)
                .build();
        daoComponent = DaggerDaoComponent.builder()
                .houseModule(houseModule)
                .build();
    }

}
