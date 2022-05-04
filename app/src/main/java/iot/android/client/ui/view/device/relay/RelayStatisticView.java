package iot.android.client.ui.view.device.relay;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.DatePicker;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.github.mikephil.charting.charts.HorizontalBarChart;
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
import iot.android.client.ui.view.datePicker.DatePickerDialogCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RelayStatisticView extends ConstraintLayout {

    private final Relay relay;

    private final HorizontalBarChart barChart;
    private final Button datePickerButton;

    private AlertDialog datePickerDialog;

    public RelayStatisticView(Context context, Relay relay) {
        super(context);

        this.relay = relay;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.relay_statistic_view, this, true);

        RelayStatisticViewBinding binding = RelayStatisticViewBinding.bind(this);

        barChart = binding.barChart;
        datePickerButton = binding.datePickerButton;

        init(context);
    }

    private void init(Context context) {

        Date nowDate = new Date();
        Timestamp now = new Timestamp(nowDate.getTime());

        Date midnightTodayDate = Date.from(nowDate.toInstant().truncatedTo(ChronoUnit.DAYS));
        Timestamp midnightToday = new Timestamp(midnightTodayDate.getTime());

        createChartsForDay(midnightToday, now);

        datePickerDialog = DatePickerDialogCreator.createDatePickerDialog(context, nowDate.getTime(), timestamp -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(timestamp.getTime()));
            calendar.add(Calendar.DATE, 1);

            createChartsForDay(timestamp, new Timestamp(calendar.getTime().getTime()));
        });

        datePickerButton.setOnClickListener(view -> {
            datePickerDialog.show();
        });
    }

    private void createChartsForDay(Timestamp startTimestamp, Timestamp endTimestamp) {
        barChart.clear();
        relay.requestSampleForPeriod(startTimestamp, endTimestamp, message -> {
            ArrayList<RelayData> dataList = (ArrayList<RelayData>) (ArrayList<?>) message.getDataList();

            if (dataList.isEmpty()) return;

            setCustomHorizontalBarChart(dataList.get(0).getDatetime().getTime(), barChart);


            ArrayList<RelayPeriodData> periodDataList = new ArrayList<>();

            for (int i = 0; i < dataList.size(); i++) {
                RelayPeriodData periodData = new RelayPeriodData();
                periodData.setDatetime(dataList.get(i).getDatetime());
                periodData.setStatus(dataList.get(i).getStatus());

                for (int j = i + 1; j < dataList.size(); i++, j++) {
                    if (periodData.getStatus() != dataList.get(j).getStatus()) {
                        periodData.setEndDatetime(dataList.get(j).getDatetime());
                        break;
                    }
                }

                if (i + 1 == dataList.size()) {
                    periodData.setEndDatetime(new Date(endTimestamp.getTime()));
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
