package iot.android.client.ui.view.device.relay;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import iot.android.client.R;
import iot.android.client.databinding.RelayStatisticViewBinding;
import iot.android.client.model.device.actuator.Relay;
import iot.android.client.model.device.data.AbstractData;
import iot.android.client.model.device.data.RelayData;
import iot.android.client.ui.chart.HorizontalBarChartCustomizer;
import iot.android.client.ui.chart.axis.DateAxisFormatter;
import iot.android.client.ui.chart.data.CustomBarDataSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RelayStatisticView extends ConstraintLayout {

    private final Relay relay;

    private final PieChart pieChart;
    private final BarChart barChart;

    public RelayStatisticView(Context context, Relay relay) {
        super(context);

        this.relay = relay;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.relay_statistic_view, this, true);

        RelayStatisticViewBinding binding = RelayStatisticViewBinding.bind(this);

        pieChart = binding.pieChart;
        barChart = binding.barChart;

        init(context);
    }

    private void init(Context context) {
        setCustomHorizontalBarChart(barChart);

        Date nowDate = new Date();
        Timestamp now = new Timestamp(nowDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DATE, -30);

        Timestamp monthAgo = new Timestamp(calendar.getTime().getTime());

        Date midnightToday = Date.from(nowDate.toInstant().truncatedTo(ChronoUnit.DAYS));

        relay.requestSampleForPeriod(monthAgo, now, message -> {
            ArrayList<RelayData> dataList = (ArrayList<RelayData>) (ArrayList<?>) message.getDataList();


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

            CustomBarDataSet set = new CustomBarDataSet(entries, null);

            if (periodDataList.get(0).getStatus() == true) {
                set.setColors(
                        Color.rgb(227, 208, 0),
                        Color.rgb(0, 208, 227)
                );
            } else {
                set.setColors(
                        Color.rgb(0, 208, 227),
                        Color.rgb(227, 208, 0)
                );
            }


            BarData barData = new BarData(set);
            barData.setDrawValues(false);

            barChart.setData(barData);
        });
    }

    private void setCustomHorizontalBarChart(HorizontalBarChart chart) {
        new HorizontalBarChartCustomizer(chart)
                .setXAxisFormatter(new DateAxisFormatter("HH:mm"))
                .finish();
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public class RelayPeriodData extends RelayData {

        private Date endDatetime;

    }

}
