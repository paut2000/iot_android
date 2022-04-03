package iot.android.client.ui.view.device.rgba;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import iot.android.client.R;
import iot.android.client.callback.OnSuccessCallback;
import iot.android.client.databinding.ColorPickerViewBinding;
import iot.android.client.model.device.actuator.RGBAStrip;
import iot.android.client.model.device.data.RGBAStripData;
import iot.android.client.ui.utils.ColorConverter;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

public class ColorPickerView extends ConstraintLayout {

    private final RGBAStrip strip;

    private final ColorPicker colorPicker;
    private final OpacityBar opacityBar;

    public ColorPickerView(@NonNull @NotNull Context context, RGBAStrip strip, OnSuccessCallback callback) {
        super(context);

        this.strip = strip;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.color_picker_view, this, true);

        ColorPickerViewBinding binding = ColorPickerViewBinding.bind(this);
        colorPicker = binding.colorPicker;
        opacityBar = binding.opacityBar;

        init(callback);
    }

    private void init(OnSuccessCallback callback) {
        colorPicker.addOpacityBar(opacityBar);
        colorPicker.setShowOldCenterColor(false);
        colorPicker.setColor(ColorConverter.RGBAtoInt(strip.getData()));

        //общий для opacity и picker
        colorPicker.setOnColorSelectedListener(color ->
                strip.changeRGBA(ColorConverter.IntToRGBA(color), callback));

        //общий для opacity и picker
        colorPicker.setOnColorChangedListener(color ->
                strip.changeRGBAWithOutSave(ColorConverter.IntToRGBA(color), new OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess();
                    }
                }));
    }

}
