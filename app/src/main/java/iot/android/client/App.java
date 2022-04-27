package iot.android.client;

import android.app.Application;
import iot.android.client.dao.DBHelper;
import iot.android.client.di.component.*;
import iot.android.client.di.module.ApiModule;
import iot.android.client.di.module.DaoModule;
import iot.android.client.di.module.HouseModule;
import iot.android.client.model.House;
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

    @Getter
    private static ActivityComponent activityComponent;

    @Getter
    private static ViewComponent viewComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        ApiModule apiModule = new ApiModule();
        DaoModule daoModule = new DaoModule(new DBHelper(getBaseContext()));
        deviceComponent = DaggerDeviceComponent.builder()
                .apiModule(apiModule)
                .build();
        houseComponent = DaggerHouseComponent.builder()
                .apiModule(apiModule)
                .daoModule(daoModule)
                .build();

        // Посылаю House через конструктор потому что даггер по непонятным мне причинам внедряет разные
        // экземпляры, хотя должен быть синглтон (во фрагменты и дао внедряется один и тот же экзмемпляр,
        // но в DeviceActivity внедряется новый экзмемпляр)
        HouseModule houseModule = new HouseModule(new House());
        fragmentComponent = DaggerFragmentComponent.builder()
                .houseModule(houseModule)
                .daoModule(daoModule)
                .build();
        daoComponent = DaggerDaoComponent.builder()
                .houseModule(houseModule)
                .build();
        activityComponent = DaggerActivityComponent.builder()
                .houseModule(houseModule)
                .build();
        viewComponent = DaggerViewComponent.builder()
                .houseModule(houseModule)
                .daoModule(daoModule)
                .build();
    }

}
