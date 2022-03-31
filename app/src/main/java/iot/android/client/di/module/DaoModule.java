package iot.android.client.di.module;

import dagger.Module;
import dagger.Provides;
import iot.android.client.dao.DBHelper;
import iot.android.client.dao.DeviceDao;
import iot.android.client.dao.GroupDao;

import javax.inject.Singleton;

@Module
public class DaoModule {

    private final DBHelper dbHelper;

    public DaoModule(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Provides
    @Singleton
    public DeviceDao provideDeviceDao() {
        return new DeviceDao(dbHelper);
    }

    @Provides
    @Singleton
    public GroupDao provideGroupDao() {
        return new GroupDao(dbHelper);
    }

}
