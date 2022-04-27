package iot.android.client.ui.chart.axis;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAxisFormatter extends IndexAxisValueFormatter {

    final String dateFormat;

    public DateAxisFormatter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String getFormattedValue(float value) {
        long emissionsMilliSince1970Time = ((long) value);

        Date date = new Date(emissionsMilliSince1970Time);

        return new SimpleDateFormat(dateFormat).format(date);
    }

}
