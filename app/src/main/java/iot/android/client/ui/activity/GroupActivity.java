package iot.android.client.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import iot.android.client.databinding.ActivityGroupBinding;
import iot.android.client.model.group.DeviceGroup;
import iot.android.client.ui.view.device.DevicesView;

public class GroupActivity extends AppCompatActivity {

    private DeviceGroup group;

    private FrameLayout placeForDevicesContainer;
    private TextView noDevicesInGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGroupBinding binding = ActivityGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        placeForDevicesContainer = binding.placeForDevicesContainer;
        noDevicesInGroup = binding.noDevicesInGroup;

        group = (DeviceGroup) getIntent().getExtras().get("group");

        init();
    }

    private void init() {
        getSupportActionBar().setTitle(group.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (group.getDevices().isEmpty()) {
            noDevicesInGroup.setVisibility(View.VISIBLE);
            return;
        }

        placeForDevicesContainer.addView(new DevicesView(getApplicationContext(), group.getDevices()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}