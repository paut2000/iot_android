package iot.android.client.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import iot.android.client.R;
import iot.android.client.databinding.GroupViewBinding;
import iot.android.client.model.group.DeviceGroup;

public class GroupView extends LinearLayout {

    private final DeviceGroup group;

    private final LinearLayout deviceContainer;
    private final TextView groupName;

    public GroupView(@NonNull Context context, DeviceGroup group) {
        super(context);

        this.group = group;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.group_view, this, true);

        GroupViewBinding binding = GroupViewBinding.bind(this);
        deviceContainer = binding.devicesContainer;
        groupName = binding.groupName;

        layout();
        init(context);
    }

    private void init(Context context) {
        groupName.setText(group.getName());

        group.getDevices().forEach(device -> deviceContainer.addView(new DeviceView(context, device)));
    }

    private void layout() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.group_background);
        setPadding(10,10,10,10);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        int marginLeft = 15;
        int marginRight = 15;
        int marginTop = 15;
        int marginBottom = 15;

        params.setMargins(marginLeft,marginTop,marginRight,marginBottom);

        setLayoutParams(params);
    }

}
