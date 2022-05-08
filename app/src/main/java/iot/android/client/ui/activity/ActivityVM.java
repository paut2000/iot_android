package iot.android.client.ui.activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import iot.android.client.App;
import iot.android.client.model.House;
import lombok.Getter;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityVM extends ViewModel {

    @Inject
    House house;

    @Getter
    private MutableLiveData<House> houseLiveData = new MutableLiveData<>();

    private final Timer regularHouseRefreshTimer = new Timer();

    public ActivityVM() {
        App.getViewModelComponent().inject(this);

        houseLiveData.setValue(house);

        regularHouseRefreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                house.refresh(() -> {
                    houseLiveData.postValue(house);
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onCleared() {
        regularHouseRefreshTimer.cancel();
        regularHouseRefreshTimer.purge();

        super.onCleared();
    }

}
