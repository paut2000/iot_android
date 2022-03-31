package iot.android.client.di.component;

import dagger.Component;
import iot.android.client.dao.DeviceDao;
import iot.android.client.di.module.ApiModule;
import iot.android.client.di.module.DaoModule;
import iot.android.client.di.module.HouseModule;
import iot.android.client.model.House;

import javax.inject.Singleton;

@Component(modules = {ApiModule.class, DaoModule.class})
@Singleton
public interface HouseComponent {

    void inject(House house);

}
