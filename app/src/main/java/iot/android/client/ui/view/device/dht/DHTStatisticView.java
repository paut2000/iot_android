package iot.android.client.ui.view.device.dht;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import iot.android.client.R;
import iot.android.client.databinding.DhtStatisticViewBinding;
import iot.android.client.model.device.data.DHTData;
import iot.android.client.model.device.sensor.DHT;
import iot.android.client.ui.chart.LineChartCustomizer;
import iot.android.client.ui.chart.LineChartDataBuilder;
import iot.android.client.ui.chart.axis.DateAxisFormatter;
import iot.android.client.ui.chart.marker.DHTMarker;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class DHTStatisticView extends ConstraintLayout {

    private final DHT dht;

    private final LineChart dayTemperatureChart;
    private final LineChart dayHumidityChart;
    private final LineChart monthTemperatureChart;
    private final LineChart monthHumidityChart;

    public DHTStatisticView(Context context, DHT dht) {
        super(context);

        this.dht = dht;

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
        customizeDayChart(context, dayTemperatureChart);
        customizeDayChart(context, dayHumidityChart);
        customizeMonthChart(context, monthTemperatureChart);
        customizeMonthChart(context, monthHumidityChart);

        Date nowDate = new Date();
        Timestamp now = new Timestamp(nowDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DATE, -30);

        Timestamp monthAgo = new Timestamp(calendar.getTime().getTime());

        dht.requestSampleForPeriod(monthAgo, now, message -> {
            ArrayList<DHTData> dhtData = (ArrayList<DHTData>) (ArrayList<?>) message.getDataList();
            dayTemperatureChart.setData(
                    new LineChartDataBuilder()
                            .addLine(
                                    createEntryListForDay(dhtData, DHTData::getTemperature),
                                    "Температура",
                                    Color.rgb(227, 127, 127),
                                    Color.rgb(222, 162, 162)
                            ).create()
            );
            dayHumidityChart.setData(
                    new LineChartDataBuilder()
                            .addLine(
                                    createEntryListForDay(dhtData, DHTData::getHumidity),
                                    "Влажность",
                                    Color.rgb(101,203,225),
                                    Color.rgb(180,230,235)
                            ).create()
            );

            monthTemperatureChart.setData(
                    new LineChartDataBuilder()
                            .addLine(
                                    createEntryListForMonth(dhtData, DHTData::getTemperature, Factor.MAX),
                                    "Максимальная температура",
                                    Color.rgb(227, 127, 127),
                                    Color.rgb(222, 162, 162)
                            ).addLine(
                                    createEntryListForMonth(dhtData, DHTData::getTemperature, Factor.MIN),
                                    "Минимальная температура",
                                    Color.rgb(101,203,225),
                                    Color.rgb(180,230,235)
                            ).create()
            );
            monthHumidityChart.setData(new LineChartDataBuilder()
                    .addLine(
                            createEntryListForMonth(dhtData, DHTData::getHumidity, Factor.MAX),
                            "Максимальная влажность",
                            Color.rgb(227, 127, 127),
                            Color.rgb(222, 162, 162)
                    ).addLine(
                            createEntryListForMonth(dhtData, DHTData::getHumidity, Factor.MIN),
                            "Минимальная влажность",
                            Color.rgb(101,203,225),
                            Color.rgb(180,230,235)
                    ).create());
        });

    }

    private void customizeDayChart(Context context, LineChart dayChart) {
        new LineChartCustomizer(dayChart)
                .setMarker(new DHTMarker(context, "HH:mm dd MMMM"))
                .setXAxisFormatter(new DateAxisFormatter("HH:mm"))
                .finish();
    }

    private void customizeMonthChart(Context context, LineChart monthChart) {
        new LineChartCustomizer(monthChart)
                .setMarker(new DHTMarker(context, "dd MMMM"))
                .setXAxisFormatter(new DateAxisFormatter("dd"))
                .finish();
    }

    private ArrayList<Entry> createEntryListForDay(
            List<DHTData> dataList,
            Function<DHTData, Double> method
    ) {
        ArrayList<Entry> entries = new ArrayList<>();

        Date oneDayBefore = Date.from(
                LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant()
        );
        dataList.forEach(dhtData -> {
            if (dhtData.getDatetime().after(oneDayBefore)) {
                entries.add(new Entry(
                        dhtData.getDatetime().getTime(),
                        method.apply(dhtData).floatValue(),
                        dhtData
                ));
            }
        });

        return entries;
    }

    private ArrayList<Entry> createEntryListForMonth(
            List<DHTData> dataList,
            Function<DHTData, Double> method, Factor factor
    ) {
        ArrayList<Entry> entries = new ArrayList<>();

        int control = 0;
        switch (factor) {
            case MAX: control = 1; break;
            case MIN: control = -1; break;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(new Date().toInstant().truncatedTo(ChronoUnit.DAYS)));
        calendar.add(Calendar.DATE, -29);

        Date rightDate = calendar.getTime();

        DHTData extreme = null;
        int i = 0;
        while (i < dataList.size()) {
            DHTData dhtData = dataList.get(i);
            if (dhtData.getDatetime().before(rightDate)) {
                if (extreme == null) {
                    extreme = dhtData;
                } else if (method.apply(dhtData).compareTo(method.apply(extreme)) * control > 0) {
                    extreme = dhtData;
                }
                i++;
            } else {
                if (extreme != null) {
                    extreme.setDatetime(Date.from(
                            extreme.getDatetime().toInstant().truncatedTo(ChronoUnit.DAYS)
                    ));
                    entries.add(new Entry(
                            extreme.getDatetime().getTime(),
                            method.apply(extreme).floatValue(),
                            extreme
                    ));

                    extreme = null;
                }
                calendar.add(Calendar.DATE, 1);
                rightDate = calendar.getTime();
            }
        }

        if (extreme != null) {
            extreme.setDatetime(Date.from(
                    extreme.getDatetime().toInstant().truncatedTo(ChronoUnit.DAYS)
            ));
            entries.add(new Entry(
                    extreme.getDatetime().getTime(),
                    method.apply(extreme).floatValue(),
                    extreme
            ));
        }

        return entries;
    }

    private enum Factor {
        MAX, MIN
    }

}
