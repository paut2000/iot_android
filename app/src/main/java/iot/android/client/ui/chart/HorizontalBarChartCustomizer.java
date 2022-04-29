package iot.android.client.ui.chart;

import android.graphics.Color;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class HorizontalBarChartCustomizer {

    private final HorizontalBarChart chart;

    public HorizontalBarChartCustomizer(HorizontalBarChart horizontalBarChart) {
        this.chart = horizontalBarChart;
        generalPlotLayout();
        generalXAxisSettings();
        generalYAxisSettings();
    }

    public void finish() {
        chart.invalidate();
    }

    public HorizontalBarChartCustomizer setXAxisFormatter(IndexAxisValueFormatter valueFormatter) {
        chart.getAxisRight().setValueFormatter(valueFormatter);
        return this;
    }

    public HorizontalBarChartCustomizer setMarker(MarkerView marker) {
        marker.setChartView(chart);
        chart.setMarker(marker);
        return this;
    }

    private void generalPlotLayout() {
        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleXEnabled(true);
        chart.setScaleYEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);
        chart.animateXY(1000, 1000);
    }

    private void generalXAxisSettings() {
        chart.getXAxis().setEnabled(false);
    }

    private void generalYAxisSettings() {
        chart.getAxisLeft().setEnabled(false);
        YAxis y = chart.getAxisRight();
        y.setEnabled(true);
        y.setLabelCount(6, true);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.BLACK);
    }

}
