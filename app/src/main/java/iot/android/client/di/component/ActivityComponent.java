package iot.android.client.di.component;

import dagger.Component;
import iot.android.client.di.module.DaoModule;
import iot.android.client.di.module.HouseModule;
import iot.android.client.ui.activity.DeviceActivity;
import iot.android.client.ui.activity.GroupActivity;

import javax.inject.Singleton;

@Component(modules = {HouseModule.class, DaoModule.class})
@Singleton
public interface ActivityComponent {

    void inject(DeviceActivity deviceActivity);
    void inject(GroupActivity groupActivity);

}
