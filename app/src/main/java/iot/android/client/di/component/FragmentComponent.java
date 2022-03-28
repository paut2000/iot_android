package iot.android.client.di.component;

import dagger.Component;
import iot.android.client.di.module.HouseModule;
import iot.android.client.ui.fragment.HomeFragment;

import javax.inject.Singleton;

@Component(modules = {HouseModule.class})
@Singleton
public interface FragmentComponent {

    void inject(HomeFragment homeFragment);

}
