package iot.android.client.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import iot.android.client.App;
import iot.android.client.R;
import iot.android.client.dao.GroupDao;
import iot.android.client.databinding.ActivityGroupBinding;
import iot.android.client.model.device.actuator.AbstractActuator;
import iot.android.client.model.group.DeviceGroup;
import iot.android.client.ui.view.device.DevicesView;

import javax.inject.Inject;

public class GroupActivity extends AppCompatActivity {

    private Long groupId;
    private DeviceGroup group;

    @Inject
    GroupDao groupDao;

    private ActivityVM viewModel;

    private FrameLayout placeForDevicesContainer;
    private TextView noDevicesInGroup;
    private Button enableButton;
    private Button disableButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGroupBinding binding = ActivityGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ActivityVM.class);

        App.getActivityComponent().inject(this);

        placeForDevicesContainer = binding.placeForDevicesContainer;
        noDevicesInGroup = binding.noDevicesInGroup;
        enableButton = binding.enableAllButton;
        disableButton = binding.disableAllButton;
        progressBar = binding.progressBar;

        groupId = (Long) getIntent().getExtras().get("group_id");

        viewModel.getHouseLiveData().observe(this, house -> {
            init();
        });
    }

    private void init() {
        group = groupDao.readById(groupId);

        getSupportActionBar().setTitle(group.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        enableButton.setOnClickListener(view -> {
            setInProgress(true);
            group.getDevices().forEach(device -> {
                if (device.isAlive() && device instanceof AbstractActuator) {
                    ((AbstractActuator) device).enable(() -> {
                        fillDevicesContainer();
                    });
                }
            });
        });

        disableButton.setOnClickListener(view -> {
            setInProgress(true);
            group.getDevices().forEach(device -> {
                if (device.isAlive() && device instanceof AbstractActuator) {
                    ((AbstractActuator) device).disable(() -> {
                        fillDevicesContainer();
                    });
                }
            });
        });

        fillDevicesContainer();
    }

    private void setInProgress(boolean b) {
        enableButton.setEnabled(!b);
        disableButton.setEnabled(!b);
        placeForDevicesContainer.setEnabled(!b);
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void fillDevicesContainer() {
        setInProgress(true);

        if (group.getDevices().isEmpty()) {
            noDevicesInGroup.setVisibility(View.VISIBLE);
            setInProgress(false);
            return;
        } else {
            noDevicesInGroup.setVisibility(View.INVISIBLE);
        }

        placeForDevicesContainer.removeAllViews();
        placeForDevicesContainer.addView(new DevicesView(this, group.getDevices()));
        setInProgress(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_activity_three_dots_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.change_group_structure: {
                createAddDevicesDialog().show();
                break;
            }
            case R.id.rename_group: {
                createRenameDialog().show();
                break;
            }
            case R.id.delete_group: {
                createDeleteGroupDialog().show();
                break;
            }
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
                    finish();
                })
                .create();
    }

    private AlertDialog createAddDevicesDialog() {
        LinearLayout deviceCheckBoxesContainer = new LinearLayout(this);
        deviceCheckBoxesContainer.setOrientation(LinearLayout.VERTICAL);
        deviceCheckBoxesContainer.setGravity(Gravity.CENTER);

        viewModel.getHouseLiveData().getValue().getDevices().forEach((serialNumber, device) -> {
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