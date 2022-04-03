package iot.android.client.di.component;

import dagger.Component;
import iot.android.client.di.module.DaoModule;
import iot.android.client.ui.view.device.DeviceInfoView;

import javax.inject.Singleton;

@Component(modules = {DaoModule.class})
@Singleton
public interface ViewComponent {

    void inject(DeviceInfoView deviceInfoView);

}
