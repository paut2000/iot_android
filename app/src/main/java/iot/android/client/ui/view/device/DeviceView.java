package iot.android.client.ui.view.device;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import iot.android.client.R;
import iot.android.client.databinding.DeviceViewBinding;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.device.sensor.AbstractSensor;
import iot.android.client.ui.activity.DeviceActivity;
import iot.android.client.ui.activity.GroupActivity;
import iot.android.client.ui.factory.DeviceViewFactory;
import org.jetbrains.annotations.NotNull;

public class DeviceView extends ConstraintLayout {

    protected final AbstractDevice device;

    private final TextView name;
    private final TextView errorView;
    private final FrameLayout deviceContainer;

    public DeviceView(@NonNull @NotNull Context context, AbstractDevice device) {
        super(context);
        this.device = device;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.device_view, this, true);

        DeviceViewBinding binding = DeviceViewBinding.bind(this);
        name = binding.name;
        errorView = binding.errorView;
        deviceContainer = binding.device;

        init(context);

        layout(context);
    }

    private void init(Context context) {
        name.setText(device.getName());

        setOnClickListener(view -> {
            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, DeviceActivity.class);
            intent.putExtra("serialNumber", device.getSerialNumber());

            activity.startActivity(intent);
        });

        if(!device.isAlive() && !(device instanceof AbstractSensor)) {
            deviceContainer.setVisibility(INVISIBLE);
            errorView.setVisibility(VISIBLE);
            return;
        }

        deviceContainer.addView(DeviceViewFactory.createDeviceView(device, context));
    }

    private void layout(Context context) {
        setBackgroundResource(R.drawable.device_background);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        GridLayout.LayoutParams params =new GridLayout.LayoutParams();
        params.setGravity(Gravity.FILL);

        int marginLeft = 15;
        int marginRight = 15;
        int marginTop = 15;
        int marginBottom = 15;

        params.setMargins(marginLeft,marginTop,marginRight,marginBottom);

        params.height = height / 3;
        params.width = width / 2 - (marginLeft + marginRight);

        setLayoutParams(params);

        setPadding(10,10,10,10);
    }

}
