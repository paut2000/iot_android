package iot.android.client.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.dao.DeviceDao;
import iot.android.client.databinding.ActivityDeviceBinding;
import iot.android.client.model.House;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.device.data.DHTData;
import iot.android.client.model.device.sensor.AbstractSensor;
import iot.android.client.model.device.sensor.DHT;
import iot.android.client.ui.factory.DeviceViewFactory;
import iot.android.client.ui.factory.StatisticViewFactory;
import iot.android.client.ui.view.device.DeviceInfoView;
import iot.android.client.ui.view.device.dht.DHTStatisticView;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class DeviceActivity extends AppCompatActivity {

    private ActivityVM viewModel;
    private AbstractDevice device;

    @Inject
    DeviceDao deviceDao;

    private String deviceSerialNumber;

    private FrameLayout placeForDeviceInfo;
    private FrameLayout placeForDevice;
    private FrameLayout placeForDeviceStatistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ActivityVM.class);

        ActivityDeviceBinding binding = ActivityDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        placeForDeviceInfo = binding.placeForDeviceInfo;
        placeForDevice = binding.placeForDevice;
        placeForDeviceStatistic = binding.placeForDeviceStatistic;

        App.getActivityComponent().inject(this);

        deviceSerialNumber = getIntent().getExtras().getString("serialNumber");

        viewModel.getHouseLiveData().observe(this, house -> {
            try {
                AbstractDevice device = viewModel.getHouseLiveData().getValue().getDevice(deviceSerialNumber);
                getSupportActionBar().setTitle(device.getName());
                fillPlaceForDeviceInfo(device);
                fillPlaceForDevice(device);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            device = viewModel.getHouseLiveData().getValue().getDevice(deviceSerialNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        init(device);
    }

    private void init(AbstractDevice device) {
        fillPlaceForDeviceStatistic(device);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_activity_three_dots_menu, menu);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.rename_device: {
                createChangeNameDialog().show();
                break;
            }
            case R.id.delete_device: {
                createDeleteDeviceDialog(this).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillPlaceForDeviceStatistic(AbstractDevice device) {
        placeForDeviceStatistic.removeAllViews();
        placeForDeviceStatistic.addView(
                StatisticViewFactory.createStatisticView(device, this)
        );
    }

    private void fillPlaceForDeviceInfo(AbstractDevice device) {
        placeForDeviceInfo.removeAllViews();
        placeForDeviceInfo.addView(new DeviceInfoView(this, device));
    }

    private void fillPlaceForDevice(AbstractDevice device) {
        placeForDevice.removeAllViews();

        if (device.isAlive() || device instanceof AbstractSensor) {
            placeForDevice.addView(DeviceViewFactory.createDeviceView(device, this));
        } else {
            placeForDevice.setVisibility(View.INVISIBLE);
        }
    }

    private AlertDialog createChangeNameDialog() {
        EditText editText = new EditText(this);
        editText.setText(device.getName());
        return new AlertDialog.Builder(this)
                .setTitle("Введите новое имя")
                .setView(editText)
                .setPositiveButton("Переименовать", (dialogInterface, i) -> {
                    String newName = editText.getText().toString();
                    device.setName(newName);
                    deviceDao.update(device);
                    getSupportActionBar().setTitle(newName);
                })
                .create();
    }

    private AlertDialog createDeleteDeviceDialog(Context context) {
        return new AlertDialog.Builder(context)
                .setTitle("Вы действительно хотите удалить данное устройство?")
                .setNegativeButton("Нет", (dialogInterface, i) -> {})
                .setPositiveButton("Да", (dialogInterface, i) -> {
                    viewModel.getHouseLiveData().getValue().deleteDevice(device, ()->{});
                    finish();
                })
                .create();
    }

}