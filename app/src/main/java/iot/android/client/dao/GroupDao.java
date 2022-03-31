package iot.android.client.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import iot.android.client.App;
import iot.android.client.model.House;
import iot.android.client.model.group.DeviceGroup;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {

    @Inject
    House house;

    private DBHelper helper;
    private SQLiteDatabase database;

    public GroupDao(DBHelper helper) {
        App.getDaoComponent().inject(this);

        this.helper = helper;
        database = helper.getWritableDatabase();
    }

    public List<DeviceGroup> readAll() {
        ArrayList<DeviceGroup> groups = new ArrayList<>();

        Cursor cursor = database.query(
                DBHelper.TABLE_GROUPS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_GROUP_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_GROUP_NAME);

            do {
                Long id = cursor.getLong(idIndex);
                String name = cursor.getString(nameIndex);

                DeviceGroup group = new DeviceGroup();
                group.setId(id);
                group.setName(name);

                synchronizeGroupsLinksToDevices(group);

                groups.add(group);
            } while (cursor.moveToNext());

        }

        cursor.close();

        return groups;
    }

    /*
     * Group id must be synchronized
     */
    public void synchronizeGroupsLinksToDevices(DeviceGroup group) {
        Cursor cursor = database.query(
                DBHelper.TABLE_DEVICE_TO_GROUP,
                new String[] { DBHelper.KEY_DEVICE_TO_GROUP_DEVICE_SERIAL_NUMBER },
                DBHelper.KEY_DEVICE_TO_GROUP_GROUP_ID + " = ? ",
                new String[] { group.getId().toString() },
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {

            int deviceSerialNumberIndex = cursor.getColumnIndex(DBHelper.KEY_DEVICE_TO_GROUP_DEVICE_SERIAL_NUMBER);

            do {
                String deviceSerialNumber = cursor.getString(deviceSerialNumberIndex);

                try {
                    group.addDevice(house.getDevice(deviceSerialNumber));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    /*
     * Create new group and synchronize id
     */
    public void create(DeviceGroup group) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_GROUP_NAME, group.getName());

        Long id = database.insert(DBHelper.TABLE_GROUPS, null, contentValues);
        group.setId(id);
    }

    /*
     * Group must be synchronized
     */
    public void updateGroupName(DeviceGroup group) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_GROUP_NAME, group.getName());

        database.update(
                DBHelper.TABLE_GROUPS,
                contentValues,
                DBHelper.KEY_GROUP_ID + " = ? ",
                new String[] { group.getId().toString() }
        );
    }

    /*
     * Group must be synchronized
     */
    public void deleteGroup(DeviceGroup group) {
        // Delete many to many links in device to group table
        database.delete(
                DBHelper.TABLE_DEVICE_TO_GROUP,
                DBHelper.KEY_DEVICE_TO_GROUP_GROUP_ID + " = ?",
                new String[] { group.getId().toString() }
        );

        // Delete from group table
        database.delete(
                DBHelper.TABLE_GROUPS,
                DBHelper.KEY_GROUP_ID + " = ?",
                new String[] { group.getId().toString() }
        );
    }

    public void addDeviceToGroup(Long groupId, String serialNumber) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_DEVICE_TO_GROUP_DEVICE_SERIAL_NUMBER, serialNumber);
        contentValues.put(DBHelper.KEY_DEVICE_TO_GROUP_GROUP_ID, groupId);

        database.insert(DBHelper.TABLE_DEVICE_TO_GROUP, null, contentValues);
    }

    public void deleteDeviceFromGroup(Long groupId, String serialNumber) {
        database.delete(
                DBHelper.TABLE_DEVICE_TO_GROUP,
                DBHelper.KEY_DEVICE_TO_GROUP_GROUP_ID + " = ? and " +
                        DBHelper.KEY_DEVICE_TO_GROUP_DEVICE_SERIAL_NUMBER + " = ? ",
                new String[] { groupId.toString(), serialNumber }
        );
    }

}
