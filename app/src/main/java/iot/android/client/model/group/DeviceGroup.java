package iot.android.client.model.group;

import iot.android.client.model.device.AbstractDevice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class DeviceGroup implements Serializable {

    @Setter
    private Long id = null;

    @Setter
    private String name;

    private List<AbstractDevice> devices = new ArrayList<>();

    public void addDevice(AbstractDevice device) {
        devices.add(device);
    }

}
