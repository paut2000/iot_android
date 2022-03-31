package iot.android.client.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.databinding.ActivityDeviceBinding;
import iot.android.client.model.House;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.ui.factory.DeviceViewFactory;
import iot.android.client.ui.view.DeviceInfoView;

import javax.inject.Inject;
import java.io.IOException;

public class DeviceActivity extends AppCompatActivity {

    @Inject
    public House house;

    private AbstractDevice device;

    private FrameLayout placeForDeviceInfo;
    private FrameLayout placeForDevice;
    private FrameLayout placeForDeviceStatistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDeviceBinding binding = ActivityDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        placeForDeviceInfo = binding.placeForDeviceInfo;
        placeForDevice = binding.placeForDevice;
        placeForDeviceStatistic = binding.placeForDeviceStatistic;

        App.getActivityComponent().inject(this);

        String deviceSerialNumber = getIntent().getExtras().getString("serialNumber");
        try {
            device = house.getDevice(deviceSerialNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
    }

    private void init() {
        getSupportActionBar().setTitle(device.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        placeForDeviceInfo.addView(new DeviceInfoView(this, device));

        if (device.isAlive()) {
            placeForDevice.addView(DeviceViewFactory.createDeviceView(device, this));
        } else {
            placeForDevice.setVisibility(View.INVISIBLE);
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}