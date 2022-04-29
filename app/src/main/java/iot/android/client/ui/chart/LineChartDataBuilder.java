package iot.android.client.ui.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineChartDataBuilder {

    private final List<ILineDataSet> lines = new ArrayList<>();

    public LineChartDataBuilder() {
    }

    public LineData create() {
        LineData lineData = new LineData(lines);
        lineData.setDrawValues(false);
        return lineData;
    }

    public LineChartDataBuilder addLine(ArrayList<Entry> entries, String label, int lineColor, int fillColor) {
        LineDataSet lineDataSet = new LineDataSet(entries, label);

        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(lineColor);
        lineDataSet.setFillColor(fillColor);
        lineDataSet.setFillAlpha(100);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);

        lines.add(lineDataSet);

        return this;
    }

}
