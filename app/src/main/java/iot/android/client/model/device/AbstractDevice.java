package iot.android.client.model.device;

import iot.android.client.model.device.data.AbstractData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractDevice {

    protected String serialNumber;

    protected String type;

    protected boolean alive;

    protected AbstractData data;

}
