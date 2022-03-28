package iot.android.client.model.device.sensor;

import iot.android.client.model.device.AbstractDevice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class AbstractSensor extends AbstractDevice {

    @Getter
    @Setter
    private Integer updateFrequency = 0;

    public abstract void changeUpdateFrequency(Integer milliSeconds);

}
