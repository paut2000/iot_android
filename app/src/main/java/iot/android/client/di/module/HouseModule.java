package iot.android.client.di.module;

import dagger.Module;
import dagger.Provides;
import iot.android.client.dao.DBHelper;
import iot.android.client.dao.DeviceDao;
import iot.android.client.model.House;

import javax.inject.Singleton;

@Module
public class HouseModule {

    private House house;

    public HouseModule(House house) {
        this.house = house;
    }

    @Provides
    @Singleton
    public House provideHouse() {
        return house;
    }

}
