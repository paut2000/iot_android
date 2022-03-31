package iot.android.client.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "smart_home";

    public static final String TABLE_DEVICES = "devices";
    public static final String KEY_DEVICE_SERIAL_NUMBER = "serial_number";
    public static final String KEY_DEVICE_NAME = "name";

    public static final String TABLE_GROUPS = "groups";
    public static final String KEY_GROUP_ID = "id";
    public static final String KEY_GROUP_NAME = "name";

    public static final String TABLE_DEVICE_TO_GROUP = "device_to_group";
    public static final String KEY_DEVICE_TO_GROUP_DEVICE_SERIAL_NUMBER = "device_serial_number";
    public static final String KEY_DEVICE_TO_GROUP_GROUP_ID = "group_id";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createDevicesTable(sqLiteDatabase);
        createGroupsTable(sqLiteDatabase);
        createDeviceToGroupTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropDeviceToGroupTable(sqLiteDatabase);
        dropDevicesTable(sqLiteDatabase);
        dropGroupsTable(sqLiteDatabase);

        onCreate(sqLiteDatabase);
    }

    private void createDevicesTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + TABLE_DEVICES + "(" +
                        KEY_DEVICE_SERIAL_NUMBER + " text not null, " +
                        KEY_DEVICE_NAME + " text not null, " +
                        "primary key (" + KEY_DEVICE_SERIAL_NUMBER + ")" +
                        ")"
        );
    }

    private void createGroupsTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + TABLE_GROUPS + "(" +
                        KEY_GROUP_ID + " integer not null, " +
                        KEY_GROUP_NAME + " text not null, " +
                        "primary key (" + KEY_GROUP_ID + ")" +
                        ")"
        );
    }

    private void createDeviceToGroupTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + TABLE_DEVICE_TO_GROUP + "(" +
                        KEY_DEVICE_TO_GROUP_DEVICE_SERIAL_NUMBER + " text, " +
                        KEY_DEVICE_TO_GROUP_GROUP_ID + " integer, " +
                        "primary key (" +
                        KEY_DEVICE_TO_GROUP_DEVICE_SERIAL_NUMBER + ", " +
                            KEY_DEVICE_TO_GROUP_GROUP_ID +
                            "), " +
                        "foreign key (" + KEY_DEVICE_TO_GROUP_DEVICE_SERIAL_NUMBER + ")" +
                        "references " + TABLE_DEVICES + "(" + KEY_DEVICE_SERIAL_NUMBER + "), " +
                        "foreign key (" + KEY_DEVICE_TO_GROUP_GROUP_ID + ")" +
                        "references " + TABLE_GROUPS + "(" + KEY_GROUP_ID + ") " +
                        ")"
        );
    }

    private void dropDevicesTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "drop table if exists " + TABLE_DEVICES
        );
    }

    private void dropGroupsTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "drop table if exists " + TABLE_GROUPS
        );
    }

    private void dropDeviceToGroupTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "drop table if exists " + TABLE_DEVICE_TO_GROUP
        );
    }

}
