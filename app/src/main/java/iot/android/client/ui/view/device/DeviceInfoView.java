package iot.android.client.ui.view.device;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.dao.DeviceDao;
import iot.android.client.databinding.DeviceInfoViewBinding;
import iot.android.client.model.House;
import iot.android.client.model.device.AbstractDevice;

import javax.inject.Inject;

public class DeviceInfoView extends FrameLayout {

    private AbstractDevice device;

    private TextView serialNumberText;
    private TextView typeText;
    private TextView aliveText;

    public DeviceInfoView(@NonNull Context context, AbstractDevice device) {
        super(context);

        this.device = device;

        App.getViewComponent().inject(this);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.device_info_view, this, true);

        DeviceInfoViewBinding binding = DeviceInfoViewBinding.bind(this);
        serialNumberText = binding.serialNumberText;
        typeText = binding.typeText;
        aliveText = binding.aliveText;

        init();
    }

    private void init() {
        serialNumberText.setText(device.getSerialNumber());
        typeText.setText(device.getType());
        aliveText.setText(device.isAlive() ? "Подключен" : "Отключен");
    }

}
