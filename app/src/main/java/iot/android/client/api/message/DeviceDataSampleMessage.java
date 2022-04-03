package iot.android.client.api.message;

import iot.android.client.model.device.data.AbstractData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeviceDataSampleMessage {

    private String type;
    private List<AbstractData> dataList;

}
