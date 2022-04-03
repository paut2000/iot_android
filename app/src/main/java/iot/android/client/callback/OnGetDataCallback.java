package iot.android.client.callback;

import iot.android.client.model.device.data.AbstractData;

import java.util.List;

public interface OnGetDataCallback {

    void onGetData(List<? extends AbstractData> data);

}
