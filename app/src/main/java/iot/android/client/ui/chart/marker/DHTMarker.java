package iot.android.client.ui.chart.marker;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import iot.android.client.R;
import iot.android.client.databinding.DhtMarkerViewBinding;
import iot.android.client.model.device.data.DHTData;

import java.text.SimpleDateFormat;

public class DHTMarker extends MarkerView {

    private final TextView temerature;
    private final TextView humidity;
    private final TextView datetime;

    private final String dateformat;

    public DHTMarker(Context context, String dateformat) {
        super(context, R.layout.dht_marker_view);

        this.dateformat = dateformat;

        DhtMarkerViewBinding binding = DhtMarkerViewBinding.bind(this);
        temerature = binding.temperature;
        humidity = binding.humidity;
        datetime = binding.datetime;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Object obj = e.getData();
        if (obj instanceof DHTData) {
            DHTData data = (DHTData) obj;

            temerature.setText(String.format("%.2f", data.getTemperature()));
            humidity.setText(String.format("%.2f", data.getHumidity()));
            datetime.setText(new SimpleDateFormat(dateformat).format(data.getDatetime()));
        }

        super.refreshContent(e, highlight);
    }
}
