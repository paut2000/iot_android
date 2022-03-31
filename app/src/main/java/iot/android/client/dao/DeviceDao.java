package iot.android.client.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import iot.android.client.App;
import iot.android.client.model.House;
import iot.android.client.model.device.AbstractDevice;

import javax.inject.Inject;

public class DeviceDao {

    private DBHelper helper;
    private SQLiteDatabase database;

    public DeviceDao(DBHelper helper) {
        this.helper = helper;
        database = helper.getWritableDatabase();
    }

    public void insert(AbstractDevice device) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_DEVICE_NAME, device.getName());
        contentValues.put(DBHelper.KEY_DEVICE_SERIAL_NUMBER, device.getSerialNumber());

        database.insert(DBHelper.TABLE_DEVICES, null, contentValues);
    }

    public void update(AbstractDevice device) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_DEVICE_NAME, device.getName());

        database.update(
                DBHelper.TABLE_DEVICES,
                contentValues,
                DBHelper.KEY_DEVICE_SERIAL_NUMBER + " = ?",
                new String[] { device.getSerialNumber() });
    }

    /* Synchronize name by serial_number */
    public void synchronizeOrInsert(AbstractDevice device) {
        Cursor cursor = database.query(
                DBHelper.TABLE_DEVICES,
                new String[] { DBHelper.KEY_DEVICE_NAME },
                DBHelper.KEY_DEVICE_SERIAL_NUMBER + " = ?",
                new String[] { device.getSerialNumber() },
                null,
                null,
                null
        );

        // If device with that serial_number doesn't exist
        if (!cursor.moveToFirst()) {
            insert(device);
        } else {
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_DEVICE_NAME);
            String name = cursor.getString(nameIndex);
            device.setName(name);
        }

        cursor.close();
    }

}
