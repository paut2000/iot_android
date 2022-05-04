package iot.android.client.model.group;

import iot.android.client.model.device.AbstractDevice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class DeviceGroup {

    @Setter
    private Long id = null;

    @Setter
    private String name;

    private List<AbstractDevice> devices = new ArrayList<>();

    public void addDevice(AbstractDevice device) throws IOException {
        if (devices.contains(device)) throw new IOException("Невозможно добавить: этот дейвайс уже в группе");
        devices.add(device);
    }

    public void removeDevice(AbstractDevice device) throws IOException {
        if (!devices.contains(device)) throw new IOException("Невозможно удалить: такого девайса нет в группе");
        devices.remove(device);
    }

    public void removeAllDevices() {
        devices.clear();
    }

}
