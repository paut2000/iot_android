package iot.android.client.ui.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import iot.android.client.R;
import iot.android.client.callback.OnSuccessCallback;
import iot.android.client.databinding.RgbaViewBinding;
import iot.android.client.model.device.actuator.RGBAStrip;
import iot.android.client.model.device.data.RGBAStripData;
import iot.android.client.ui.utils.ColorConverter;
import org.jetbrains.annotations.NotNull;

public class RGBAStripView  extends ConstraintLayout {

    private final RGBAStrip strip;

    private final TextView name;
    private final Button colorPickerButton;
    private final Button onButton;
    private final Button offButton;
    private final ImageView colorView;
    private final ProgressBar progressBar;
    private final TextView errorView;

    public RGBAStripView(@NonNull @NotNull Context context, RGBAStrip strip) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.rgba_view, this, true);

        this.strip = strip;

        RgbaViewBinding binding = RgbaViewBinding.bind(this);
        name = binding.name;
        onButton = binding.onButton;
        offButton = binding.offButton;
        colorPickerButton = binding.colorPickerButton;
        colorView = binding.colorView;
        progressBar = binding.progressBar;
        errorView = binding.errorView;

        init(context);
    }

    private void init(Context context) {
        name.setText(strip.getSerialNumber());

        // Заблокировать всё, если девайс мёртв
        if (!strip.isAlive()) {
            errorView.setVisibility(VISIBLE);
            colorPickerButton.setVisibility(INVISIBLE);
            onButton.setVisibility(INVISIBLE);
            offButton.setVisibility(INVISIBLE);
            colorView.setVisibility(INVISIBLE);
            setColorViewColor(strip.getData());
            return;
        }

        refreshStatesOfAll();

        colorPickerButton.setOnClickListener(view -> createColorPickerDialog(context).show());

        onButton.setOnClickListener(view -> {
            progressBar.setVisibility(VISIBLE);
            disableAll();
            strip.enable(() -> {
                enableAll();
                onButton.setEnabled(false);
                setColorViewColor(strip.getData());
                progressBar.setVisibility(INVISIBLE);
            });
        });

        offButton.setOnClickListener(view -> {
            progressBar.setVisibility(VISIBLE);
            disableAll();
            strip.disable(() -> {
                enableAll();
                offButton.setEnabled(false);
                setColorViewColor(strip.getData());
                progressBar.setVisibility(INVISIBLE);
            });
        });
    }

    private void disableAll() {
        changeEnabledAll(false);
    }

    private void enableAll() {
        changeEnabledAll(true);
    }

    private void changeEnabledAll(Boolean isEnable) {
        onButton.setEnabled(isEnable);
        offButton.setEnabled(isEnable);
        colorPickerButton.setEnabled(isEnable);
    }

    private void setColorViewColor(RGBAStripData data) {
        ((GradientDrawable) colorView.getBackground()).setColor(ColorConverter.RGBAtoInt(data));
    }

    private void refreshStatesOfAll() {
        // Задание цвета фигуры
        setColorViewColor(strip.getData());

        // Заблокировать кнопку выключения, если выключено
        if (strip.getData().getAlfa() == 0) {
            offButton.setEnabled(false);
        } else {
            offButton.setEnabled(true);
        }

        // Заблокировать кнопку включения, если включено
        if (strip.getData().getAlfa() == 255 &&
                strip.getData().getRed() == 255 &&
                strip.getData().getGreen() == 255 &&
                strip.getData().getBlue() == 255) {
            onButton.setEnabled(false);
        } else {
            onButton.setEnabled(true);
        }
    }

    private AlertDialog createColorPickerDialog(Context context) {
        return new AlertDialog.Builder(context)
                .setView(new ColorPickerView(context, strip, this::refreshStatesOfAll))
                .setTitle("Выберите цвет")
                .setPositiveButton("Закрыть", (dialogInterface, i) -> {})
                .create();
    }

}
