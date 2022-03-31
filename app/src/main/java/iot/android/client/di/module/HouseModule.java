package iot.android.client.di.module;

import dagger.Module;
import dagger.Provides;
import iot.android.client.dao.DBHelper;
import iot.android.client.dao.DeviceDao;
import iot.android.client.model.House;

import javax.inject.Singleton;

@Module
public class HouseModule {

    @Provides
    @Singleton
    public House provideHouse() {
        return new House();
    }

}
