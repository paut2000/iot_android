package iot.android.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iot.android.client.App;
import iot.android.client.api.iapi.IHouseApi;
import iot.android.client.callback.AbstractCallbackDefaultOnFailure;
import iot.android.client.callback.OnSuccessCallback;
import iot.android.client.dao.DeviceDao;
import iot.android.client.model.device.AbstractDevice;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

@Getter
@Setter
public class House {

    private Map<String, AbstractDevice> devices;

    @Inject
    IHouseApi houseApi;

    @Inject
    DeviceDao deviceDao;

    public House() {
        App.getHouseComponent().inject(this);
        refresh(()->{});
    }

    @JsonIgnore
    public AbstractDevice getDevice(String serialNumber) throws IOException {
        AbstractDevice device = this.devices.get(serialNumber);
        if (device == null) throw new IOException("Девайс " + serialNumber);
        return device;
    }

    public void refresh(OnSuccessCallback onSuccessCallback) {
        houseApi.getHouse().enqueue(new AbstractCallbackDefaultOnFailure<House>() {
            @Override
            public void onResponse(Call<House> call, Response<House> response) {
                if (response.isSuccessful()) {
                    devices = response.body().getDevices();
                    devices.forEach((s, device) -> deviceDao.synchronizeOrInsert(device));
                    onSuccessCallback.onSuccess();
                }
            }
        });
    }

    public void deleteDevice(AbstractDevice device, OnSuccessCallback onSuccessCallback) {
        houseApi.removeDevice(device.getSerialNumber()).enqueue(new AbstractCallbackDefaultOnFailure<House>() {
            @Override
            public void onResponse(Call<House> call, Response<House> response) {
                if (response.isSuccessful()) {
                    devices.remove(device.getSerialNumber());
                    onSuccessCallback.onSuccess();
                }
            }
        });
    }

}
