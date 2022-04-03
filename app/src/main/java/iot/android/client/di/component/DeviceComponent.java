package iot.android.client.di.component;

import dagger.Component;
import iot.android.client.di.module.ApiModule;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.device.actuator.AbstractActuator;

import javax.inject.Singleton;

@Component(modules = {ApiModule.class})
@Singleton
public interface DeviceComponent {

    void inject(AbstractActuator abstractActuator);
    void inject(AbstractDevice abstractDevice);

}
