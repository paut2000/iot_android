package iot.android.client.ui.chart.axis;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAxisFormatter extends IndexAxisValueFormatter {

    final String dateFormat;
    final Long epochShift;

    public DateAxisFormatter(Long epochShift, String dateFormat) {
        super();
        this.dateFormat = dateFormat;
        this.epochShift = epochShift;
    }

    @Override
    public String getFormattedValue(float value) {
        Date date = new Date(epochShift + (long) value);

        return new SimpleDateFormat(dateFormat).format(date);
    }

}
