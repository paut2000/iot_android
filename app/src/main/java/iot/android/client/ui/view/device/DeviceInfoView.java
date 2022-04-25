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
import iot.android.client.model.device.AbstractDevice;

import javax.inject.Inject;

public class DeviceInfoView extends FrameLayout {

    private AbstractDevice device;

    @Inject
    DeviceDao deviceDao;

    private TextView serialNumberText;
    private TextView typeText;
    private TextView aliveText;
    private Button changeDeviceNameButton;

    private ActionBar actionBar;

    public DeviceInfoView(@NonNull Context context, AbstractDevice device, ActionBar actionBar) {
        super(context);

        this.device = device;
        this.actionBar = actionBar;

        App.getViewComponent().inject(this);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.device_info_view, this, true);

        DeviceInfoViewBinding binding = DeviceInfoViewBinding.bind(this);
        serialNumberText = binding.serialNumberText;
        typeText = binding.typeText;
        aliveText = binding.aliveText;
        changeDeviceNameButton = binding.changeNameButton;

        init();
    }

    private void init() {
        serialNumberText.setText(device.getSerialNumber());
        typeText.setText(device.getType());
        aliveText.setText(device.isAlive() ? "Подключен" : "Отключен");

        changeDeviceNameButton.setOnClickListener(view -> {
            createChangeNameDialog().show();
        });
    }

    private AlertDialog createChangeNameDialog() {
        EditText editText = new EditText(getContext());
        return new AlertDialog.Builder(getContext())
                .setTitle("Введите новое имя")
                .setView(editText)
                .setPositiveButton("Переименовать", (dialogInterface, i) -> {
                    String newName = editText.getText().toString();
                    device.setName(newName);
                    deviceDao.update(device);
                    actionBar.setTitle(newName);
                })
                .create();
    }

}