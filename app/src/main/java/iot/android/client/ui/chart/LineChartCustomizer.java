package iot.android.client.ui.chart;

import com.github.mikephil.charting.charts.LineChart;

public class LineChartCustomizer {

    private final LineChart chart;

    public LineChartCustomizer(LineChart chart) {
        this.chart = chart;
    }

    public void finish() {
        chart.invalidate();
    }

}
