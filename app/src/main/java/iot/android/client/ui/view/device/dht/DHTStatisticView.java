package iot.android.client.ui.view.device.dht;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import iot.android.client.R;
import iot.android.client.databinding.DhtStatisticViewBinding;
import iot.android.client.model.device.data.DHTData;
import iot.android.client.ui.chart.axis.DateAxisFormatter;
import iot.android.client.ui.chart.marker.DHTMarker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DHTStatisticView extends ConstraintLayout {

    private final List<DHTData> dataList;

    private final LineChart dayTemperatureChart;
    private final LineChart dayHumidityChart;
    private final LineChart monthTemperatureChart;
    private final LineChart monthHumidityChart;

    public DHTStatisticView(Context context, List<DHTData> dataList) {
        super(context);

        this.dataList = dataList;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dht_statistic_view, this, true);

        DhtStatisticViewBinding binding = DhtStatisticViewBinding.bind(this);

        dayTemperatureChart = binding.dayTemperatureChart;
        dayHumidityChart = binding.dayHumidityChart;
        monthTemperatureChart = binding.monthTemperatureChart;
        monthHumidityChart = binding.monthHumidityChart;

        init(context);
    }

    private void init(Context context) {
        createChartsForDay(context);
        createChartsForMonth(context);
    }

    private void createChartsForMonth(Context context) {
        ArrayList<Entry> maxTemperatureEntries = new ArrayList<>();
        ArrayList<Entry> minTemperatureEntries = new ArrayList<>();
        ArrayList<Entry> maxHumidityEntries = new ArrayList<>();
        ArrayList<Entry> minHumidityEntries = new ArrayList<>();
        
        Date startDay = Date.from(
                LocalDateTime.now()
                        .truncatedTo(ChronoUnit.DAYS)
                        .minusMonths(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        Date endDay = Date.from(
                LocalDateTime.now()
                        .truncatedTo(ChronoUnit.DAYS)
                        .minusMonths(1)
                        .plusDays(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        Date terminalDate = Date.from(
                LocalDateTime.now()
                        .truncatedTo(ChronoUnit.DAYS)
                        .plusDays(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        while (!startDay.equals(terminalDate)) {
            Date finalStartDay = startDay;
            Date finalEndDay = endDay;

            Supplier<Stream<DHTData>> requiredDataPeriodStream = () -> dataList.stream()
                    .filter(
                            dhtData -> dhtData.getDatetime().after(finalStartDay) &&
                                            dhtData.getDatetime().before(finalEndDay)
                    );
            DHTData maxTemperature = requiredDataPeriodStream.get()
                    .max(Comparator.comparing(DHTData::getTemperature))
                    .orElse(null);
            DHTData minTemperature = requiredDataPeriodStream.get()
                    .min(Comparator.comparing(DHTData::getTemperature))
                    .orElse(null);
            DHTData maxHumidity = requiredDataPeriodStream.get()
                    .max(Comparator.comparing(DHTData::getHumidity))
                    .orElse(null);
            DHTData minHumidity = requiredDataPeriodStream.get()
                    .min(Comparator.comparing(DHTData::getHumidity))
                    .orElse(null);

            if (maxTemperature != null) {
                maxTemperature.setDatetime(Date.from(
                        maxTemperature.getDatetime().toInstant().truncatedTo(ChronoUnit.DAYS)
                ));
                maxTemperatureEntries.add(new Entry(
                        maxTemperature.getDatetime().getTime(),
                        maxTemperature.getTemperature().floatValue(),
                        maxTemperature
                ));
            }

            if (minTemperature != null) {
                minTemperature.setDatetime(Date.from(
                        minTemperature.getDatetime().toInstant().truncatedTo(ChronoUnit.DAYS)
                ));
                minTemperatureEntries.add(new Entry(
                        minTemperature.getDatetime().getTime(),
                        minTemperature.getTemperature().floatValue(),
                        minTemperature
                ));
            }
            if (maxHumidity != null) {
                maxHumidity.setDatetime(Date.from(
                        maxHumidity.getDatetime().toInstant().truncatedTo(ChronoUnit.DAYS)
                ));
                maxHumidityEntries.add(new Entry(
                        maxHumidity.getDatetime().getTime(),
                        maxHumidity.getHumidity().floatValue(),
                        maxHumidity
                ));
            }
            if (minHumidity != null) {
                minHumidity.setDatetime(Date.from(
                        minHumidity.getDatetime().toInstant().truncatedTo(ChronoUnit.DAYS)
                ));
                minHumidityEntries.add(new Entry(
                        minHumidity.getDatetime().getTime(),
                        minHumidity.getHumidity().floatValue(),
                        minHumidity
                ));
            }

            startDay = Date.from(
                    startDay.toInstant().plus(1, ChronoUnit.DAYS)
            );
            endDay = Date.from(
                    startDay.toInstant().plus(1, ChronoUnit.DAYS)
            );
        }

        LineDataSet maxTemperatureDataSet = createChartDataSet(
                maxTemperatureEntries,
                "Максимальная температура",
                Color.rgb(227, 127, 127),
                Color.rgb(222, 162, 162)
        );
        LineDataSet minTemperatureDataSet = createChartDataSet(
                minTemperatureEntries,
                "Минимальная температура",
                Color.rgb(101,203,225),
                Color.rgb(180,230,235)
        );

        List<ILineDataSet> temperatureDataSets = new ArrayList<>();
        temperatureDataSets.add(maxTemperatureDataSet);
        temperatureDataSets.add(minTemperatureDataSet);

        LineData temperatureLineData = new LineData(temperatureDataSets);
        temperatureLineData.setDrawValues(false);
        monthTemperatureChart.setData(temperatureLineData);

        LineDataSet maxHumidityDataSet = createChartDataSet(
                maxHumidityEntries,
                "Максимальная влажность",
                Color.rgb(227, 127, 127),
                Color.rgb(222, 162, 162)
        );
        LineDataSet minHumidityDataSet = createChartDataSet(
                minHumidityEntries,
                "Минимальная влажность",
                Color.rgb(101,203,225),
                Color.rgb(180,230,235)
        );

        List<ILineDataSet> humidityDataSets = new ArrayList<>();
        humidityDataSets.add(maxHumidityDataSet);
        humidityDataSets.add(minHumidityDataSet);

        LineData humidityLineData = new LineData(humidityDataSets);
        humidityLineData.setDrawValues(false);
        monthHumidityChart.setData(humidityLineData);

        setChartLayout(monthTemperatureChart, "dd", "dd MMMM",
                true, context);
        setChartLayout(monthHumidityChart, "dd", "dd MMMM",
                true,context);
    }

    private void createChartsForDay(Context context) {
        setChartLayout(dayTemperatureChart, "HH:mm", "HH:mm dd MMMM",
                false,context);
        setChartLayout(dayHumidityChart, "HH:mm", "HH:mm dd MMMM",
                false,context);

        ArrayList<Entry> temperatureEntries = new ArrayList<>();
        ArrayList<Entry> humidityEntries = new ArrayList<>();
        Date oneDayBefore = Date.from(
                LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant()
        );
        dataList.forEach(dhtData -> {
            if (dhtData.getDatetime().after(oneDayBefore)) {
                temperatureEntries.add(new Entry(
                        dhtData.getDatetime().getTime(),
                        dhtData.getTemperature().floatValue(),
                        dhtData
                ));
                humidityEntries.add(new Entry(
                        dhtData.getDatetime().getTime(),
                        dhtData.getHumidity().floatValue(),
                        dhtData
                ));
            }
        });

        LineData temperatureLineData = new LineData(createChartDataSet(
                temperatureEntries,
                "Температура",
                Color.rgb(227, 127, 127),
                Color.rgb(222, 162, 162)));
        temperatureLineData.setDrawValues(false);
        dayTemperatureChart.setData(temperatureLineData);
        LineData humidityLineData = new LineData(createChartDataSet(
                humidityEntries,
                "Влажность",
                Color.rgb(101,203,225),
                Color.rgb(180,230,235)));
        humidityLineData.setDrawValues(false);
        dayHumidityChart.setData(humidityLineData);
    }

    private LineDataSet createChartDataSet(ArrayList<Entry> entries, String label, int lineColor, int fillColor) {
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

        return lineDataSet;
    }

    private void setChartLayout(
            LineChart chart,
            String axisDateFormat,
            String markerDateFormat,
            boolean isXLabelCountDefault,
            Context context) {
        chart.setViewPortOffsets(100, 100, 100, 100);
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);

        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        if (!isXLabelCountDefault) x.setLabelCount(6, true);
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.BLACK);

        YAxis y = chart.getAxisLeft();
        y.setLabelCount(6, true);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.BLACK);

        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setValueFormatter(new DateAxisFormatter(axisDateFormat));

        chart.animateXY(0, 0);

        DHTMarker dhtMarker = new DHTMarker(context, markerDateFormat);
        dhtMarker.setChartView(chart);
        chart.setMarker(dhtMarker);

        // don't forget to refresh the drawing
        chart.invalidate();
    }

}
