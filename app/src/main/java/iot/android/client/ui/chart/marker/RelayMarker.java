package iot.android.client.ui.chart.marker;

import android.content.Context;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import iot.android.client.R;
import iot.android.client.databinding.RelayMarkerViewBinding;
import iot.android.client.ui.view.device.relay.RelayStatisticView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RelayMarker extends MarkerView {

    private final TextView startTimeText;
    private final TextView endTimeText;
    private final TextView statusText;

    private final String dateFormat;

    public RelayMarker(Context context, String dateFormat) {
        super(context, R.layout.relay_marker_view);

        RelayMarkerViewBinding binding = RelayMarkerViewBinding.bind(this);

        this.dateFormat = dateFormat;

        startTimeText = binding.startTimeText;
        endTimeText = binding.endTimeText;
        statusText = binding.statusText;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int periodIndex = highlight.getStackIndex();

        RelayStatisticView.RelayPeriodData data =
                ((ArrayList<RelayStatisticView.RelayPeriodData>) e.getData()).get(periodIndex);

        startTimeText.setText(new SimpleDateFormat(dateFormat).format(data.getDatetime()));
        endTimeText.setText(new SimpleDateFormat(dateFormat).format(data.getEndDatetime()));

        if (data.getStatus() == true) {
            statusText.setText("Включено");
        } else {
            statusText.setText("Выключено");
        }

        super.refreshContent(e, highlight);
    }
}
