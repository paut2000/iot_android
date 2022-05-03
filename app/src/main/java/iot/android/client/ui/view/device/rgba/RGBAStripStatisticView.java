package iot.android.client.ui.view.device.rgba;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import iot.android.client.R;
import iot.android.client.databinding.RelayStatisticViewBinding;
import iot.android.client.model.device.actuator.RGBAStrip;
import iot.android.client.model.device.data.RGBAStripData;
import iot.android.client.ui.chart.HorizontalBarChartCustomizer;
import iot.android.client.ui.chart.axis.DateAxisFormatter;
import iot.android.client.ui.chart.marker.RGBAStripMarker;
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

public class RGBAStripStatisticView extends ConstraintLayout {

    private final RGBAStrip strip;

    private final HorizontalBarChart barChart;

    public RGBAStripStatisticView(Context context, RGBAStrip strip) {
        super(context);

        this.strip = strip;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.relay_statistic_view, this, true);

        RelayStatisticViewBinding binding = RelayStatisticViewBinding.bind(this);

        barChart = binding.barChart;

        init();
    }

    private void init() {
        Date nowDate = new Date();
        Timestamp now = new Timestamp(nowDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DATE, -30);

        Timestamp monthAgo = new Timestamp(calendar.getTime().getTime());

        Date midnightToday = Date.from(nowDate.toInstant().truncatedTo(ChronoUnit.DAYS));

        strip.requestSampleForPeriod(monthAgo, now, message -> {
            ArrayList<RGBAStripData> dataList = (ArrayList<RGBAStripData>) (ArrayList<?>) message.getDataList();

            setCustomHorizontalBarChart(dataList.get(0).getDatetime().getTime(), barChart);


            ArrayList<RGBAStripPeriodData> periodDataList = new ArrayList<>();
            ArrayList<RGBAStripData> oneDayData = dataList.stream()
                    .filter(relayData -> relayData.getDatetime().after(midnightToday))
                    .collect(Collectors.toCollection(ArrayList::new));

            for (int i = 0; i < oneDayData.size(); i++) {
                RGBAStripPeriodData periodData = new RGBAStripPeriodData();
                periodData.setDatetime(oneDayData.get(i).getDatetime());
                periodData.setAlfa(oneDayData.get(i).getAlfa());
                periodData.setRed(oneDayData.get(i).getRed());
                periodData.setGreen(oneDayData.get(i).getGreen());
                periodData.setBlue(oneDayData.get(i).getBlue());

                for (int j = i + 1; j < oneDayData.size(); i++, j++) {
                    if (periodData.getAlfa() != oneDayData.get(j).getAlfa() ||
                            periodData.getRed() != oneDayData.get(j).getRed() ||
                            periodData.getGreen() != oneDayData.get(j).getGreen() ||
                            periodData.getBlue() != oneDayData.get(j).getBlue()) {
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
                RGBAStripPeriodData current = periodDataList.get(i);
                periods[i] = current.getEndDatetime().getTime() - current.getDatetime().getTime();
            });

            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(0, periods, periodDataList));

            BarDataSet set = new BarDataSet(entries, null);

            set.resetColors();
            periodDataList.forEach(data -> {
                set.addColor(Color.rgb(data.getRed(), data.getGreen(), data.getBlue()));
            });

            BarData barData = new BarData(set);
            barData.setDrawValues(false);

            barChart.setData(barData);
        });
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public class RGBAStripPeriodData extends RGBAStripData {

        private Date endDatetime;

    }

    private void setCustomHorizontalBarChart(Long epochShift, HorizontalBarChart chart) {
        new HorizontalBarChartCustomizer(chart)
                .setMarker(new RGBAStripMarker(getContext(), "HH:mm"))
                .setXAxisFormatter(new DateAxisFormatter(epochShift, "HH:mm"))
                .finish();
    }

}
