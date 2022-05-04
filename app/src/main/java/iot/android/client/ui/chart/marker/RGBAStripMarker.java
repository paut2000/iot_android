package iot.android.client.ui.chart.marker;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import iot.android.client.R;
import iot.android.client.databinding.RgbaStripMarkerViewBinding;
import iot.android.client.ui.utils.ColorUtils;
import iot.android.client.ui.view.device.relay.RelayStatisticView;
import iot.android.client.ui.view.device.rgba.RGBAStripStatisticView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RGBAStripMarker extends MarkerView {

    private final TextView startTimeText;
    private final TextView endTimeText;
    private final TextView colorText;

    private final String dateFormat;

    public RGBAStripMarker(Context context, String dateFormat) {
        super(context, R.layout.rgba_strip_marker_view);

        this.dateFormat = dateFormat;

        RgbaStripMarkerViewBinding binding = RgbaStripMarkerViewBinding.bind(this);

        startTimeText = binding.startTimeText;
        endTimeText = binding.endTimeText;
        colorText = binding.colorText;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int periodIndex = highlight.getStackIndex();

        if (periodIndex < 0) periodIndex = 0;

        RGBAStripStatisticView.RGBAStripPeriodData data =
                ((ArrayList<RGBAStripStatisticView.RGBAStripPeriodData>) e.getData()).get(periodIndex);

        startTimeText.setText(new SimpleDateFormat(dateFormat).format(data.getDatetime()));
        endTimeText.setText(new SimpleDateFormat(dateFormat).format(data.getEndDatetime()));

        colorText.setText(ColorUtils.getBestColorName(
                Color.valueOf(data.getRed(), data.getGreen(), data.getBlue())
        ));

        super.refreshContent(e, highlight);
    }

}
