package iot.android.client.ui.chart;

import android.graphics.Color;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class LineChartCustomizer {

    private final LineChart chart;

    public LineChartCustomizer(LineChart chart) {
        this.chart = chart;
        generalPlotLayout();
        generalXAxisSettings();
        generalYAxisSettings();
    }

    public void finish() {
        chart.invalidate();
    }

    public LineChartCustomizer setXAxisFormatter(IndexAxisValueFormatter valueFormatter) {
        chart.getXAxis().setValueFormatter(valueFormatter);
        return this;
    }

    public LineChartCustomizer setMarker(MarkerView marker) {
        marker.setChartView(chart);
        chart.setMarker(marker);
        return this;
    }

    private void generalPlotLayout() {
        chart.setViewPortOffsets(100, 100, 100, 100);
        chart.getDescription().setEnabled(false);
        chart.setNoDataText("Нет данных");
        // enable touch gestures
        chart.setTouchEnabled(true);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleXEnabled(true);
        chart.setScaleYEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);
        chart.animateXY(1000, 1000);
    }

    private void generalXAxisSettings() {
        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        x.setLabelCount(6, true);
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.BLACK);
    }

    private void generalYAxisSettings() {
        YAxis y = chart.getAxisLeft();
        y.setLabelCount(6, true);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.BLACK);
        chart.getAxisRight().setEnabled(false);
    }

}
