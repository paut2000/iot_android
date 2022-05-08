package iot.android.client.di.component;

import dagger.Component;
import iot.android.client.di.module.HouseModule;
import iot.android.client.ui.activity.ActivityVM;
import iot.android.client.ui.fragment.HomeFragmentVM;

import javax.inject.Singleton;

@Component(modules = {HouseModule.class})
@Singleton
public interface ViewModelComponent {

    void inject(HomeFragmentVM homeFragmentVM);
    void inject(ActivityVM activityVM);

}
