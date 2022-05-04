package iot.android.client.ui.view.datePicker;

import android.content.Context;
import android.widget.DatePicker;
import androidx.appcompat.app.AlertDialog;
import iot.android.client.callback.OnSelectDateCallback;
import iot.android.client.ui.utils.DateTimeUtils;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DatePickerDialogCreator {

    public static AlertDialog createDatePickerDialog(
            Context context,
            long maxDate,
            OnSelectDateCallback callback
    ) {
        DatePicker datePicker = new DatePicker(context);
        datePicker.setMaxDate(maxDate);

        return new AlertDialog.Builder(context)
                .setView(datePicker)
                .setTitle("Выберите дату")
                .setPositiveButton("Выбрать", (dialogInterface, i) -> {
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);

                    Timestamp timestamp = new Timestamp(
                            DateTimeUtils.truncateToMidnight(calendar.getTime()).getTime()
                    );

                    callback.onSelect(timestamp);
                })
                .setNegativeButton("Закрыть", (dialogInterface, i) -> {})
                .create();
    }

}
