package iot.android.client.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import iot.android.client.R;
import iot.android.client.databinding.GroupViewBinding;
import iot.android.client.model.device.actuator.AbstractActuator;
import iot.android.client.model.group.DeviceGroup;
import iot.android.client.ui.activity.GroupActivity;

public class GroupView extends LinearLayout {

    private final DeviceGroup group;
    
    private final TextView groupName;
    private final Button enableAllButton;
    private final Button disableAllButton;
    private final ProgressBar progressBar;

    public GroupView(@NonNull Context context, DeviceGroup group) {
        super(context);

        this.group = group;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.group_view, this, true);

        GroupViewBinding binding = GroupViewBinding.bind(this);
        groupName = binding.groupName;
        disableAllButton = binding.disableAllButton;
        enableAllButton = binding.enableAllButton;
        progressBar = binding.progressBar;

        layout();
        init();
    }

    private void init() {
        groupName.setText(group.getName());

        enableAllButton.setOnClickListener(view -> {
            setInProgress(true);

            group.getDevices().forEach(device -> {
                if (device.isAlive() && device instanceof AbstractActuator) {
                    ((AbstractActuator) device).enable(() -> {
                        setInProgress(false);
                    });
                }
            });
        });

        disableAllButton.setOnClickListener(view -> {
            setInProgress(true);

            group.getDevices().forEach(device -> {
                if (device.isAlive() && device instanceof AbstractActuator) {
                    ((AbstractActuator) device).disable(() -> {
                        setInProgress(false);
                    });
                }
            });
        });

        setOnLongClickListener(view -> {
            Activity activity = (Activity) view.getContext();
            Intent intent = new Intent(activity, GroupActivity.class);

            activity.startActivity(intent);
            return false;
        });

    }

    private void setInProgress(boolean b) {
        if (b) {
            enableAllButton.setEnabled(false);
            disableAllButton.setEnabled(false);
            progressBar.setVisibility(VISIBLE);
        } else {
            enableAllButton.setEnabled(true);
            disableAllButton.setEnabled(true);
            progressBar.setVisibility(INVISIBLE);
        }
    }

    private void layout() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.group_background);
        setPadding(10,10,10,10);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        int marginLeft = 15;
        int marginRight = 15;
        int marginTop = 15;
        int marginBottom = 15;

        params.setMargins(marginLeft,marginTop,marginRight,marginBottom);

        setLayoutParams(params);
    }

}
