package iot.android.client.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.ScrollView;
import iot.android.client.R;
import iot.android.client.databinding.DevicesViewBinding;
import iot.android.client.model.device.AbstractDevice;

import java.util.Collection;

public class DevicesView extends ScrollView {

    private Collection<AbstractDevice> devices;

    private GridLayout devicesContainer;

    public DevicesView(Context context, Collection<AbstractDevice> devices) {
        super(context);

        this.devices = devices;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.devices_view, this, true);

        DevicesViewBinding binding = DevicesViewBinding.bind(this);
        devicesContainer = binding.devicesContainer;

        init();
    }

    private void init() {
        devicesContainer.removeAllViews();
        devices.forEach(device -> {
            devicesContainer.addView(new DeviceView(getContext(), device));
        });
    }

}
