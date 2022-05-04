package iot.android.client.ui.view.device.rgba;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import iot.android.client.R;
import iot.android.client.callback.OnSuccessCallback;
import iot.android.client.databinding.ColorPickerViewBinding;
import iot.android.client.model.device.actuator.RGBAStrip;
import iot.android.client.ui.utils.ColorConverter;
import iot.android.client.ui.utils.ColorUtils;
import org.jetbrains.annotations.NotNull;

public class ColorPickerView extends LinearLayout {

    private final RGBAStrip strip;

    private final ColorPicker colorPicker;
    private final OpacityBar opacityBar;
    private final TextView colorName;

    public ColorPickerView(@NonNull @NotNull Context context, RGBAStrip strip, OnSuccessCallback callback) {
        super(context);

        this.setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        this.strip = strip;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.color_picker_view, this, true);

        ColorPickerViewBinding binding = ColorPickerViewBinding.bind(this);
        colorPicker = binding.colorPicker;
        opacityBar = binding.opacityBar;
        colorName = binding.colorName;

        init(callback);
    }

    private void init(OnSuccessCallback callback) {
        colorPicker.addOpacityBar(opacityBar);
        colorPicker.setShowOldCenterColor(false);
        colorPicker.setColor(ColorConverter.RGBAtoInt(strip.getData()));
        colorName.setText(
                ColorUtils.getBestColorName(
                        Color.valueOf(
                            ColorConverter.RGBAtoInt(strip.getData())
                        )
                )
        );

        //общий для opacity и picker
        colorPicker.setOnColorSelectedListener(color -> {
            strip.changeRGBA(ColorConverter.IntToRGBA(color), callback);
        });

        //общий для opacity и picker
        colorPicker.setOnColorChangedListener(color -> {
            colorName.setText(ColorUtils.getBestColorName(Color.valueOf(color)));
            strip.changeRGBAWithOutSave(ColorConverter.IntToRGBA(color), callback);
        });
    }

}
