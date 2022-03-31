package iot.android.client.di.component;

import dagger.Component;
import iot.android.client.dao.DeviceDao;
import iot.android.client.dao.GroupDao;
import iot.android.client.di.module.HouseModule;

import javax.inject.Singleton;

@Component(modules = {HouseModule.class})
@Singleton
public interface DaoComponent {

    void inject(GroupDao groupDao);

}
