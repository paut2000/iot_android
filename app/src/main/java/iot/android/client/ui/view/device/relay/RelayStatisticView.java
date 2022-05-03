package iot.android.client.ui.view.device.relay;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import iot.android.client.R;
import iot.android.client.databinding.RelayStatisticViewBinding;
import iot.android.client.model.device.actuator.Relay;
import iot.android.client.model.device.data.RelayData;
import iot.android.client.ui.chart.HorizontalBarChartCustomizer;
import iot.android.client.ui.chart.axis.DateAxisFormatter;
import iot.android.client.ui.chart.marker.RelayMarker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RelayStatisticView extends ConstraintLayout {

    private final Relay relay;

    private final HorizontalBarChart barChart;

    public RelayStatisticView(Context context, Relay relay) {
        super(context);

        this.relay = relay;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.relay_statistic_view, this, true);

        RelayStatisticViewBinding binding = RelayStatisticViewBinding.bind(this);

        barChart = binding.barChart;

        init(context);
    }

    private void init(Context context) {
        Date nowDate = new Date();
        Timestamp now = new Timestamp(nowDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DATE, -30);

        Timestamp monthAgo = new Timestamp(calendar.getTime().getTime());

        Date midnightToday = Date.from(nowDate.toInstant().truncatedTo(ChronoUnit.DAYS));

        relay.requestSampleForPeriod(monthAgo, now, message -> {
            ArrayList<RelayData> dataList = (ArrayList<RelayData>) (ArrayList<?>) message.getDataList();

            setCustomHorizontalBarChart(dataList.get(0).getDatetime().getTime(), barChart);


            ArrayList<RelayPeriodData> periodDataList = new ArrayList<>();
            ArrayList<RelayData> oneDayData = dataList.stream()
                    .filter(relayData -> relayData.getDatetime().after(midnightToday))
                    .collect(Collectors.toCollection(ArrayList::new));

            for (int i = 0; i < oneDayData.size(); i++) {
                RelayPeriodData periodData = new RelayPeriodData();
                periodData.setDatetime(oneDayData.get(i).getDatetime());
                periodData.setStatus(oneDayData.get(i).getStatus());

                for (int j = i + 1; j < oneDayData.size(); i++, j++) {
                    if (periodData.getStatus() != oneDayData.get(j).getStatus()) {
                        periodData.setEndDatetime(oneDayData.get(j).getDatetime());
                        break;
                    }
                }

                if (i + 1 == oneDayData.size()) {
                    periodData.setEndDatetime(nowDate);
                }

                periodDataList.add(periodData);
            }

            float[] periods = new float[periodDataList.size()];
            IntStream.range(0, periodDataList.size()).forEach(i -> {
                RelayPeriodData current = periodDataList.get(i);
                periods[i] = current.getEndDatetime().getTime() - current.getDatetime().getTime();
            });

            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(0, periods, periodDataList));

            BarDataSet set = new BarDataSet(entries, null);

            set.resetColors();
            periodDataList.forEach(data -> {
                if (data.getStatus() == true) {
                    set.addColor(Color.rgb(66, 135, 245));
                } else {
                    set.addColor(Color.rgb(59, 20, 175));
                }
            });

            BarData barData = new BarData(set);
            barData.setDrawValues(false);

            barChart.setData(barData);
        });
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public class RelayPeriodData extends RelayData {

        private Date endDatetime;

    }

    private void setCustomHorizontalBarChart(Long epochShift, HorizontalBarChart chart) {
        new HorizontalBarChartCustomizer(chart)
                .setMarker(new RelayMarker(getContext(), "HH:mm"))
                .setXAxisFormatter(new DateAxisFormatter(epochShift, "HH:mm"))
                .finish();
    }



}
