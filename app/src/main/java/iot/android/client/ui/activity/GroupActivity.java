package iot.android.client.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import iot.android.client.App;
import iot.android.client.dao.GroupDao;
import iot.android.client.databinding.ActivityGroupBinding;
import iot.android.client.model.House;
import iot.android.client.model.group.DeviceGroup;
import iot.android.client.ui.view.device.DevicesView;

import javax.inject.Inject;

public class GroupActivity extends AppCompatActivity {

    private DeviceGroup group;

    @Inject
    GroupDao groupDao;

    @Inject
    House house;

    private FrameLayout placeForDevicesContainer;
    private TextView noDevicesInGroup;
    private Button addDeviceButton;
    private Button renameGroupButton;
    private Button deleteGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGroupBinding binding = ActivityGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.getActivityComponent().inject(this);

        placeForDevicesContainer = binding.placeForDevicesContainer;
        noDevicesInGroup = binding.noDevicesInGroup;
        addDeviceButton = binding.addDeviceButton;
        renameGroupButton = binding.renameGroupButton;
        deleteGroupButton = binding.deleteGroupButton;

        Long id = (Long) getIntent().getExtras().get("group_id");
        group = groupDao.readById(id);

        init();
    }

    private void init() {
        getSupportActionBar().setTitle(group.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        renameGroupButton.setOnClickListener(view -> {
            createRenameDialog().show();
        });

        deleteGroupButton.setOnClickListener(view -> {
            createDeleteGroupDialog().show();
        });

        addDeviceButton.setOnClickListener(view -> {
            createAddDevicesDialog().show();
        });

        if (group.getDevices().isEmpty()) {
            noDevicesInGroup.setVisibility(View.VISIBLE);
            return;
        }

        fillDevicesContainer();
    }

    private void fillDevicesContainer() {
        placeForDevicesContainer.removeAllViews();
        placeForDevicesContainer.addView(new DevicesView(this, group.getDevices()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private AlertDialog createRenameDialog() {
        EditText editText = new EditText(this);
        editText.setText(group.getName());

        return new AlertDialog.Builder(this)
                .setTitle("Введите новое имя")
                .setView(editText)
                .setPositiveButton("Переименовать", (dialogInterface, i) -> {
                    String newName = editText.getText().toString();
                    group.setName(newName);
                    groupDao.updateGroupName(group);
                    getSupportActionBar().setTitle(newName);
                })
                .create();
    }

    private AlertDialog createDeleteGroupDialog() {
        return new AlertDialog.Builder(this)
                .setTitle("Вы действительно хотите удалить данную группу?")
                .setNegativeButton("Нет", (dialogInterface, i) -> {})
                .setPositiveButton("Да", (dialogInterface, i) -> {
                    groupDao.deleteGroup(group);
                })
                .create();
    }

    private AlertDialog createAddDevicesDialog() {
        LinearLayout deviceCheckBoxesContainer = new LinearLayout(this);
        deviceCheckBoxesContainer.setOrientation(LinearLayout.VERTICAL);
        deviceCheckBoxesContainer.setGravity(Gravity.CENTER);

        house.getDevices().forEach((serialNumber, device) -> {
            CheckBox deviceCheckBox = new CheckBox(this);
            deviceCheckBox.setText(device.getName() + " : " + device.getSerialNumber());
            deviceCheckBox.setChecked(group.getDevices().contains(device));
            deviceCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b == true) {
                    groupDao.addDeviceToGroup(group.getId(), serialNumber);
                } else {
                    groupDao.deleteDeviceFromGroup(group.getId(), serialNumber);
                }
                groupDao.synchronizeGroupsLinksToDevices(group);
            });

            deviceCheckBoxesContainer.addView(deviceCheckBox);
        });

        return new AlertDialog.Builder(this)
                .setTitle("Выберите нужные устройства")
                .setView(deviceCheckBoxesContainer)
                .setPositiveButton("Сохранить", (dialogInterface, i) -> {
                    fillDevicesContainer();
                })
                .create();
    }

}