package iot.android.client.ui.view.device.dht;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import iot.android.client.R;
import iot.android.client.databinding.DhtViewBinding;
import iot.android.client.model.device.sensor.DHT;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

public class DHTView extends TableLayout {

    private final DHT dht;

    private final TextView temperature;
    private final TextView humidity;
    private final TextView datetime;

    public DHTView(@NonNull @NotNull Context context, DHT dht) {
        super(context);

        this.dht = dht;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dht_view, this, true);

        DhtViewBinding binding = DhtViewBinding.bind(this);
        temperature = binding.temperatureView;
        humidity = binding.humidityView;
        datetime = binding.datetimeView;

        init();
    }

    private void init() {
        temperature.setText(String.format("%.2f", dht.getData().getTemperature()));
        humidity.setText(String.format("%.2f", dht.getData().getHumidity()));
        datetime.setText(new SimpleDateFormat("hh:mm").format(dht.getData().getDatetime().getTime()));

    }

}
