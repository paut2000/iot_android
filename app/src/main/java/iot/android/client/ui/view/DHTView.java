package iot.android.client.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import iot.android.client.R;
import iot.android.client.databinding.DhtViewBinding;
import iot.android.client.model.device.sensor.DHT;
import org.jetbrains.annotations.NotNull;

public class DHTView extends ConstraintLayout {

    private final DHT dht;

    private final TextView name;
    private final TextView temperature;
    private final TextView humidity;
    private final TextView datetime;
    private final TextView errorView;

    public DHTView(@NonNull @NotNull Context context, DHT dht) {
        super(context);

        this.dht = dht;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dht_view, this, true);

        DhtViewBinding binding = DhtViewBinding.bind(this);
        name = binding.name;
        temperature = binding.temperatureView;
        humidity = binding.humidityView;
        datetime = binding.datetimeView;
        errorView = binding.errorView;

        init();
    }

    private void init() {
        name.setText(dht.getSerialNumber());

        if (!dht.isAlive()) {
            errorView.setVisibility(VISIBLE);
            temperature.setVisibility(INVISIBLE);
            humidity.setVisibility(INVISIBLE);
            datetime.setVisibility(INVISIBLE);
            return;
        }

        temperature.setText(dht.getData().getTemperature().toString());
        humidity.setText(dht.getData().getHumidity().toString());
        datetime.setText(dht.getData().getDatetime().toString());

    }

}
